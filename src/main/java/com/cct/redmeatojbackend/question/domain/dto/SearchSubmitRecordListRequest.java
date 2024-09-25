package com.cct.redmeatojbackend.question.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 提交记录表分页查询请求
* @author cct
*/
@ApiModel(description = "提交记录表分页查询请求")
@Data
public class SearchSubmitRecordListRequest extends BasePageReq implements Serializable {

    
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

    @Override
    public Page<SubmitRecord> plusPage() {
        return new Page<>();
    }

    private static final long serialVersionUID = 1L;
}