package com.cct.redmeatojbackend.question.domain.dto.testcase;

import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 添加题目测试用例请求
 */
@ApiModel(description = "添加题目测试用例")
@Data
public class AddTestCaseRequest {

    @ApiModelProperty("题目id")
    @NotNull
    private Long questionId;

    @ApiModelProperty("更新题目用例集合")
    @Size(min = 1)
    List<TestCase> testCases;
}
