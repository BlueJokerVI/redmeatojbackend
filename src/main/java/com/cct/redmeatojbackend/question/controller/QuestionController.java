package com.cct.redmeatojbackend.question.controller;

import com.cct.redmeatojbackend.common.annotation.RoleAccess;
import com.cct.redmeatojbackend.common.domain.enums.UserRoleEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.question.domain.dto.*;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import com.cct.redmeatojbackend.question.domain.vo.QuestionVo;
import com.cct.redmeatojbackend.question.service.QuestionIOService;
import com.cct.redmeatojbackend.question.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author cct
 * @description 题目表控制器
 */
@RestController
@RequestMapping("/question")
@Api(tags = "题目控制器")
public class QuestionController {

    @Resource
    QuestionService questionService;

    @Resource
    QuestionIOService questionIOService;


    @PostMapping("/add")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    @ApiOperation("题目添加")
    BaseResponse<QuestionVo> addQuestion(@Valid @RequestBody AddQuestionRequest addQuestionRequest) {
        return questionService.addQuestion(addQuestionRequest);
    }

    @GetMapping("/delete")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    @ApiOperation("题目删除")
    BaseResponse<Void> deleteQuestion(@Valid @NotNull @RequestParam Long questionId) {
        return questionService.deleteQuestion(questionId);
    }

    @PostMapping("/update")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    @ApiOperation("题目更新")
    BaseResponse<QuestionVo> updateQuestion(@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest) {
        return questionService.updateQuestion(updateQuestionRequest);
    }

    @PostMapping("/search")
    @ApiOperation("题目搜索")
    BaseResponse<QuestionVo> searchQuestion(@Valid @RequestBody SearchQuestionRequest searchQuestionRequest) {
        return questionService.searchQuestion(searchQuestionRequest);
    }

    @PostMapping("/searchPage")
    @ApiOperation("题目分页搜索")
    BaseResponse<BasePageResp<QuestionVo>> searchQuestionPage(@Valid @RequestBody SearchQuestionListRequest searchQuestionListRequest) {
        return questionService.searchQuestionPage(searchQuestionListRequest);
    }

    @PostMapping("/getTestCasePage")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    @ApiOperation("测试用例分页搜索")
    BaseResponse<BasePageResp<TestCase>> getTestCasePage(@Valid @RequestBody GetTestCasePageRequest getTestCasePageRequest) {
        return questionIOService.getTestCasePage(getTestCasePageRequest);
    }

    @PostMapping("/deleteTestCase")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    @ApiOperation("测试用例删除")
    BaseResponse<Void> deleteTestCase(@Valid @RequestBody DeleteTestCaseRequest deleteTestCaseRequest) {
        questionIOService.deleteTestCase(deleteTestCaseRequest.getQuestionId(), deleteTestCaseRequest.getTestCaseId());
        return RespUtils.success();
    }

}
