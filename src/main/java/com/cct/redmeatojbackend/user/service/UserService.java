package com.cct.redmeatojbackend.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.utils.HttpUtils;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.user.domain.dto.CustomUserInfoRequest;
import com.cct.redmeatojbackend.user.domain.dto.GetUserListRequest;
import com.cct.redmeatojbackend.user.domain.dto.UpdatePasswordReq;
import com.cct.redmeatojbackend.user.domain.dto.UpdateUserInfoByAdminReq;
import com.cct.redmeatojbackend.user.domain.entity.User;
import com.cct.redmeatojbackend.user.domain.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.cct.redmeatojbackend.common.constant.CommonConstant.LOGIN_USER_INFO_SESSION_KEY;

/**
* @author cct
* @description
* @createDate 2024-08-13 19:14:13
*/

public interface UserService{
    /**
     * 用户注册
     * @param account
     * @param password
     * @param repeatPassword
     * @return
     */
    Boolean register(String account,String password,String repeatPassword);

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    User login(String account, String password);

    /**
     * 用户修改自身信息
     * @param customUserInfoRequest
     * @return
     */
    User customUserInfo(CustomUserInfoRequest customUserInfoRequest);


    /**
     * 获取当前登入用户信息
     * @return
     */
    default UserVo getCurrentUser(){
        //1.RequestContextHolder中获取与当前线程绑定的request
        HttpServletRequest request = HttpUtils.getCurrentRequest();
        //2.从request中获取session，并判断session中是否存在user信息，来判断user是否登入
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute(LOGIN_USER_INFO_SESSION_KEY);
        ThrowUtils.throwIf(userVo == null, RespCodeEnum.NOT_LOGIN_ERROR);
        return userVo;
    }

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    Long banUser(Long userId);

    /**
     * 用户更新密码
     * @param updatePasswordReq
     * @return
     */
    void updatePassword(UpdatePasswordReq updatePasswordReq);

    /**
     * 管理员更新用户信息
     * @param updateUserInfoByAdminReq
     */
    void updateUserInfoByAdmin(UpdateUserInfoByAdminReq updateUserInfoByAdminReq);

    /**
     * 获取用户列表
     * @param getUserListRequest
     * @return
     */
    Page<User> getUserList(GetUserListRequest getUserListRequest);
}
