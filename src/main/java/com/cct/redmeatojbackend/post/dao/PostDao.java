package com.cct.redmeatojbackend.post.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.post.dao.mapper.PostMapper;
import com.cct.redmeatojbackend.post.domain.dto.post.SearchPostRequest;
import com.cct.redmeatojbackend.post.domain.entity.Post;
import org.springframework.stereotype.Repository;

/**
* @description 帖子表数据库操作层
* @author cct
*/
@Repository
public class PostDao extends ServiceImpl<PostMapper, Post> {
}




