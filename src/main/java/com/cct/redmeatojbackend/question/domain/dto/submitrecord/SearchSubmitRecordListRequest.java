package com.cct.redmeatojbackend.question.domain.dto.submitrecord;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 提交记录表分页查询请求
* @author cct
*/
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "提交记录表分页查询请求")
@Data
public class SearchSubmitRecordListRequest extends BasePageReq implements Serializable {

    @ApiModelProperty("提交记录id")
    private Long id;

    @ApiModelProperty("题目id")
    private Long questionId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("提交结果")
    private Integer judgeResult;

    @ApiModelProperty("提交时间")
    private Date createTime;


    public SubmitRecord toSubmitRecord(){
        SubmitRecord submitRecord = new SubmitRecord();
        BeanUtil.copyProperties(this,submitRecord);
        return submitRecord;
    }

    @Override
    public Page<SubmitRecord> plusPage() {
        return new Page<>(getCurrent(),getPageSize());
    }

    private static final long serialVersionUID = 1L;
}