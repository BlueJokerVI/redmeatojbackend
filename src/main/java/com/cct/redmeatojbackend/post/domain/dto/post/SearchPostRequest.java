package com.cct.redmeatojbackend.post.domain.dto.post;
import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.post.domain.entity.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;

/**
* @description 帖子表查询请求
* @author cct
*/
@ApiModel(description = "帖子表查询请求")
@Data
public class SearchPostRequest implements Serializable {

    @ApiModelProperty("帖子id")
    private Long id;


    public Post toPost(){
        Post post = new Post();
        BeanUtil.copyProperties(this,post);
        return post;
    }

    private static final long serialVersionUID = 1L;
}