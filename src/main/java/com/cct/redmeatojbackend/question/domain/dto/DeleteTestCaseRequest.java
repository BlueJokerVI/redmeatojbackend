package com.cct.redmeatojbackend.question.domain.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 删除测试用例请求
 */
@ApiModel(description = "删除测试用例请求")
@Data
public class DeleteTestCaseRequest  implements Serializable {

    @ApiModelProperty("题目id")
    @NotNull
    private Long questionId;

    @ApiModelProperty("测试用例id")
    @NotNull
    private Integer testCaseId;

    private static final long serialVersionUID = 1L;
}