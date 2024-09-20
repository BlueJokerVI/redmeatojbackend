package com.cct.redmeatojbackend.user.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 用户登入请求
 */

@ApiModel(description = "用户登入请求")
@Data
public class UserLoginRequest {

    @ApiModelProperty("账号")
    @NotNull
    @Size(min = 5,max = 20,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "account非法")
    private String account;

    @ApiModelProperty("密码")
    @NotNull
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password非法")
    private String password;
}
