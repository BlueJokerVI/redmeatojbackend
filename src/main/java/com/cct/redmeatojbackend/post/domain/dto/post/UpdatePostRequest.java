package com.cct.redmeatojbackend.post.domain.dto.post;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 帖子表更新请求
* @author cct
*/
@ApiModel(description = "帖子表更新请求")
@Data
public class UpdatePostRequest implements Serializable {


    @ApiModelProperty("帖子ID")
    @NotNull
    private Long id;

    @ApiModelProperty("帖子标题")
    @Max(32)
    private String title;

    @ApiModelProperty("帖子内容")
    @Max(8192)
    private String content;

    @ApiModelProperty("点赞数")
    @Max(Integer.MAX_VALUE)
    @Min(0)
    private Integer thumbsUpNums;

    @ApiModelProperty("浏览量")
    @Max(Integer.MAX_VALUE)
    @Min(0)
    private Integer viewNums;

    @ApiModelProperty("收藏量")
    @Max(Integer.MAX_VALUE)
    @Min(0)
    private Integer collectNums;

    @ApiModelProperty("热力值，描述该贴的火热程度")
    @Max(Long.MAX_VALUE)
    @Min(0)
    private Long hotScore;

    @ApiModelProperty("评论量")
    @Max(Integer.MAX_VALUE)
    @Min(0)
    private Integer commentNums;

    private static final long serialVersionUID = 1L;
}