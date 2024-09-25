package com.cct.redmeatojbackend.question.controller;

import com.cct.redmeatojbackend.common.annotation.Login;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.AddSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchSubmitRecordListRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.UpdateSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.vo.SubmitRecordVo;
import com.cct.redmeatojbackend.question.service.SubmitRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @description 提交记录表控制器
 * @author cct
 */
@RestController
@RequestMapping("/submitRecord")
@Api(tags = "提交记录控制器")
public class SubmitRecordController {

    @Resource
    SubmitRecordService submitRecordService;

    @PostMapping("/add")
    @ApiOperation("提交记录添加")
    @Login
    BaseResponse<SubmitRecordVo>  addSubmitRecord(@Valid @RequestBody AddSubmitRecordRequest addSubmitRecordRequest){
        return submitRecordService.addSubmitRecord(addSubmitRecordRequest);
    }

    @GetMapping("/delete")
    @ApiOperation("提交记录删除")
    BaseResponse<Void>  deleteSubmitRecord(@Valid @NotNull @RequestParam Long submitRecordId){
        return submitRecordService.deleteSubmitRecord(submitRecordId);
    }

    @PostMapping("/update")
    @ApiOperation("提交记录更新")
    BaseResponse<SubmitRecordVo>  updateSubmitRecord(@Valid @RequestBody UpdateSubmitRecordRequest updateSubmitRecordRequest){
        return submitRecordService.updateSubmitRecord(updateSubmitRecordRequest);
    }

    @PostMapping("/search")
    @ApiOperation("提交记录查询")
    BaseResponse<SubmitRecordVo>  searchSubmitRecord(@Valid @RequestBody SearchSubmitRecordRequest searchSubmitRecordRequest){
        return submitRecordService.searchSubmitRecord(searchSubmitRecordRequest);
    }

    @PostMapping("/searchPage")
    @ApiOperation("提交记录分页查询")
    BaseResponse<BasePageResp<SubmitRecordVo>> searchSubmitRecordPage(@Valid @RequestBody SearchSubmitRecordListRequest searchSubmitRecordListRequest){
        return submitRecordService.searchSubmitRecordPage(searchSubmitRecordListRequest);
    }

}
