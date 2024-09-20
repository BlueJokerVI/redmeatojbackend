package com.cct.redmeatojbackend.user.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cct.redmeatojbackend.user.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 管理员更新用户信息
 */
@ApiModel(description = "管理员更新用户信息")
@Data
public class UpdateUserInfoByAdminReq implements Serializable {


    private static final long serialVersionUID = 8686356347202350839L;

    @ApiModelProperty("用户id")
    @NotNull
    private Long id;


    @ApiModelProperty("用户密码")
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password非法")
    private String password;


    @ApiModelProperty("用户昵称")
    @Size(min = 1,max = 20,message = "长度请在要求范围内")
    private String name;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("用户头像")
    @Size(max = 1024,message = "头像地址请在要求范围内")
    private String userAvatar;

    @ApiModelProperty("用户简介")
    @Size(min = 1,max = 512,message = "长度请在要求范围内")
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


    public User getUser(User user){
        BeanUtil.copyProperties(this,user, CopyOptions.create().setIgnoreNullValue(true));
        return user;
    }

}
