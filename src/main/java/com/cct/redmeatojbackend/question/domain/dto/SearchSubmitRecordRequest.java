package com.cct.redmeatojbackend.question.domain.dto;
import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import io.swagger.annotations.ApiModel;
import java.lang.Integer;
import io.swagger.annotations.ApiModel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
/**
* @description 提交记录表查询请求
* @author cct
*/
@ApiModel(description = "提交记录表查询请求")
@Data
public class SearchSubmitRecordRequest implements Serializable {
    
    private Long id;
    
    private Long questionId;
    
    private Long userId;
    
    private String submitContext;
    
    private Integer judgeResult;
    
    private Integer timeConsume;
    
    private Integer memoryConsume;
    
    private Date createTime;

    public SubmitRecord toSubmitRecord(){
        SubmitRecord submitRecord = new SubmitRecord();
        BeanUtil.copyProperties(this,submitRecord);
        return submitRecord;
    }

    private static final long serialVersionUID = 1L;
}