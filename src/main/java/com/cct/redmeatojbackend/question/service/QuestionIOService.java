package com.cct.redmeatojbackend.question.service;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.GetTestCasePageRequest;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 问题输入输出判题用例服务
 * @Version: 1.0
 */
public interface QuestionIOService {


    /**
     * 上传问题输入输出判题用例
     * @param questionId
     * @param testCase
     * @return
     */
    boolean upLoadQuestionIOFile(Long questionId,TestCase testCase);


    /**
     * 分页获取题目测试用例
     * @param getTestCasePageRequest
     * @return
     */
    BaseResponse<BasePageResp<TestCase>> getTestCasePage(GetTestCasePageRequest getTestCasePageRequest);

    /**
     * 删除题目测试用例
     * @param questionId
     */
    void deleteTestCase(Long questionId);

    /**
     * 删除指定测试用例
     * @param questionId
     * @param testCaseId
     */
    void deleteTestCase(Long questionId,Integer testCaseId);
}
