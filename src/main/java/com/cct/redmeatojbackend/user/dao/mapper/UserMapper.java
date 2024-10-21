package com.cct.redmeatojbackend.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.question.domain.entity.UserRank;
import com.cct.redmeatojbackend.user.domain.dto.GetUserListRequest;
import com.cct.redmeatojbackend.user.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cct
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2024-08-13 19:14:13
 * @Entity generator.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页获取用户
     *
     * @param plusPage
     * @param getUserListRequest
     * @return
     */
    Page<User> getUserList(Page<User> plusPage, @Param("getUserListRequest") GetUserListRequest getUserListRequest);

    /**
     * 更具ids获取用户排名列表
     * @param userIds
     * @return
     */
    List<UserRank> getUserRank(@Param("userIds") List<Long> userIds);
}




