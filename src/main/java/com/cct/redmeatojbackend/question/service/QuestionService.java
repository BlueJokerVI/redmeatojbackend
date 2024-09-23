package com.cct.redmeatojbackend.question.service;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.*;
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


}
