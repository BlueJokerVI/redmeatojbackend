package com.cct.redmeatojbackend.user.domain.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.user.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 分页获取用户
 */
@ApiModel(description = "分页获取用户")
@Data
public class GetUserListRequest extends BasePageReq implements Serializable {

    private static final long serialVersionUID = 8686356347202350839L;

    @ApiModelProperty("用户昵称")
    @Size(min = 1, max = 20, message = "长度请在要求范围内")
    private String name;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户账户")
    private String account;

    @ApiModelProperty("用户昵称")
    private Integer gender;

    @ApiModelProperty("用户角色：普通用户：0、管理员：1、黑名单用户：2")
    private Integer userRole;

    @ApiModelProperty("微信开放平台id")
    private String unionId;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @Override
    public Page<User> plusPage() {
        return new Page<>(getCurrent(),getPageSize());
    }
}
