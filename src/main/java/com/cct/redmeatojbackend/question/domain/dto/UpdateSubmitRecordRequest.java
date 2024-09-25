package com.cct.redmeatojbackend.question.domain.dto;

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


    private Long id;

    private Long questionId;

    private Long userId;

    private String submitContext;

    private Integer judgeResult;

    private Integer timeConsume;

    private Integer memoryConsume;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}