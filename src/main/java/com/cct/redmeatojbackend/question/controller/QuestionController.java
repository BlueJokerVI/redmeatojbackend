package com.cct.redmeatojbackend.question.controller;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.question.domain.dto.AddQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchQuestionListRequest;
import com.cct.redmeatojbackend.question.domain.dto.SearchQuestionRequest;
import com.cct.redmeatojbackend.question.domain.dto.UpdateQuestionRequest;
import com.cct.redmeatojbackend.question.domain.vo.QuestionVo;
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

}
