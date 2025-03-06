package com.cct.redmeatojbackend.post.service;

import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.post.domain.dto.comment.AddCommentRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentListRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.UpdateCommentRequest;
import com.cct.redmeatojbackend.post.domain.vo.CommentVo;

/**
 * @description 评论表服务层
 * @author cct
 */
public interface CommentService {

    /**
     * 给帖子添加评论
     * @param addCommentRequest
     * @return
     */
    BaseResponse<CommentVo> addComment(AddCommentRequest addCommentRequest);

    /**
     * 删除评论
     * @param questionId
     * @return
     */
    BaseResponse<Void> deleteComment(Long questionId);

    /**
     * 更新评论
     * @param updateCommentRequest
     * @return
     */
    BaseResponse<CommentVo> updateComment(UpdateCommentRequest updateCommentRequest);

    /**
     * 更具id查询评论
     * @param searchCommentRequest
     * @return
     */
    BaseResponse<CommentVo> searchComment(SearchCommentRequest searchCommentRequest);


    /**
     * 分页查询评论
     * @param searchCommentListRequest
     * @return
     */
    BaseResponse<BasePageResp<CommentVo>> searchCommentPage(SearchCommentListRequest searchCommentListRequest);
}
