package com.cct.redmeatojbackend.post.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cct.redmeatojbackend.post.dao.mapper.CommentMapper;
import com.cct.redmeatojbackend.post.domain.dto.comment.SearchCommentRequest;
import com.cct.redmeatojbackend.post.domain.entity.Comment;
import org.springframework.stereotype.Repository;

/**
* @description 评论表数据库操作层
* @author cct
*/
@Repository
public class CommentDao extends ServiceImpl<CommentMapper, Comment> {

}




