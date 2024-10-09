package com.cct.redmeatojbackend.question.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.question.dao.mapper.QuestionMapper;
import com.cct.redmeatojbackend.question.domain.dto.question.SearchQuestionRequest;
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
                            .eq(StrUtil.isNotBlank(searchQuestionRequest.getQuestionName()), Question::getQuestionName, searchQuestionRequest.getQuestionName())
                            .like(searchQuestionRequest.getQuestionTags() != null, Question::getQuestionTags, searchQuestionRequest.getQuestionTags())
                            .last("limit 1").one();
    }

    public Question searchQuestionByName(String questionName) {
        return lambdaQuery().eq(Question::getQuestionName, questionName).one();
    }
}




