package com.cct.redmeatojbackend.question.domain.dto.submitrecord;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author cct
 * @description 提交记录表添加请求
 */
@Data
@ApiModel(description = "提交记录表添加请求")
public class AddSubmitRecordRequest implements Serializable {

    @ApiModelProperty("题目id")
    @NotNull
    private Long questionId;


    @ApiModelProperty("语言")
    @NotBlank
    @Size(min = 1, max = 8)
    private String language;

    @ApiModelProperty("提交代码内容")
    @NotBlank
    @Size(min = 1, max = 8012)
    private String submitContext;


    public SubmitRecord toSubmitRecord() {
        SubmitRecord submitRecord = new SubmitRecord();
        BeanUtil.copyProperties(this, submitRecord);
        return submitRecord;
    }


    private static final long serialVersionUID = 1L;
}