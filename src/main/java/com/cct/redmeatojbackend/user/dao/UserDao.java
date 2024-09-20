package com.cct.redmeatojbackend.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.common.domain.enums.UserRoleEnum;
import com.cct.redmeatojbackend.user.dao.mapper.UserMapper;
import com.cct.redmeatojbackend.user.domain.dto.GetUserListRequest;
import com.cct.redmeatojbackend.user.domain.entity.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


/**
 * @author cct
 * @description 针对表【user(用户表)】的数据库操作Dao实现
 * @createDate 2024-08-13 19:14:13
 */
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {


    @Resource
    private UserMapper userMapper;

    /**
     * 更具账号查找用户
     *
     * @param account
     * @return
     */
    public User getByAccount(String account) {
        return lambdaQuery().eq(User::getAccount, account).one();
    }

    /**
     * 拉黑用户
     *
     * @param userId
     * @return
     */
    public boolean banUser(Long userId) {
        return lambdaUpdate().eq(User::getId, userId)
                .set(User::getUserRole, UserRoleEnum.BLACK_USER.getCode())
                .update();
    }

    /**
     * 分页获取用户
     *
     * @param getUserListRequest
     * @return
     */
    public Page<User> getUserList(GetUserListRequest getUserListRequest) {
        return userMapper.getUserList(getUserListRequest.plusPage(), getUserListRequest);
    }
}




