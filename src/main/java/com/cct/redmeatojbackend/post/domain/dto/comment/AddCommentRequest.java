package com.cct.redmeatojbackend.post.domain.dto.comment;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.post.domain.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cct
 * @description 评论表添加请求
 */
@Data
@ApiModel(description = "评论表添加请求")
public class AddCommentRequest implements Serializable {

    @ApiModelProperty("用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty("帖子id")
    @NotNull
    private Long postId;

    @ApiModelProperty("评论内容")
    @Max(2048)
    @NotNull
    @NotBlank
    private String comment;


    public Comment toComment() {
        Comment comment = new Comment();
        BeanUtil.copyProperties(this, comment);
        return comment;
    }


    private static final long serialVersionUID = 1L;
}