package com.cct.redmeatojbackend.post.domain.dto.post;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.post.domain.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 帖子表分页查询请求
* @author cct
*/
@ApiModel(description = "帖子表分页查询请求")
@Data
public class SearchPostListRequest extends BasePageReq implements Serializable {

    @ApiModelProperty("帖子id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty("帖子内容")
    /**
     * TODO 考虑使用ElasticSearch 优化全文检索
     */
    private String content;

    @ApiModelProperty("热力值")
    private Long hotScore;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public Post toPost(){
        Post post = new Post();
        BeanUtil.copyProperties(this,post);
        return post;
    }

    @Override
    public Page<Post> plusPage() {
        return new Page<>();
    }

    private static final long serialVersionUID = 1L;
}