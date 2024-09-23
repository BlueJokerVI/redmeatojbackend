package com.cct.redmeatojbackend.question.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author cct
 * @description 题目表添加请求
 */
@Data
@ApiModel(description = "题目表添加请求")
public class AddQuestionRequest implements Serializable {

    @ApiModelProperty("题目名称")
    @NotBlank
    @Size(max = 64, message = "长度请在要求范围内")
    private String questionName;

    @ApiModelProperty("题目输入输出样例")
    @Size(min = 1,max = 3,message = "长度请在要求范围内")
    private List<TestCase> questionIoExample;


    /**
     * 记录该题目的测试用例有多少个，同时测试用例命名规则：
     * 输入文件名：x.in
     * 输出文件名：x.out
     * x取值：从1开始顺序增长的正整数
     */
    @ApiModelProperty("题目输入输出样例数量")
    @Min(value = 1, message = "样例数量不能小于1")
    private Integer questionIoTotal;


    @ApiModelProperty("题目描述")
    @NotBlank
    @Size(max = 8192, message = "长度请在要求范围内")
    private String questionDesc;

    @ApiModelProperty("题目标签")
    @Size(min = 1, message = "长度请在要求范围内")
    private List<String> questionTags;

    @ApiModelProperty("题目运行时内存限制，单位ms")
    @NotNull
    @Min(0)
    private Integer questionMemLimit;

    @ApiModelProperty("题目运行时时间限制，单位kb")
    @NotNull
    @Min(0)
    private Integer questionTimeLimit;


    public Question toQuestion() {
        Question question = new Question();
        BeanUtil.copyProperties(this, question);
        return question;
    }


    private static final long serialVersionUID = 1L;
}