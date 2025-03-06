package com.cct.redmeatojbackend.post.controller;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.post.domain.dto.comment.AddCommentRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentListRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.UpdateCommentRequest;
import com.cct.redmeatojbackend.post.domain.vo.CommentVo;
import com.cct.redmeatojbackend.post.service.CommentService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @description 评论表控制器
 * @author cct
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论控制器")
public class CommentController {

    @Resource
    CommentService commentService;

    @PostMapping("/add")
    @ApiOperation("评论添加")
    BaseResponse<CommentVo>  addComment(@Valid @RequestBody AddCommentRequest addCommentRequest){
        return commentService.addComment(addCommentRequest);
    }

    @GetMapping("/delete")
    @ApiOperation("评论删除")
    BaseResponse<Void>  deleteComment(@Valid @NotNull @RequestParam Long commentId){
        return commentService.deleteComment(commentId);
    }

    @PostMapping("/update")
    @ApiOperation("评论更新")
    BaseResponse<CommentVo>  updateComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest){
        return commentService.updateComment(updateCommentRequest);
    }

    @PostMapping("/search")
    @ApiOperation("评论查询")
    BaseResponse<CommentVo>  searchComment(@Valid @RequestBody SearchCommentRequest searchCommentRequest){
        return commentService.searchComment(searchCommentRequest);
    }

    @PostMapping("/searchPage")
    @ApiOperation("评论分页查询")
    BaseResponse<BasePageResp<CommentVo>> searchCommentPage(@Valid @RequestBody SearchCommentListRequest searchCommentListRequest){
        return commentService.searchCommentPage(searchCommentListRequest);
    }

}
