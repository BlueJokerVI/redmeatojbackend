package com.cct.redmeatojbackend;

//package com.cct.redmeatojbackend;
//
//import cn.hutool.core.io.IoUtil;
//import oshi.SystemInfo;
//import oshi.software.os.OperatingSystem;
//import oshi.software.os.OSProcess;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//
//public class Test {
//
//    public static void main(String[] args) {
//        // 要执行的命令
//        String runCmd = "java -Xmx512m -Xss180k -cp E:\\012_redMeat_OJ\\redmeatojbackend\\src\\test\\java\\com\\cct\\redmeatojbackend Main";
//
//        try {
//            // 启动进程
//            Process runProcess = Runtime.getRuntime().exec(runCmd);
//
//            // 获取进程 ID
//            long processId = runProcess.pid();
//            System.out.println("Started process with ID: " + processId);
//
//            new Thread(() -> {
//                //监控进程错误输出
//                System.out.println(IoUtil.readUtf8(runProcess.getErrorStream()));;
//            }).start();
//
//             //开启监控线程
//            new Thread(() -> {
//                monitorProcessMemory(processId);
//            }).start();
//
//            // 等待进程结束
//            int exitCode = runProcess.waitFor();
//            System.out.println("Process exited with code: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
////        monitorProcessMemory(16740);
//    }
//
//    private static void monitorProcessMemory(long processId) {
//        SystemInfo systemInfo = new SystemInfo();
//        OperatingSystem os = systemInfo.getOperatingSystem();
//
//        while (true) {
//            // 获取当前进程信息
//            OSProcess process = os.getProcess((int)processId);
//            if (process == null) {
//                System.out.println("Process with ID " + processId + " has terminated.");
//                break; // 进程已结束，退出循环
//            }
//
//            // 打印内存使用情况
//            System.out.println("Process ID: " + process.getProcessID());
//            System.out.println("Memory Usage (RSS): " + process.getResidentSetSize() + " bytes");
//            System.out.println("Memory Usage (Virtual): " + process.getVirtualSize() + " bytes");
//
//        }
//    }
//}

import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws InterruptedException, IOException {

        RunCodeReq runCodeReq = new RunCodeReq();
        System.out.println();
    }
}