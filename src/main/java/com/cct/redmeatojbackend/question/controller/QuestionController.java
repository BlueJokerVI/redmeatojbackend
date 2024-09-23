package com.cct.redmeatojbackend.question.controller;

import com.cct.redmeatojbackend.common.annotation.RoleAccess;
import com.cct.redmeatojbackend.common.domain.enums.UserRoleEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.*;
import com.cct.redmeatojbackend.question.domain.vo.QuestionVo;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import com.cct.redmeatojbackend.question.service.QuestionIOService;
import com.cct.redmeatojbackend.question.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @description 题目表控制器
 * @author cct
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    QuestionService questionService;

    @Resource
    QuestionIOService questionIOService;


    @PostMapping("/add")
    BaseResponse<QuestionVo>  addQuestion(@Valid @RequestBody AddQuestionRequest addQuestionRequest){
        return questionService.addQuestion(addQuestionRequest);
    }

    @GetMapping("/delete")
    BaseResponse<Void>  deleteQuestion(@Valid @NotNull @RequestParam Long questionId){
        return questionService.deleteQuestion(questionId);
    }

    @PostMapping("/update")
    BaseResponse<QuestionVo>  updateQuestion(@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest){
        return questionService.updateQuestion(updateQuestionRequest);
    }

    @PostMapping("/search")
    BaseResponse<QuestionVo>  searchQuestion(@Valid @RequestBody SearchQuestionRequest searchQuestionRequest){
        return questionService.searchQuestion(searchQuestionRequest);
    }

    @PostMapping("/searchPage")
    BaseResponse<BasePageResp<QuestionVo>> searchQuestionPage(@Valid @RequestBody SearchQuestionListRequest searchQuestionListRequest){
        return questionService.searchQuestionPage(searchQuestionListRequest);
    }

    @PostMapping("/getTestCasePage")
    @RoleAccess(role = UserRoleEnum.ADMIN_USER)
    BaseResponse<BasePageResp<TestCase>> getTestCasePage(@Valid @RequestBody GetTestCasePageRequest getTestCasePageRequest){
        return questionIOService.getTestCasePage(getTestCasePageRequest);
    }


}
