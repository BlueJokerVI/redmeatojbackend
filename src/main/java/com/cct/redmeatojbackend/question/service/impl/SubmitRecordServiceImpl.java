package com.cct.redmeatojbackend.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.service.RunCodeServiceManagerChain;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
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
import com.cct.redmeatojbackend.question.domain.enums.JudgeResultEnum;
import com.cct.redmeatojbackend.question.domain.vo.SubmitRecordVo;
import com.cct.redmeatojbackend.question.service.QuestionIOService;
import com.cct.redmeatojbackend.question.service.SubmitRecordService;
import com.cct.redmeatojbackend.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private UserService userService;

    @Override
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

        //统计最大测试内存与时间消耗
        int maxTimeConsume = Integer.MIN_VALUE;
        int maxMemConsume = Integer.MIN_VALUE;
        //用于标记是否该题目通过
        boolean normal = true;
        //遍历测试用例，逐个运行
        for (TestCase testCase : testCaseList) {
            RunCodeReq runCodeReq = RunCodeReq.builder()
                    .language(addSubmitRecordRequest.getLanguage())
                    .code(addSubmitRecordRequest.getSubmitContext())
                    .inputContent(testCase.getInputContent())
                    .timeLimit(existed.getQuestionTimeLimit())
                    .memoryLimit(existed.getQuestionMemLimit())
                    .build();
            RunCodeResp runCodeResp = runCodeServiceManagerChain.runCode(runCodeReq);
            if (!Objects.equals(runCodeResp.getCode(), RespCodeEnum.SUCCESS.getCode())) {
                //代码没能正常运行结束
                JudgeResultEnum resultEnum = JudgeResultEnum.getEnumByCode(runCodeResp.getCode());
                submitRecord.setJudgeResult(Optional.ofNullable(resultEnum).orElse(JudgeResultEnum.RUNTIME_ERROR).getCode());
                submitRecord.setLastRunCase(testCase.getTestCaseId());
                normal = false;
                break;
            }

            //代码正常运行结束，判断输出是否正确
            if (!Objects.equals(runCodeResp.getOutputContext(), testCase.getOutputContent())) {
                //输出与预取不符
                submitRecord.setJudgeResult(JudgeResultEnum.ANSWER_ERROR.getCode());
                submitRecord.setLastRunCase(testCase.getTestCaseId());
                normal = false;
                break;
            }

            if (runCodeResp.getMemoryConsume() > maxMemConsume) {
                maxMemConsume = runCodeResp.getMemoryConsume();
            }
            if (runCodeResp.getTimeConsume() > maxTimeConsume) {
                maxTimeConsume = runCodeResp.getTimeConsume();
            }
        }

        if (normal) {
            //所有测试用例均通过
            submitRecord.setJudgeResult(JudgeResultEnum.SUCCESS.getCode());
            submitRecord.setMemoryConsume(maxMemConsume);
            submitRecord.setLastRunCase(testCaseList.get(testCaseList.size() - 1).getTestCaseId());
            submitRecord.setTimeConsume(maxTimeConsume);
        }

        //4.封装提交记录结果返回
        submitRecord.setId(IdUtil.getSnowflakeNextId());
        ThrowUtils.throwIf(!submitRecordDao.save(submitRecord), RespCodeEnum.OPERATION_ERROR, "添加提交记录失败");
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
        LambdaQueryWrapper<SubmitRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
                .eq(searchSubmitRecordListRequest.getId() != null, SubmitRecord::getId, searchSubmitRecordListRequest.getId())
                .eq(searchSubmitRecordListRequest.getQuestionId() != null, SubmitRecord::getQuestionId, searchSubmitRecordListRequest.getQuestionId())
                .eq(searchSubmitRecordListRequest.getUserId() != null, SubmitRecord::getUserId, searchSubmitRecordListRequest.getUserId())
                .eq(searchSubmitRecordListRequest.getJudgeResult() != null, SubmitRecord::getJudgeResult, searchSubmitRecordListRequest.getJudgeResult())
                .eq(searchSubmitRecordListRequest.getCreateTime() != null, SubmitRecord::getCreateTime, searchSubmitRecordListRequest.getCreateTime());
        Page<SubmitRecord> submitRecordPage = submitRecordMapper.selectPage(searchSubmitRecordListRequest.plusPage(), wrapper);
        BasePageResp<SubmitRecord> basePageResp = BasePageResp.init(submitRecordPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, SubmitRecordVo.class));
    }
}
