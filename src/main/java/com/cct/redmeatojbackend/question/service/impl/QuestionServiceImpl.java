package com.cct.redmeatojbackend.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.question.dao.QuestionDao;
import com.cct.redmeatojbackend.question.dao.mapper.QuestionMapper;
import com.cct.redmeatojbackend.question.domain.dto.question.AddQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.question.SearchQuestionListRequest;
import com.cct.redmeatojbackend.question.domain.dto.question.SearchQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.question.UpdateQuestionRequest;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import com.cct.redmeatojbackend.question.domain.vo.QuestionVo;
import com.cct.redmeatojbackend.question.service.QuestionIOService;
import com.cct.redmeatojbackend.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cct
 * @description 题目表服务层实现类
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDao questionDao;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionIOService questionIOService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<QuestionVo> addQuestion(AddQuestionRequest addQuestionRequest) {
        Question question = addQuestionRequest.toQuestion();
        //1.检测题目是否已经存在
        Question existed =  questionDao.searchQuestionByName(question.getQuestionName());
        ThrowUtils.throwIf(existed != null, RespCodeEnum.OPERATION_ERROR, "题目名称已存在");
        question.setId(IdUtil.getSnowflakeNextId());
        //2.保存题目
        ThrowUtils.throwIf(!questionDao.save(question), RespCodeEnum.OPERATION_ERROR, "添加题目失败");
        //3.将输入输出样例添加到改题目的判题用例
        try {
            List<TestCase> testCases = question.getQuestionIoExample();
            for (TestCase testCase : testCases) {
                questionIOService.upLoadQuestionIOFile(question.getId(), testCase);
            }
        } catch (Exception e) {
            log.error("添加题目时，上传输入输出样例失败", e);
            throw new BusinessException(RespCodeEnum.OPERATION_ERROR, "添加题目时，上传输入输出样例失败");
        }
        return RespUtils.success(QuestionVo.toVo(question));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deleteQuestion(Long questionId) {
        //1.校验题目是否存在
        ThrowUtils.throwIf(questionDao.getById(questionId) == null, RespCodeEnum.OPERATION_ERROR, "题目不存在");
        ThrowUtils.throwIf(!questionDao.removeById(questionId), RespCodeEnum.OPERATION_ERROR, "删除题目失败");
        //2.删除题目对应的输入输出样例
        try {
            questionIOService.deleteTestCase(questionId);
        } catch (Exception e) {
            log.error("删除题目时，删除输入输出样例失败", e);
            throw new BusinessException(RespCodeEnum.OPERATION_ERROR, "删除题目时，删除输入输出样例失败");
        }
        return RespUtils.success();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<QuestionVo> updateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        //1.判断题目是否存在
        Question oldQuestion = questionDao.getById(updateQuestionRequest);
        ThrowUtils.throwIf(oldQuestion == null, RespCodeEnum.OPERATION_ERROR, "题目不存在");
        BeanUtil.copyProperties(updateQuestionRequest, oldQuestion, CopyOptions.create().setIgnoreNullValue(true));

        //2.如果要跟新的名字是否已经存在
        if(updateQuestionRequest.getQuestionName() != null) {
            Question existed = questionDao.searchQuestionByName(updateQuestionRequest.getQuestionName());
            ThrowUtils.throwIf(existed != null && !existed.getId().equals(updateQuestionRequest.getId()), RespCodeEnum.OPERATION_ERROR, "题目名称已存在");
        }

        //3.更新题目
        ThrowUtils.throwIf(!questionDao.updateById(oldQuestion), RespCodeEnum.OPERATION_ERROR, "更新题目失败");

        //如果testCases字段存在还要更新题目测试用例
        List<TestCase> testCases = updateQuestionRequest.getTestCases();
        if (testCases != null) {
            for (TestCase testCase : testCases) {
                try {
                    questionIOService.upLoadQuestionIOFile(updateQuestionRequest.getId(), testCase);
                } catch (Exception e) {
                    log.error("更新题目时，上传输入输出样例失败", e);
                    throw new BusinessException(RespCodeEnum.OPERATION_ERROR, "更新题目时，上传输入输出样例失败");
                }
            }
        }
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
                .like(searchQuestionListRequest.getQuestionTags() != null, Question::getQuestionTags, searchQuestionListRequest.getQuestionTags());
        Page<Question> questionPage = questionMapper.selectPage(searchQuestionListRequest.plusPage(), wrapper);
        BasePageResp<Question> basePageResp = BasePageResp.init(questionPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, QuestionVo.class));
    }


}
