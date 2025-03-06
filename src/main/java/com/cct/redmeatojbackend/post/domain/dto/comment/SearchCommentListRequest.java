package com.cct.redmeatojbackend.post.domain.dto.comment;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.post.domain.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cct
 * @description 评论表分页查询请求
 */
@ApiModel(description = "评论表分页查询请求")
@Data
public class SearchCommentListRequest extends BasePageReq implements Serializable {


    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("帖子id")
    private Long postId;


    public Comment toComment() {
        Comment comment = new Comment();
        BeanUtil.copyProperties(this, comment);
        return comment;
    }

    @Override
    public Page<Comment> plusPage() {
        return new Page<>();
    }

    private static final long serialVersionUID = 1L;
}