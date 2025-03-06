package com.cct.redmeatojbackend.post.service;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.post.domain.dto.post.AddPostRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostListRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.UpdatePostRequest;
import com.cct.redmeatojbackend.post.domain.vo.PostVo;

/**
 * @description 帖子表服务层
 * @author cct
 */
public interface PostService {


    /**
     * 添加帖子
     * @param addPostRequest
     * @return
     */
    BaseResponse<PostVo> addPost(AddPostRequest addPostRequest);

    /**
     * 删除帖子
     * @param questionId
     * @return
     */
    BaseResponse<Void> deletePost(Long questionId);

    /**
     * 帖子更新
     * @param updatePostRequest
     * @return
     */
    BaseResponse<PostVo> updatePost(UpdatePostRequest updatePostRequest);

    /**
     * 更具id查询帖子请求
     * @param searchPostRequest
     * @return
     */
    BaseResponse<PostVo> searchPost(SearchPostRequest searchPostRequest);


    /**
     * 帖子批量查询
     * @param searchPostListRequest
     * @return
     */
    BaseResponse<BasePageResp<PostVo>> searchPostPage(SearchPostListRequest searchPostListRequest);
}
