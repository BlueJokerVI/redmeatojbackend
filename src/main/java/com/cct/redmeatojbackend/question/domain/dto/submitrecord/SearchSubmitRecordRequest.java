package com.cct.redmeatojbackend.question.domain.dto.submitrecord;
import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import io.swagger.annotations.*;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
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

    private static final long serialVersionUID = 1L;
}