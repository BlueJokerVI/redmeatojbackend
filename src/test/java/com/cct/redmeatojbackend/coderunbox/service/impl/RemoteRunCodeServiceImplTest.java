package com.cct.redmeatojbackend.coderunbox.service.impl;

import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.service.RemoteCodeBoxService;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
class RemoteRunCodeServiceImplTest {

    @Resource
    private RemoteCodeBoxService remoteCodeBoxService;

    @Test
    void run() {

        TestCase testCase = new TestCase();
        testCase.setInputContent("1 2");
        testCase.setOutputContent("3");
        testCase.setTestCaseId(1);
        ArrayList<TestCase> testCases = new ArrayList<>();
        testCases.add(testCase);

        RunCodeReq runCodeReq = RunCodeReq.builder()
                .code("import java.util.Scanner;\n" +
                        "public class Main {\n" +
                        "    public static void main(String[] args)  {\n" +
                        "        Scanner scanner = new Scanner(System.in);\n" +
                        "        int i = scanner.nextInt();\n" +
                        "        int i1 = scanner.nextInt();\n" +
                        "        System.out.print(i + i1);\n" +
                        "    }\n" +
                        "}\n")
                .language("java")
                .memoryLimit(524288)
                .timeLimit(1000)
                .testCases(testCases)
                .build();
        remoteCodeBoxService.run(runCodeReq);
    }
}