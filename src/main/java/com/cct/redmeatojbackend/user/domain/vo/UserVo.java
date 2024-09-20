package com.cct.redmeatojbackend.user.domain.vo;

import com.cct.redmeatojbackend.user.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 用户登入后返回用户Vo
 */
@Data
@ApiModel("用户视图")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1369922972471506342L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户账户")
    private String account;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("用户简介")
    private String userProfile;

    @ApiModelProperty("用户类别普通用户：0、管理员：1、黑名单用户：2")
    private Integer userRole;

    public static UserVo getVo(User user){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    public static User getUser(UserVo userVo){
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        return user;
    }

}
