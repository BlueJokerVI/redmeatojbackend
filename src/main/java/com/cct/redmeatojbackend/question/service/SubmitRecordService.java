package com.cct.redmeatojbackend.question.service;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.AddSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchSubmitRecordListRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.UpdateSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.vo.SubmitRecordVo;

/**
 * @description 提交记录表服务层
 * @author cct
 */
public interface SubmitRecordService {

    BaseResponse<SubmitRecordVo> addSubmitRecord(AddSubmitRecordRequest addSubmitRecordRequest);

    BaseResponse<Void> deleteSubmitRecord(Long questionId);

    BaseResponse<SubmitRecordVo> updateSubmitRecord(UpdateSubmitRecordRequest updateSubmitRecordRequest);

    BaseResponse<SubmitRecordVo> searchSubmitRecord(SearchSubmitRecordRequest searchSubmitRecordRequest);

    BaseResponse<BasePageResp<SubmitRecordVo>> searchSubmitRecordPage(SearchSubmitRecordListRequest searchSubmitRecordListRequest);
}
