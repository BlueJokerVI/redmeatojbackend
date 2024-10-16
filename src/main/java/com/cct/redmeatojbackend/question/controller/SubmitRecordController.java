package com.cct.redmeatojbackend.question.controller;

import com.cct.redmeatojbackend.common.annotation.Limit;
import com.cct.redmeatojbackend.common.annotation.Login;
import com.cct.redmeatojbackend.common.annotation.RoleAccess;
import com.cct.redmeatojbackend.common.domain.enums.UserRoleEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.AddSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.SearchSubmitRecordListRequest;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.SearchSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.dto.submitrecord.UpdateSubmitRecordRequest;
import com.cct.redmeatojbackend.question.domain.vo.SubmitRecordVo;
import com.cct.redmeatojbackend.question.service.SubmitRecordService;
import com.cct.redmeatojbackend.user.service.UserService;
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
    private SubmitRecordService submitRecordService;

    @PostMapping("/add")
    @ApiOperation("提交记录添加")
    @Login
    @Limit(keyPrefix = "com.cct.redmeatojbackend.question.controller.addSubmitRecord",count = 1,interval = 1)
    BaseResponse<SubmitRecordVo>  addSubmitRecord(@Valid @RequestBody AddSubmitRecordRequest addSubmitRecordRequest){
        return submitRecordService.addSubmitRecord(addSubmitRecordRequest);
    }

    @PostMapping("/addMQ")
    @ApiOperation("提交记录添加MQ")
    @Login
    @Limit(keyPrefix = "com.cct.redmeatojbackend.question.controller.addSubmitRecord",count = 1,interval = 1)
    BaseResponse<Long> addSubmitRecordWithMQ(@Valid @RequestBody AddSubmitRecordRequest addSubmitRecordRequest){
        return submitRecordService.addSubmitRecordWithMQ(addSubmitRecordRequest);
    }

    @GetMapping("/delete")
    @ApiOperation("提交记录删除")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<Void>  deleteSubmitRecord(@Valid @NotNull @RequestParam Long submitRecordId){
        return submitRecordService.deleteSubmitRecord(submitRecordId);
    }

    @PostMapping("/update")
    @ApiOperation("提交记录更新")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<SubmitRecordVo>  updateSubmitRecord(@Valid @RequestBody UpdateSubmitRecordRequest updateSubmitRecordRequest){
        return submitRecordService.updateSubmitRecord(updateSubmitRecordRequest);
    }

    @PostMapping("/search")
    @ApiOperation("提交记录查询")
    @Login
    BaseResponse<SubmitRecordVo>  searchSubmitRecord(@Valid @RequestBody SearchSubmitRecordRequest searchSubmitRecordRequest){
        return submitRecordService.searchSubmitRecord(searchSubmitRecordRequest);
    }

    @PostMapping("/searchPage")
    @ApiOperation("提交记录分页查询")
    @Login
    BaseResponse<BasePageResp<SubmitRecordVo>> searchSubmitRecordPage(@Valid @RequestBody SearchSubmitRecordListRequest searchSubmitRecordListRequest){
        return submitRecordService.searchSubmitRecordPage(searchSubmitRecordListRequest);
    }

}
