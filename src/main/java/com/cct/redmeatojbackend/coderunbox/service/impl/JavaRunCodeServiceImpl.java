package com.cct.redmeatojbackend.coderunbox.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.domain.enums.RunCodeResultEnum;
import com.cct.redmeatojbackend.coderunbox.service.RunCodeService;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.exception.BusinessException;

import java.io.*;
import java.nio.file.Paths;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: java运行代码服务
 */
public class JavaRunCodeServiceImpl implements RunCodeService {
    @Override
    public RunCodeResp run(RunCodeReq runCodeReq) {

        RunCodeResp runCodeResp = new RunCodeResp();


        //1.将代码保存
        //获取当前文件所在路径
        String projectPath = System.getProperty("user.dir");
        //拼接文件路径
        String saveTmpFilePath = Paths.get(projectPath, "tmp", "java",
                IdUtil.getSnowflakeNextIdStr(), "Main.java").toString();
        FileUtil.writeUtf8String(runCodeReq.getCode(), saveTmpFilePath);
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
                FileUtil.del(FileUtil.getParent(saveTmpFilePath,1));
                return runCodeResp;
            }
            // 等待进程执行完成
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "代码编译失败");
        }

        //3.运行
        String runCmd = String.format("java -cp %s Main", FileUtil.getParent(saveTmpFilePath, 1));
        try {
            //程序输入内容
            String inputContent = runCodeReq.getInputContent();

            Process runProcess = Runtime.getRuntime().exec(runCmd);
            InputStream errorStream = runProcess.getErrorStream();
            InputStream inputStream = runProcess.getInputStream();
            OutputStream outputStream = runProcess.getOutputStream();


            // 输入数据，写入到进程的输入流
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.append(inputContent);
            writer.flush();
            writer.close();

            //等待进程运行结束
            runProcess.waitFor();

            // 获取错误输出流（错误信息）
            // 如果有错误输出，即编译失败
            if (errorStream.available() > 0) {
                runCodeResp.setOutputContext(IoUtil.readUtf8(errorStream));
                runCodeResp.setResult(RunCodeResultEnum.RUNTIME_ERROR.getDesc());
                runCodeResp.setCode(RunCodeResultEnum.RUNTIME_ERROR.getValue());
                //返回前删除临时文件
                FileUtil.del(FileUtil.getParent(saveTmpFilePath,1));
                return runCodeResp;
            }

            //收集进程正常输出内容
            runCodeResp.setOutputContext(IoUtil.readUtf8(inputStream));
            runCodeResp.setResult(RunCodeResultEnum.SUCCESS.getDesc());
            runCodeResp.setCode(RunCodeResultEnum.SUCCESS.getValue());

        } catch (IOException | InterruptedException e) {
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "代码运行失败");
        }finally {
            //删除临时文件
            FileUtil.del(FileUtil.getParent(saveTmpFilePath,1));
        }

        //4.封装结果返回
        return runCodeResp;
    }

}
