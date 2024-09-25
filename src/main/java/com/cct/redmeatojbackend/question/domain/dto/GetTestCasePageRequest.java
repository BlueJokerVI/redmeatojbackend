package com.cct.redmeatojbackend.question.domain.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @description 分页获取题目测试用例请求
* @author cct
*/
@ApiModel(description = "题目测试用例分页查询请求")
@Data
public class GetTestCasePageRequest extends BasePageReq implements Serializable {

    @ApiModelProperty("题目id")
    @NotNull
    private Long questionId;
    @Override
    public Page<TestCase> plusPage() {
        return new Page<>(getCurrent(),getPageSize());
    }

    private static final long serialVersionUID = 1L;
}