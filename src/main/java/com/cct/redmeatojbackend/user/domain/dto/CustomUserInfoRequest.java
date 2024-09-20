package com.cct.redmeatojbackend.user.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 用户自定义信息请求
 */
@ApiModel(description = "用户自定义信息请求")
@Data
public class CustomUserInfoRequest implements Serializable {

    private static final long serialVersionUID = 8686356347202350839L;

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
}
