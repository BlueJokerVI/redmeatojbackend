package com.cct.redmeatojbackend.post.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName post
 */
@TableName(value ="post")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 发帖用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 帖子标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 帖子内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 点赞数
     */
    @TableField(value = "thumbs_up_nums")
    private Integer thumbsUpNums;

    /**
     * 观看数量
     */
    @TableField(value = "view_nums")
    private Integer viewNums;

    /**
     * 收藏数量
     */
    @TableField(value = "collect_nums")
    private Integer collectNums;

    /**
     * 热度分
     */
    @TableField(value = "hot_score")
    private Long hotScore;

    /**
     * 评论数
     */
    @TableField(value = "comment_nums")
    private Integer commentNums;

    /**
     * 发帖时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;
}