package com.cct.redmeatojbackend;


import cn.hutool.core.io.FileUtil;
import com.cct.redmeatojbackend.common.constant.OssConstant;
import com.cct.redmeatojbackend.oss.MinIOTemplate;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@SpringBootTest
class ApplicationTests {


    @Resource
    private MinIOTemplate minIOTemplate;

    @Test
    void test() {

        for (int i = 1; i <= 10; i++) {
            String ossPathIn = OssConstant.QUESTION_IO_PREFIX_PATH +"1/" + String.format("%d", i) + ".in";
            String ossPathOut = OssConstant.QUESTION_IO_PREFIX_PATH +"1/" + String.format("%d", i) + ".out";
            File filein = FileUtil.file("E:\\012_redMeat_OJ\\redmeatojbackend\\src\\test\\java\\com\\cct\\redmeatojbackend\\tmp\\1.in");
            File fileout = FileUtil.file("E:\\012_redMeat_OJ\\redmeatojbackend\\src\\test\\java\\com\\cct\\redmeatojbackend\\tmp\\1.out");

            minIOTemplate.putObjectWithFile(OssConstant.OSS_BUCKET, ossPathIn, filein);
            minIOTemplate.putObjectWithFile(OssConstant.OSS_BUCKET, ossPathOut, fileout);
        }
    }

    @Test
    void test1() throws IOException {

        String ossPathIn = OssConstant.QUESTION_IO_PREFIX_PATH + "1.in";
        String ossPathOut = OssConstant.QUESTION_IO_PREFIX_PATH + "1.out";
        InputStream inputStreamOss = minIOTemplate.getObject(OssConstant.OSS_BUCKET, ossPathIn);
        InputStream outputStreamOss = minIOTemplate.getObject(OssConstant.OSS_BUCKET, ossPathOut);

        try {
            // 1. 启动进程，运行编译后的 Java 类
            System.out.println(System.getProperty("user.dir"));
            String classPackagePath = System.getProperty("user.dir") + "\\src\\test\\java";
            String run = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s com.cct.redmeatojbackend.Main", classPackagePath);
            //ProcessBuilder构建命令每个参数要用","隔开，或传入String[]数组
            ProcessBuilder processBuilder = new ProcessBuilder(run.split(" "));
            Process process = processBuilder.start();

            // 2. 获取进程的输入和输出流
            OutputStream outputStream = process.getOutputStream(); // 输入流
            InputStream inputStream = process.getInputStream(); // 输出流

            // 3. 输入数据，写入到进程的输入流（如果需要）
            // 如果你的代码需要输入，你可以在这里写入数据
            // 向进程写入输入（模拟用户输入）
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            BufferedReader textReader = new BufferedReader(new InputStreamReader(inputStreamOss));
            String inputLine;
            while ((inputLine = textReader.readLine()) != null) {
                writer.write(inputLine);
                writer.newLine(); // 模拟输入后按下回车
            }
            ;
            writer.flush();  // 将输入发送到进程中


            // 4. 读取进程的输出结果与结果集进行比较
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String outputLine;
            BufferedReader answerReader = new BufferedReader(new InputStreamReader(outputStreamOss));
            String answerLine = null;
            while ((outputLine = reader.readLine()) != null && (answerLine = answerReader.readLine()) != null) {
                if (!Objects.equals(outputLine, answerLine)) {
                    //todo 答案错误的一个操作逻辑
                    System.out.println("答案错误");
                    break;
                }
            }

            if (outputLine == null) {
                answerLine = answerReader.readLine();
                if (answerLine != null) {//todo 答案错误的一个操作逻辑
                    System.out.println("答案错误");
                }else {
                    //todo 答案正确的一个操作逻辑
                    System.out.println("答案正确");
                }
            }

            // 关闭流
            outputStream.close();
            inputStream.close();
            process.waitFor(); // 等待进程结束
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void test2() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Iterable<Result<Item>> redmeatojFileResult = minIOTemplate.listObjects("redmeatoj","" ,true);
        for (Result<Item> itemResult : redmeatojFileResult) {
            Item item = itemResult.get();
            String fileName = FileUtil.getName(item.objectName());
            System.out.println(fileName);
        }
        System.out.println();
    }


}
