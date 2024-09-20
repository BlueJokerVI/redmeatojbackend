package com.cct.redmeatojbackend.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户账户
     */
    @TableField(value = "account")
    private String account;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户昵称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 用户头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 用户简介
     */
    @TableField(value = "user_profile")
    private String userProfile;

    /**
     * 普通用户：0、管理员：1、黑名单用户：2
     */
    @TableField(value = "user_role")
    private Integer userRole;

    /**
     * 微信开放平台id
     */
    @TableField(value = "union_id")
    private String unionId;

    /**
     * 微信公众号id
     */
    @TableField(value = "mp_open_id")
    private String mpOpenId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除（0：没删除、1：删除）
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}