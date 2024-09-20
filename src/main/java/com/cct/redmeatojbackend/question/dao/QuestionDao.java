package com.cct.redmeatojbackend.question.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.question.dao.mapper.QuestionMapper;
import com.cct.redmeatojbackend.question.domain.dto.SearchQuestionRequest;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import org.springframework.stereotype.Repository;

/**
* @description 题目表数据库操作层
* @author cct
*/
@Repository
public class QuestionDao extends ServiceImpl<QuestionMapper, Question> {


    public Question searchQuestion(SearchQuestionRequest searchQuestionRequest) {
        return lambdaQuery().select()
                            .eq(searchQuestionRequest.getId() != null, Question::getId, searchQuestionRequest.getId())
                            .eq(searchQuestionRequest.getQuestionName() != null, Question::getQuestionName, searchQuestionRequest.getQuestionName())
                            .eq(searchQuestionRequest.getQuestionIoExample() != null, Question::getQuestionIoExample, searchQuestionRequest.getQuestionIoExample())
                            .eq(searchQuestionRequest.getQuestionDesc() != null, Question::getQuestionDesc, searchQuestionRequest.getQuestionDesc())
                            .eq(searchQuestionRequest.getQuestionTags() != null, Question::getQuestionTags, searchQuestionRequest.getQuestionTags())
                            .eq(searchQuestionRequest.getQuestionMemLimit() != null, Question::getQuestionMemLimit, searchQuestionRequest.getQuestionMemLimit())
                            .eq(searchQuestionRequest.getQuestionTimeLimit() != null, Question::getQuestionTimeLimit, searchQuestionRequest.getQuestionTimeLimit())
                            .eq(searchQuestionRequest.getQuestionSubmitNum() != null, Question::getQuestionSubmitNum, searchQuestionRequest.getQuestionSubmitNum())
                            .eq(searchQuestionRequest.getQuestionAcNum() != null, Question::getQuestionAcNum, searchQuestionRequest.getQuestionAcNum())
                            .eq(searchQuestionRequest.getCreateTime() != null, Question::getCreateTime, searchQuestionRequest.getCreateTime())
                            .eq(searchQuestionRequest.getUpdateTime() != null, Question::getUpdateTime, searchQuestionRequest.getUpdateTime())
                            .eq(searchQuestionRequest.getIsDelete() != null, Question::getIsDelete, searchQuestionRequest.getIsDelete())
                            .last("limit 1").one();
    }
}




