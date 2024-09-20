package com.cct.redmeatojbackend.question.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 题目表更新请求
* @author cct
*/
@ApiModel(description = "题目表更新请求")
@Data
public class UpdateQuestionRequest implements Serializable {


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

    private static final long serialVersionUID = 1L;
}