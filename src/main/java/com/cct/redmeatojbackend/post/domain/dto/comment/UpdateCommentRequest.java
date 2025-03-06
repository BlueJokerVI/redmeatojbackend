package com.cct.redmeatojbackend.post.domain.dto.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cct
 * @description 评论表更新请求
 */
@ApiModel(description = "评论表更新请求")
@Data
public class UpdateCommentRequest implements Serializable {


    @ApiModelProperty("评论id")
    @NotNull
    private Long id;

    @ApiModelProperty("评论内容")
    @NotBlank
    @Max(2048)
    private String comment;

    private static final long serialVersionUID = 1L;
}