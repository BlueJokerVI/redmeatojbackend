package com.cct.redmeatojbackend.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.domain.enums.UserRoleEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.post.dao.CommentDao;
import com.cct.redmeatojbackend.post.dao.PostDao;
import com.cct.redmeatojbackend.post.dao.mapper.CommentMapper;
import com.cct.redmeatojbackend.post.domain.dto.comment.AddCommentRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentListRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentRequest;
import com.cct.redmeatojbackend.post.domain.dto.comment.UpdateCommentRequest;
import com.cct.redmeatojbackend.post.domain.entity.Comment;
import com.cct.redmeatojbackend.post.domain.entity.Post;
import com.cct.redmeatojbackend.post.domain.vo.CommentVo;
import com.cct.redmeatojbackend.post.service.CommentService;
import com.cct.redmeatojbackend.user.dao.UserDao;
import com.cct.redmeatojbackend.user.domain.entity.User;
import com.cct.redmeatojbackend.user.domain.vo.UserVo;
import com.cct.redmeatojbackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cct
 * @description 评论表服务层实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Override
    public BaseResponse<CommentVo> addComment(AddCommentRequest addCommentRequest) {
        //1.校验帖子是否存在
        Post existedPost = postDao.getById(addCommentRequest.getPostId());
        ThrowUtils.throwIf(existedPost == null, RespCodeEnum.PARAMS_ERROR, "帖子不存在");
        //2.校验用户是否存在
        User existedUser = userDao.getById(addCommentRequest.getUserId());
        ThrowUtils.throwIf(existedUser == null, RespCodeEnum.PARAMS_ERROR, "用户不存在");
        //3.添加评论
        Comment comment = addCommentRequest.toComment();
        comment.setId(IdUtil.getSnowflakeNextId());
        ThrowUtils.throwIf(!commentDao.save(comment), RespCodeEnum.OPERATION_ERROR, "添加评论失败");
        return RespUtils.success(CommentVo.toVo(comment));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deleteComment(Long commentId) {
        //1.校验评论是否存在
        Comment existed = commentDao.getById(commentId);
        ThrowUtils.throwIf(existed == null, RespCodeEnum.PARAMS_ERROR, "评论不存在");
        //2.校验是否有权限删除
        UserVo currentUser = userService.getCurrentUser();
        if (!existed.getUserId().equals(currentUser.getId()) &&
                !currentUser.getUserRole().equals(UserRoleEnum.ADMIN_USER.getCode())
        ) {
            throw new BusinessException(RespCodeEnum.NO_AUTH_ERROR, "无权限");
        }
        //3.删除评论
        ThrowUtils.throwIf(!commentDao.removeById(commentId), RespCodeEnum.OPERATION_ERROR, "删除评论失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<CommentVo> updateComment(UpdateCommentRequest updateCommentRequest) {
        //1.校验评论是否存在
        Comment oldComment = commentDao.getById(updateCommentRequest);
        ThrowUtils.throwIf(oldComment == null, RespCodeEnum.OPERATION_ERROR, "评论不存在");
        //2.校验是否有权限
        UserVo currentUser = userService.getCurrentUser();
        if (!oldComment.getUserId().equals(currentUser.getId()) &&
                !currentUser.getUserRole().equals(UserRoleEnum.ADMIN_USER.getCode())) {
            throw new BusinessException(RespCodeEnum.NO_AUTH_ERROR, "无权限");
        }
        //3.更新评论
        BeanUtil.copyProperties(updateCommentRequest, oldComment, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(!commentDao.updateById(oldComment), RespCodeEnum.OPERATION_ERROR, "更新评论失败");
        return RespUtils.success(CommentVo.toVo(oldComment));
    }

    @Override
    public BaseResponse<CommentVo> searchComment(SearchCommentRequest searchCommentRequest) {
        Comment comment = commentDao.getById(searchCommentRequest.getId());
        ThrowUtils.throwIf(comment == null, RespCodeEnum.PARAMS_ERROR, "评论不存在");
        return RespUtils.success(CommentVo.toVo(comment));
    }

    @Override
    public BaseResponse<BasePageResp<CommentVo>> searchCommentPage(SearchCommentListRequest searchCommentListRequest) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
                .eq(searchCommentListRequest.getId() != null, Comment::getId, searchCommentListRequest.getId())
                .eq(searchCommentListRequest.getUserId() != null, Comment::getUserId, searchCommentListRequest.getUserId())
                .eq(searchCommentListRequest.getPostId() != null, Comment::getPostId, searchCommentListRequest.getPostId())
                .orderBy(true, false, Comment::getThumbsUpNums)
                .orderBy(true, false, Comment::getCreateTime);
        Page<Comment> commentPage = commentMapper.selectPage(searchCommentListRequest.plusPage(), wrapper);
        BasePageResp<Comment> basePageResp = BasePageResp.init(commentPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, CommentVo.class));
    }
}
