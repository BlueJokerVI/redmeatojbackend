package com.cct.redmeatojbackend.question.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.question.dao.mapper.SubmitRecordMapper;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.SearchSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import org.springframework.stereotype.Repository;

/**
* @description 提交记录表数据库操作层
* @author cct
*/
@Repository
public class SubmitRecordDao extends ServiceImpl<SubmitRecordMapper, SubmitRecord> {


    public SubmitRecord searchSubmitRecord(SearchSubmitRecordRequest searchSubmitRecordRequest) {
        return lambdaQuery().select()
                            .eq(searchSubmitRecordRequest.getId() != null, SubmitRecord::getId, searchSubmitRecordRequest.getId())
                            .eq(searchSubmitRecordRequest.getQuestionId() != null, SubmitRecord::getQuestionId, searchSubmitRecordRequest.getQuestionId())
                            .eq(searchSubmitRecordRequest.getUserId() != null, SubmitRecord::getUserId, searchSubmitRecordRequest.getUserId())
                            .eq(searchSubmitRecordRequest.getJudgeResult() != null, SubmitRecord::getJudgeResult, searchSubmitRecordRequest.getJudgeResult())
                            .ge(searchSubmitRecordRequest.getCreateTime() != null, SubmitRecord::getCreateTime, searchSubmitRecordRequest.getCreateTime())
                            .last("limit 1").one();
    }
}




