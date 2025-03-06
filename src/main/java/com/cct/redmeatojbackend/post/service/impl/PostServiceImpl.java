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
import com.cct.redmeatojbackend.post.dao.PostDao;
import com.cct.redmeatojbackend.post.dao.mapper.PostMapper;
import com.cct.redmeatojbackend.post.domain.dto.post.AddPostRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostListRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostRequest;
import com.cct.redmeatojbackend.post.domain.dto.post.UpdatePostRequest;
import com.cct.redmeatojbackend.post.domain.entity.Post;
import com.cct.redmeatojbackend.post.domain.vo.PostVo;
import com.cct.redmeatojbackend.post.service.PostService;
import com.cct.redmeatojbackend.user.dao.UserDao;
import com.cct.redmeatojbackend.user.domain.entity.User;
import com.cct.redmeatojbackend.user.domain.vo.UserVo;
import com.cct.redmeatojbackend.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cct
 * @description 帖子表服务层实现类
 */
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostDao postDao;

    @Resource
    private PostMapper postMapper;

    @Resource
    private UserDao userDao;

    @Resource
    private UserService userService;

    @Override
    public BaseResponse<PostVo> addPost(AddPostRequest addPostRequest) {
        //1.校验用户是否存在
        User existed = userDao.getById(addPostRequest.getUserId());
        ThrowUtils.throwIf(existed == null, RespCodeEnum.PARAMS_ERROR, "用户不存在！");
        //2.保存帖子
        Post post = addPostRequest.toPost();
        post.setId(IdUtil.getSnowflakeNextId());
        ThrowUtils.throwIf(!postDao.save(post), RespCodeEnum.OPERATION_ERROR, "添加帖子失败");
        return RespUtils.success(PostVo.toVo(post));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> deletePost(Long postId) {
        //1.校验帖子是否存在
        Post post = postDao.getById(postId);
        ThrowUtils.throwIf(postDao.getById(postId) == null, RespCodeEnum.OPERATION_ERROR, "帖子不存在");
        //2.校验是否有权限删除帖子
        UserVo currentUser = userService.getCurrentUser();
        if (!post.getUserId().equals(currentUser.getId()) &&
                !currentUser.getUserRole().equals(UserRoleEnum.ADMIN_USER.getCode())) {
            throw new BusinessException(RespCodeEnum.NO_AUTH_ERROR, "无权限");
        }
        ThrowUtils.throwIf(!postDao.removeById(postId), RespCodeEnum.OPERATION_ERROR, "删除帖子失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<PostVo> updatePost(UpdatePostRequest updatePostRequest) {
        //1.校验帖子是否存在
        Post oldPost = postDao.getById(updatePostRequest);
        ThrowUtils.throwIf(oldPost == null, RespCodeEnum.OPERATION_ERROR, "帖子不存在");

        //2.校验是否有权限更新
        UserVo currentUser = userService.getCurrentUser();
        if (!oldPost.getUserId().equals(currentUser.getId()) &&
                !currentUser.getUserRole().equals(UserRoleEnum.ADMIN_USER.getCode())) {
            throw new BusinessException(RespCodeEnum.NO_AUTH_ERROR, "无权限");
        }
        //3.更新帖子
        BeanUtil.copyProperties(updatePostRequest, oldPost, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(!postDao.updateById(oldPost), RespCodeEnum.OPERATION_ERROR, "更新帖子失败");
        return RespUtils.success(PostVo.toVo(oldPost));
    }

    @Override
    public BaseResponse<PostVo> searchPost(SearchPostRequest searchPostRequest) {
        Post post = postDao.getById(searchPostRequest.getId());
        ThrowUtils.throwIf(post == null, RespCodeEnum.PARAMS_ERROR, "帖子不存在");
        return RespUtils.success(PostVo.toVo(post));
    }

    @Override
    public BaseResponse<BasePageResp<PostVo>> searchPostPage(SearchPostListRequest searchPostListRequest) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
                .eq(searchPostListRequest.getId() != null, Post::getId, searchPostListRequest.getId())
                .eq(searchPostListRequest.getUserId() != null, Post::getUserId, searchPostListRequest.getUserId())
                .like(searchPostListRequest.getTitle() != null, Post::getTitle, searchPostListRequest.getTitle())
                .like(searchPostListRequest.getContent() != null, Post::getContent, searchPostListRequest.getContent())
                .eq(searchPostListRequest.getHotScore() != null, Post::getHotScore, searchPostListRequest.getHotScore())
                .ge(searchPostListRequest.getUpdateTime() != null, Post::getUpdateTime, searchPostListRequest.getUpdateTime());

        Page<Post> postPage = postMapper.selectPage(searchPostListRequest.plusPage(), wrapper);
        BasePageResp<Post> basePageResp = BasePageResp.init(postPage);
        return RespUtils.success(basePageResp.toVo(basePageResp, PostVo.class));
    }
}
