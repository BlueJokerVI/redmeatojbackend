package com.cct.redmeatojbackend.question.domain.dto.question;

import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

/**
* @description 题目表更新请求
* @author cct
*/
@ApiModel(description = "题目表更新请求")
@Data
public class UpdateQuestionRequest implements Serializable {


    @ApiModelProperty("题目id")
    @NotNull
    private Long id;

    @ApiModelProperty("题目名称")
    @Size(min = 1, max = 64,message = "题目名称长度在1到64之间")
    private String questionName;

    @ApiModelProperty("题目输入输出样例")
    private List<TestCase> questionIoExample;

    @ApiModelProperty("题目描述")
    @Size(min = 1, max = 8192,message = "题目描述长度在1到8192之间")
    private String questionDesc;

    @ApiModelProperty("题目标签")
    @Size(min = 1, max = 1024,message = "题目标签长度在1到1024之间")
    private List<String> questionTags;

    @ApiModelProperty("题目内存限制")
    @Min(0)
    private Integer questionMemLimit;

    @ApiModelProperty("题目时间限制")
    @Min(0)
    private Integer questionTimeLimit;

    @ApiModelProperty("题目提交次数")
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer questionSubmitNum;

    @ApiModelProperty("题目通过次数")
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer questionAcNum;

    @ApiModelProperty("更新题目用例集合")
    List<TestCase> testCases;

    private static final long serialVersionUID = 1L;
}