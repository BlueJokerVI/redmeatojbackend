package com.cct.redmeatojbackend.question.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.question.dao.mapper.QuestionMapper;
import com.cct.redmeatojbackend.question.domain.dto.question.SearchQuestionRequest;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import org.springframework.stereotype.Repository;

/**
 * @author cct
 * @description 题目表数据库操作层
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

    public boolean addQuestionSubmitNumAndAcNum(Long questionId) {
        return lambdaUpdate().setSql("question_submit_num = question_submit_num + 1 , question_ac_num = question_ac_num + 1").eq(Question::getId, questionId).update();
    }

    public boolean addQuestionSubmitNum(Long questionId) {
        return lambdaUpdate().setSql("question_submit_num = question_submit_num + 1").eq(Question::getId, questionId).update();
    }

}




