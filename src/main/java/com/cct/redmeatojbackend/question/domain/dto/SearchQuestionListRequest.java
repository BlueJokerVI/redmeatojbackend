package com.cct.redmeatojbackend.question.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.domain.dto.BasePageReq;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

/**
* @description 题目表分页查询请求
* @author cct
*/
@ApiModel(description = "题目表分页查询请求")
@Data
public class SearchQuestionListRequest extends BasePageReq implements Serializable {


    @ApiModelProperty(value = "题目id")
    private Long id;

    @ApiModelProperty(value = "题目名称")
    private String questionName;

    @ApiModelProperty(value = "题目描述")
    private String questionTags;

    public Question toQuestion(){
        Question question = new Question();
        BeanUtil.copyProperties(this,question);
        return question;
    }

    @Override
    public Page<Question> plusPage() {
        return new Page<>();
    }

    private static final long serialVersionUID = 1L;
}