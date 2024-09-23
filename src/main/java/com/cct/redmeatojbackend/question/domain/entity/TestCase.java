package com.cct.redmeatojbackend.question.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cct
 * @description 题目测试用例视图
 */
@Data
public class TestCase implements Serializable {

    /**
     * 规定默认testCaseId从1开始顺序增长的正整数
     */
    Integer testCaseId;

    String inputContent;

    String outputContent;

    private static final long serialVersionUID = 1L;
}