package com.cct.redmeatojbackend.question.domain.dto.submitrecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 提交记录表更新请求
* @author cct
*/
@ApiModel(description = "提交记录表更新请求")
@Data
public class UpdateSubmitRecordRequest implements Serializable {

    @NotNull
    @ApiModelProperty("提交记录id")
    private Long id;

    @ApiModelProperty("提交记录结果")
    @NotNull
    private Integer judgeResult;

    @ApiModelProperty("时间消耗")
    private Integer timeConsume;

    @ApiModelProperty("内存消耗")
    private Integer memoryConsume;

    private static final long serialVersionUID = 1L;
}