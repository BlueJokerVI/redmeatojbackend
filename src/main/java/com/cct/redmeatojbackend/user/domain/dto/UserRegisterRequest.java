package com.cct.redmeatojbackend.user.domain.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.*;
import java.io.Serializable;


/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 用户注册请求
 */
@ApiModel(description = "用户注册请求")
@Data
public class UserRegisterRequest implements Serializable {
    /**
     * 用户账户
     */
    @ApiModelProperty(value = "用户账号")
    @NotBlank
    @Size(min = 5,max = 20,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "account非法")
    private String account;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    @NotNull
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password非法")
    private String password;

    /**
     * 第二次输入密码
     */
    @ApiModelProperty(value = "重复密码")
    @NotNull
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password")
    private String repeatPassword;


    private static final long serialVersionUID = 3980665266154091736L;
}
