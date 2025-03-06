package com.cct.redmeatojbackend.post.domain.dto.post;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.post.domain.entity.Post;
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
 * @description 帖子表添加请求
 */
@Data
@ApiModel(description = "帖子表添加请求")
public class AddPostRequest implements Serializable {


    @ApiModelProperty("用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty("帖子名称")
    @NotNull
    @NotBlank
    private String title;

    @ApiModelProperty("帖子内容")
    @NotBlank
    @NotNull
    private String content;


    public Post toPost() {
        Post post = new Post();
        BeanUtil.copyProperties(this, post);
        return post;
    }


    private static final long serialVersionUID = 1L;
}