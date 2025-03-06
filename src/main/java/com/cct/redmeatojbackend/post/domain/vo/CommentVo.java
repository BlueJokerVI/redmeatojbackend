package com.cct.redmeatojbackend.post.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.post.domain.entity.Comment;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 评论表视图
* @author cct
*/
@Data
public class CommentVo implements Serializable {

    
    private Long id;
    
    private Long userId;
    
    private Long postId;
    
    private String comment;
    
    private Integer thumbsUpNums;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer isDelete;

    public static CommentVo toVo(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtil.copyProperties(comment,commentVo);
        return commentVo;
    }


    private static final long serialVersionUID = 1L;
}