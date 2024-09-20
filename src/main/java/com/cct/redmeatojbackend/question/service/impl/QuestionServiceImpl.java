package com.cct.redmeatojbackend.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.question.dao.QuestionDao;
import com.cct.redmeatojbackend.question.dao.mapper.QuestionMapper;
import com.cct.redmeatojbackend.question.domain.dto.AddQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchQuestionListRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.UpdateQuestionRequest;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import com.cct.redmeatojbackend.question.domain.vo.QuestionVo;
import com.cct.redmeatojbackend.question.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
/**
 * @description 题目表服务层实现类
 * @author cct
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDao questionDao;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public BaseResponse<QuestionVo> addQuestion(AddQuestionRequest addQuestionRequest) {
        Question question = addQuestionRequest.toQuestion();
        question.setId(IdUtil.getSnowflakeNextId());
        ThrowUtils.throwIf(!questionDao.save(question), RespCodeEnum.OPERATION_ERROR, "添加题目失败");
        //将输入输出样例添加到改题目的判题用例
        String questionIoExample = question.getQuestionIoExample();

        return RespUtils.success(QuestionVo.toVo(question));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deleteQuestion(Long questionId) {
        ThrowUtils.throwIf(questionDao.getById(questionId) == null, RespCodeEnum.OPERATION_ERROR, "题目不存在");
        ThrowUtils.throwIf(!questionDao.removeById(questionId), RespCodeEnum.OPERATION_ERROR, "删除题目失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<QuestionVo> updateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        Question oldQuestion = questionDao.getById(updateQuestionRequest);
        ThrowUtils.throwIf(oldQuestion == null, RespCodeEnum.OPERATION_ERROR, "题目不存在");
        BeanUtil.copyProperties(updateQuestionRequest, oldQuestion, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(questionDao.save(oldQuestion), RespCodeEnum.OPERATION_ERROR, "更新题目失败");
        return RespUtils.success(QuestionVo.toVo(oldQuestion));
    }

    @Override
    public BaseResponse<QuestionVo> searchQuestion(SearchQuestionRequest searchQuestionRequest) {
        Question question = questionDao.searchQuestion(searchQuestionRequest);
        return RespUtils.success(QuestionVo.toVo(question));
    }

    @Override
    public BaseResponse<BasePageResp<QuestionVo>> searchQuestionPage(SearchQuestionListRequest searchQuestionListRequest) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
               .eq(searchQuestionListRequest.getId() != null, Question::getId, searchQuestionListRequest.getId())
               .eq(searchQuestionListRequest.getQuestionName() != null, Question::getQuestionName, searchQuestionListRequest.getQuestionName())
               .eq(searchQuestionListRequest.getQuestionIoExample() != null, Question::getQuestionIoExample, searchQuestionListRequest.getQuestionIoExample())
               .eq(searchQuestionListRequest.getQuestionDesc() != null, Question::getQuestionDesc, searchQuestionListRequest.getQuestionDesc())
               .eq(searchQuestionListRequest.getQuestionTags() != null, Question::getQuestionTags, searchQuestionListRequest.getQuestionTags())
               .eq(searchQuestionListRequest.getQuestionMemLimit() != null, Question::getQuestionMemLimit, searchQuestionListRequest.getQuestionMemLimit())
               .eq(searchQuestionListRequest.getQuestionTimeLimit() != null, Question::getQuestionTimeLimit, searchQuestionListRequest.getQuestionTimeLimit())
               .eq(searchQuestionListRequest.getQuestionSubmitNum() != null, Question::getQuestionSubmitNum, searchQuestionListRequest.getQuestionSubmitNum())
               .eq(searchQuestionListRequest.getQuestionAcNum() != null, Question::getQuestionAcNum, searchQuestionListRequest.getQuestionAcNum())
               .eq(searchQuestionListRequest.getCreateTime() != null, Question::getCreateTime, searchQuestionListRequest.getCreateTime())
               .eq(searchQuestionListRequest.getUpdateTime() != null, Question::getUpdateTime, searchQuestionListRequest.getUpdateTime())
               .eq(searchQuestionListRequest.getIsDelete() != null, Question::getIsDelete, searchQuestionListRequest.getIsDelete());
        Page<Question> questionPage = questionMapper.selectPage(searchQuestionListRequest.plusPage(), wrapper);
        BasePageResp<Question> basePageResp = BasePageResp.init(questionPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, QuestionVo.class));
    }
}
