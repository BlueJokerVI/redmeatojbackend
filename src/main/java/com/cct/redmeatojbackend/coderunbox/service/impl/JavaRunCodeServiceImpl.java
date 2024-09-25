package com.cct.redmeatojbackend.coderunbox.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.domain.enums.LanguageEnum;
import com.cct.redmeatojbackend.coderunbox.domain.enums.RunCodeResultEnum;
import com.cct.redmeatojbackend.coderunbox.service.RunCodeService;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.cct.redmeatojbackend.common.thread.ThreadPoolConfig.CODE_BOX_IO_EXECUTOR;
import static com.cct.redmeatojbackend.common.thread.ThreadPoolConfig.CODE_BOX_MONITOR_MEMORY_EXECUTOR;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: java运行代码服务
 */
@Service
@Slf4j
@Validated
public class JavaRunCodeServiceImpl implements RunCodeService {

    @Resource(name = CODE_BOX_IO_EXECUTOR)
    private Executor executor;

    @Resource(name = CODE_BOX_MONITOR_MEMORY_EXECUTOR)
    private ScheduledThreadPoolExecutor scheduledExecutor;

    private static final SystemInfo SYSTEM_INFO = new SystemInfo();
    private static final OperatingSystem OS = SYSTEM_INFO.getOperatingSystem();


    static final String STACK_OVERFLOW_ERROR = "Exception in thread \"main\" java.lang.OutOfMemoryError";
    static final String OUT_OF_MEMORY_ERROR = "Exception in thread \"main\" java.lang.StackOverflowError";

    @Override
    public boolean support(String language) {
        return language.equals(LanguageEnum.JAVA.getLanguage());
    }

    @Override
    public RunCodeResp run(@Valid RunCodeReq runCodeReq) {

        RunCodeResp runCodeResp = new RunCodeResp();

        //1.保存临时文件
        String saveTmpFilePath = saveTmpFileAndGetPath(runCodeReq);

        //2.编译
        RunCodeResp compileFailResp = compileFile(runCodeResp, saveTmpFilePath);
        if (compileFailResp != null) {
            return compileFailResp;
        }

        //3.运行
        Integer memoryLimit = runCodeReq.getMemoryLimit();

        //构造运行命令
        memoryLimit /= 1024;
        String runCmd = String.format("java -Xmx%dm -Xss256k -Dfile.encoding=UTF-8 -cp %s Main", memoryLimit, FileUtil.getParent(saveTmpFilePath, 1));

        //程序输入内容
        String inputContent = runCodeReq.getInputContent();

        Process runProcess;
        long timeStart = System.currentTimeMillis();
        try {
            runProcess = Runtime.getRuntime().exec(runCmd);
        } catch (IOException e) {
            //返回前删除临时文件
            FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
            log.error("运行失败 {}", e.getMessage());
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, runCmd + "运行失败!");
        }
        InputStream errorStream = runProcess.getErrorStream();
        InputStream inputStream = runProcess.getInputStream();
        OutputStream outputStream = runProcess.getOutputStream();
        long pid = runProcess.pid();

        // 统计程序运行所消耗的最大内存，每10ms统计一次
        final AtomicLong maxMem = new AtomicLong(0);
        scheduledExecutor.schedule(() -> {
            OSProcess process = OS.getProcess((int) pid);
            if (process == null) {
                return;
            }
            long memory = process.getResidentSetSize();
            maxMem.updateAndGet(oldValue -> Math.max(oldValue, memory));
        }, 10, TimeUnit.MILLISECONDS);


        // 输入数据，写入到进程的输入流
        executor.execute(() -> {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            try {
                writer.append(inputContent);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                //返回前删除临时文件
                FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
                log.error("运行失败 {}", e.getMessage());
                throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "代码运行输入失败");
            }
        });

        //收集进程正常输出内容
        StringBuilder tmpNormalOutput = new StringBuilder();
        executor.execute(() -> {
            tmpNormalOutput.append(IoUtil.readUtf8(inputStream));
        });


        //收集进程异常输出内容
        StringBuilder tmpErrorOutput = new StringBuilder();
        executor.execute(() -> {
            tmpErrorOutput.append(IoUtil.readUtf8(errorStream));
        });


        //设置进程最长时间，等待其运行结束
        boolean finished = false;
        try {
            finished = runProcess.waitFor(runCodeReq.getTimeLimit(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            //返回前删除临时文件
            FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
            log.error("runProcess.waitFor被中断 {}", e.getMessage());
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "runProcess.waitFor被中断");
        }
        //计算运行时间
        long timeConsume = System.currentTimeMillis() - timeStart;
        if (!finished) {
            // 如果进程运行超时，则终止进程，并返回
            runProcess.destroy();
            runCodeResp.setResult(RunCodeResultEnum.TIME_LIMIT_EXCEEDED.getDesc());
            runCodeResp.setCode(RunCodeResultEnum.TIME_LIMIT_EXCEEDED.getValue());
            //返回前删除临时文件
            FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
            return runCodeResp;
        }

        //如果有错误输出,即运行失败
        if (tmpErrorOutput.length() > 0) {
            runCodeResp.setOutputContext(tmpErrorOutput.toString());

            //判断是否是内存溢出错误
            if (StrUtil.startWith(runCodeResp.getOutputContext(), STACK_OVERFLOW_ERROR) || StrUtil.startWith(runCodeResp.getOutputContext(), OUT_OF_MEMORY_ERROR)) {
                runCodeResp.setResult(RunCodeResultEnum.MEMORY_LIMIT_EXCEEDED.getDesc());
                runCodeResp.setCode(RunCodeResultEnum.MEMORY_LIMIT_EXCEEDED.getValue());
                //返回前删除临时文件
                FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
                return runCodeResp;
            }

            runCodeResp.setResult(RunCodeResultEnum.RUNTIME_ERROR.getDesc());
            runCodeResp.setCode(RunCodeResultEnum.RUNTIME_ERROR.getValue());
            runCodeResp.setTimeConsume((int) timeConsume);
            //返回前删除临时文件
            FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
            return runCodeResp;
        }

        //4.正常运行结束返回
        runCodeResp.setOutputContext(tmpNormalOutput.toString());
        runCodeResp.setResult(RunCodeResultEnum.SUCCESS.getDesc());
        runCodeResp.setCode(RunCodeResultEnum.SUCCESS.getValue());
        runCodeResp.setMemoryConsume((int) (maxMem.get() / 1024));
        runCodeResp.setTimeConsume((int) timeConsume);
        //返回前删除临时文件
        FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
        return runCodeResp;

    }

    @Nullable
    private static RunCodeResp compileFile(RunCodeResp runCodeResp, String saveTmpFilePath) {
        //2.编译

        //构造编译命令
        String compileCmd = String.format("javac -encoding UTF-8 %s", saveTmpFilePath);
        try {
            Process process = Runtime.getRuntime().exec(compileCmd);
            // 获取错误输出流（错误信息）
            InputStream errorStream = process.getErrorStream();
            // 如果有错误输出，即编译失败
            if (errorStream.available() > 0) {
                runCodeResp.setOutputContext(IoUtil.readUtf8(errorStream));
                runCodeResp.setResult(RunCodeResultEnum.COMPILE_ERROR.getDesc());
                runCodeResp.setCode(RunCodeResultEnum.COMPILE_ERROR.getValue());
                //返回前删除临时文件
                FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
                return runCodeResp;
            }
            // 等待进程执行完成
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            //返回前删除临时文件
            FileUtil.del(FileUtil.getParent(saveTmpFilePath, 1));
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "代码编译失败");
        }
        return null;
    }

    @NotNull
    private static String saveTmpFileAndGetPath(RunCodeReq runCodeReq) {
        //1.将代码保存
        //获取当前文件所在路径
        String projectPath = System.getProperty("user.dir");
        //拼接文件路径
        String saveTmpFilePath = Paths.get(projectPath, "tmp", "java",
                IdUtil.getSnowflakeNextIdStr(), "Main.java").toString();
        FileUtil.writeUtf8String(runCodeReq.getCode(), saveTmpFilePath);
        return saveTmpFilePath;
    }

}
