package com.cct.redmeatojbackend.question.domain.dto.question;
import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 题目表查询请求
* @author cct
*/
@ApiModel(description = "题目表查询请求")
@Data
public class SearchQuestionRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}