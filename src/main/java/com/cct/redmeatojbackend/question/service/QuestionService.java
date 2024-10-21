package com.cct.redmeatojbackend.question.service;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.question.AddQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.question.SearchQuestionListRequest;
import com.cct.redmeatojbackend.question.domain.dto.question.SearchQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.question.UpdateQuestionRequest;
import com.cct.redmeatojbackend.question.domain.vo.QuestionVo;

/**
 * @description 题目表服务层
 * @author cct
 */
public interface QuestionService {

    BaseResponse<QuestionVo> addQuestion(AddQuestionRequest addQuestionRequest);

    BaseResponse<Void> deleteQuestion(Long questionId);

    BaseResponse<QuestionVo> updateQuestion(UpdateQuestionRequest updateQuestionRequest);

    BaseResponse<QuestionVo> searchQuestion(SearchQuestionRequest searchQuestionRequest);

    BaseResponse<BasePageResp<QuestionVo>> searchQuestionPage(SearchQuestionListRequest searchQuestionListRequest);

    /**
     * 题目点赞
     * @param questionId
     * @param count
     * @return
     */
    BaseResponse<Long> thumbsQuestion(Long questionId,Long count);

    /**
     * 获取题目点赞数
     * @param questionId
     * @return
     */
    BaseResponse<Long> getThumbs(Long questionId);
}
