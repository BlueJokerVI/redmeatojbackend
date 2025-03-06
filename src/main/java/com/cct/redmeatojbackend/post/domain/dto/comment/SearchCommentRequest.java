package com.cct.redmeatojbackend.post.domain.dto.comment;
import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.post.domain.entity.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;

/**
* @description 评论表查询请求
* @author cct
*/
@ApiModel(description = "评论表查询请求")
@Data
public class SearchCommentRequest implements Serializable {

    @ApiModelProperty("评论id")
    @NotNull
    private Long id;


    public Comment toComment(){
        Comment comment = new Comment();
        BeanUtil.copyProperties(this,comment);
        return comment;
    }

    private static final long serialVersionUID = 1L;
}