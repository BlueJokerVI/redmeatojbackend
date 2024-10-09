package com.cct.redmeatojbackend.question.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

/**
* @description 题目表视图
* @author cct
*/
@Data
@ApiModel("题目视图")
public class QuestionVo implements Serializable {

    @ApiModelProperty("题目id")
    private Long id;

    @ApiModelProperty("题目名称")
    private String questionName;

    @ApiModelProperty("题目测试用例")
    private List<TestCase> questionIoExample;

    @ApiModelProperty("题目描述")
    private String questionDesc;

    @ApiModelProperty("题目标签")
    private List<String> questionTags;

    @ApiModelProperty("题目内存限制")
    private Integer questionMemLimit;

    @ApiModelProperty("题目时间限制")
    private Integer questionTimeLimit;

    @ApiModelProperty("题目提交次数")
    private Integer questionSubmitNum;

    @ApiModelProperty("题目通过次数")
    private Integer questionAcNum;

    @ApiModelProperty("题目测试用例数")
    private Integer questionIoTotal;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public static QuestionVo toVo(Question question) {
        QuestionVo questionVo = new QuestionVo();
        BeanUtil.copyProperties(question,questionVo);
        return questionVo;
    }

    private static final long serialVersionUID = 1L;
}