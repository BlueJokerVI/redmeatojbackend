package com.cct.redmeatojbackend.question.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 题目表视图
* @author cct
*/
@Data
public class QuestionVo implements Serializable {

    
    private Long id;
    
    private String questionName;
    
    private String questionIoExample;
    
    private String questionDesc;
    
    private String questionTags;
    
    private Integer questionMemLimit;
    
    private Integer questionTimeLimit;
    
    private Integer questionSubmitNum;
    
    private Integer questionAcNum;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer isDelete;

    public static QuestionVo toVo(Question question) {
        QuestionVo questionVo = new QuestionVo();
        BeanUtil.copyProperties(question,questionVo);
        return questionVo;
    }


    private static final long serialVersionUID = 1L;
}