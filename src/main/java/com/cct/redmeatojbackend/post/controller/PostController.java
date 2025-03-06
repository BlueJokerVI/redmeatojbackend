package com.cct.redmeatojbackend.post.controller;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.post.domain.dto.post.AddPostRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostListRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.UpdatePostRequest;
import com.cct.redmeatojbackend.post.domain.vo.PostVo;
import com.cct.redmeatojbackend.post.service.PostService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @description 帖子表控制器
 * @author cct
 */
@RestController
@RequestMapping("/post")
@Api(tags = "帖子控制器")
public class PostController {

    @Resource
    PostService postService;

    @PostMapping("/add")
    @ApiOperation("帖子添加")
    BaseResponse<PostVo>  addPost(@Valid @RequestBody AddPostRequest addPostRequest){
        return postService.addPost(addPostRequest);
    }

    @GetMapping("/delete")
    @ApiOperation("帖子删除")
    BaseResponse<Void>  deletePost(@Valid @NotNull @RequestParam Long postId){
        return postService.deletePost(postId);
    }

    @PostMapping("/update")
    @ApiOperation("帖子更新")
    BaseResponse<PostVo>  updatePost(@Valid @RequestBody UpdatePostRequest updatePostRequest){
        return postService.updatePost(updatePostRequest);
    }

    @PostMapping("/search")
    @ApiOperation("帖子查询")
    BaseResponse<PostVo>  searchPost(@Valid @RequestBody SearchPostRequest searchPostRequest){
        return postService.searchPost(searchPostRequest);
    }

    @PostMapping("/searchPage")
    @ApiOperation("帖子分页查询")
    BaseResponse<BasePageResp<PostVo>> searchPostPage(@Valid @RequestBody SearchPostListRequest searchPostListRequest){
        return postService.searchPostPage(searchPostListRequest);
    }

}
