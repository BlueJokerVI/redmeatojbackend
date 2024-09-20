package com.cct.redmeatojbackend.user.domain.dto;

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
 * @Description: 用户更新密码
 */
@ApiModel(description = "用户更新密码")
@Data
public class UpdatePasswordReq implements Serializable {

    private static final long serialVersionUID = 8686356347202350839L;

    @ApiModelProperty("旧密码")
    @NotNull
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password非法")
    private String oldPassword;

    @ApiModelProperty("新密码")
    @NotNull
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password非法")
    private String newPassword;

    @ApiModelProperty("新密码重复")
    @NotNull
    @Size(min = 6,message = "长度请在要求范围内")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9,.!?@#$%^&*()_+\\-=]+$",message = "password非法")
    private String newRepeatedPassword;

}
