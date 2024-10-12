package com.cct.redmeatojbackend.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.service.RemoteCodeBoxService;
import com.cct.redmeatojbackend.coderunbox.service.RunCodeServiceManagerChain;
import com.cct.redmeatojbackend.common.constant.CommonConstant;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.question.dao.QuestionDao;
import com.cct.redmeatojbackend.question.dao.SubmitRecordDao;
import com.cct.redmeatojbackend.question.dao.mapper.SubmitRecordMapper;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.AddSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.SearchSubmitRecordListRequest;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.SearchSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.UpdateSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import com.cct.redmeatojbackend.common.domain.enums.JudgeResultEnum;
import com.cct.redmeatojbackend.question.domain.vo.SubmitRecordVo;
import com.cct.redmeatojbackend.question.service.QuestionIOService;
import com.cct.redmeatojbackend.question.service.SubmitRecordService;
import com.cct.redmeatojbackend.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author cct
 * @description 提交记录表服务层实现类
 */
@Service
public class SubmitRecordServiceImpl implements SubmitRecordService {

    @Resource
    private SubmitRecordDao submitRecordDao;

    @Resource
    private SubmitRecordMapper submitRecordMapper;

    @Resource
    private QuestionIOService questionIOService;

    @Resource
    private QuestionDao questionDao;

    @Resource
    private RunCodeServiceManagerChain runCodeServiceManagerChain;

    @Resource
    private RemoteCodeBoxService remoteCodeBoxService;

    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<SubmitRecordVo> addSubmitRecord(AddSubmitRecordRequest addSubmitRecordRequest) {

        //1.添加记录请求转为要添加的提交记录
        SubmitRecord submitRecord = addSubmitRecordRequest.toSubmitRecord();
        submitRecord.setUserId(userService.getCurrentUser().getId());

        //2.判断题目是否存在
        Long questionId = addSubmitRecordRequest.getQuestionId();
        Question existed = questionDao.getById(questionId);
        ThrowUtils.throwIf(existed == null, RespCodeEnum.OPERATION_ERROR, "题目不存在");

        //3.获取该题目的测试用例
        List<TestCase> testCaseList = questionIOService.getTestCasesByQuestionId(questionId);

        //4.调用代码运行箱运行用户提交代码
        RunCodeReq runCodeReq = RunCodeReq.builder()
                .language(addSubmitRecordRequest.getLanguage())
                .code(addSubmitRecordRequest.getSubmitContext())
                .testCases(testCaseList)
                .timeLimit(existed.getQuestionTimeLimit())
                .memoryLimit(existed.getQuestionMemLimit())
                .build();

        BaseResponse<RunCodeResp> response = remoteCodeBoxService.run(runCodeReq);
        if(response.getCode()!=RespCodeEnum.SUCCESS.getCode()){
            throw new BusinessException(RespCodeEnum.OPERATION_ERROR, response.getMessage());
        }
//        RunCodeResp runCodeResp = runCodeServiceManagerChain.runCode(runCodeReq);
        RunCodeResp runCodeResp = response.getData();

        //4.封装提交记录结果返回
        submitRecord.setId(IdUtil.getSnowflakeNextId());
        submitRecord.setJudgeResult(runCodeResp.getCode());
        submitRecord.setLastRunCase(runCodeResp.getLastTestCaseId());
        submitRecord.setLanguage(runCodeReq.getLanguage());
        submitRecord.setSubmitContext(runCodeReq.getCode());
        submitRecord.setTimeConsume(runCodeResp.getTimeConsume());
        submitRecord.setMemoryConsume(runCodeResp.getMemoryConsume());
        ThrowUtils.throwIf(!submitRecordDao.save(submitRecord), RespCodeEnum.OPERATION_ERROR, "添加提交记录失败");


        if (submitRecord.getJudgeResult() == JudgeResultEnum.SUCCESS.getCode()) {
            //增加题目ac 与 提交次数
            ThrowUtils.throwIf(!questionDao.addQuestionSubmitNumAndAcNum(questionId), RespCodeEnum.OPERATION_ERROR, "更新题目提交次数失败");
        }else{
            //增加题目提交次数
            ThrowUtils.throwIf(!questionDao.addQuestionSubmitNum(questionId), RespCodeEnum.OPERATION_ERROR, "更新题目提交次数失败");
        }

        return RespUtils.success(SubmitRecordVo.toVo(submitRecord));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deleteSubmitRecord(Long submitRecordId) {
        ThrowUtils.throwIf(submitRecordDao.getById(submitRecordId) == null, RespCodeEnum.OPERATION_ERROR, "提交记录不存在");
        ThrowUtils.throwIf(!submitRecordDao.removeById(submitRecordId), RespCodeEnum.OPERATION_ERROR, "删除提交记录失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<SubmitRecordVo> updateSubmitRecord(UpdateSubmitRecordRequest updateSubmitRecordRequest) {
        SubmitRecord oldSubmitRecord = submitRecordDao.getById(updateSubmitRecordRequest);
        ThrowUtils.throwIf(oldSubmitRecord == null, RespCodeEnum.OPERATION_ERROR, "提交记录不存在");
        BeanUtil.copyProperties(updateSubmitRecordRequest, oldSubmitRecord, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(!submitRecordDao.updateById(oldSubmitRecord), RespCodeEnum.OPERATION_ERROR, "更新提交记录失败");
        return RespUtils.success(SubmitRecordVo.toVo(oldSubmitRecord));
    }

    @Override
    public BaseResponse<SubmitRecordVo> searchSubmitRecord(SearchSubmitRecordRequest searchSubmitRecordRequest) {
        SubmitRecord submitRecord = submitRecordDao.searchSubmitRecord(searchSubmitRecordRequest);
        return RespUtils.success(SubmitRecordVo.toVo(submitRecord));
    }

    @Override
    public BaseResponse<BasePageResp<SubmitRecordVo>> searchSubmitRecordPage(SearchSubmitRecordListRequest searchSubmitRecordListRequest) {
        QueryWrapper<SubmitRecord> wrapper = new QueryWrapper<>();
        wrapper.select()
                .eq(searchSubmitRecordListRequest.getId() != null, "id", searchSubmitRecordListRequest.getId())
                .eq(searchSubmitRecordListRequest.getQuestionId() != null, "question_id", searchSubmitRecordListRequest.getQuestionId())
                .eq(searchSubmitRecordListRequest.getUserId() != null, "user_id", searchSubmitRecordListRequest.getUserId())
                .eq(searchSubmitRecordListRequest.getJudgeResult() != null, "judge_result", searchSubmitRecordListRequest.getJudgeResult())
                .eq(searchSubmitRecordListRequest.getCreateTime() != null, "create_time", searchSubmitRecordListRequest.getCreateTime())
                .orderBy(searchSubmitRecordListRequest.getSortField() != null && searchSubmitRecordListRequest.getSortOrder() != null,
                        Objects.equals(searchSubmitRecordListRequest.getSortOrder(), CommonConstant.SORT_ORDER_ASC),
                        searchSubmitRecordListRequest.getSortField());


        Page<SubmitRecord> submitRecordPage = submitRecordMapper.selectPage(searchSubmitRecordListRequest.plusPage(), wrapper);
        BasePageResp<SubmitRecord> basePageResp = BasePageResp.init(submitRecordPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, SubmitRecordVo.class));
    }
}
