package com.cct.redmeatojbackend.question.domain.dto;
import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 题目表查询请求
* @author cct
*/
@ApiModel(description = "题目表查询请求")
@Data
public class SearchQuestionRequest implements Serializable {
    
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

    public Question toQuestion(){
        Question question = new Question();
        BeanUtil.copyProperties(this,question);
        return question;
    }

    private static final long serialVersionUID = 1L;
}