package com.cct.redmeatojbackend.post.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.post.domain.entity.Post;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 帖子表视图
* @author cct
*/
@Data
public class PostVo implements Serializable {

    
    private Long id;
    
    private Long userId;
    
    private String title;
    
    private String content;
    
    private Integer thumbsUpNums;
    
    private Integer viewNums;
    
    private Integer collectNums;
    
    private Long hotScore;
    
    private Integer commentNums;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer isDelete;

    public static PostVo toVo(Post post) {
        PostVo postVo = new PostVo();
        BeanUtil.copyProperties(post,postVo);
        return postVo;
    }


    private static final long serialVersionUID = 1L;
}