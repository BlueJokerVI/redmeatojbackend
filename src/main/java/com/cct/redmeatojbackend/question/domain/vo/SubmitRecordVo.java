package com.cct.redmeatojbackend.question.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.cct.redmeatojbackend.question.domain.entity.SubmitRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Date;
/**
* @description 提交记录表视图
* @author cct
*/
@Data
public class SubmitRecordVo implements Serializable {

    @ApiModelProperty("提交记录id")
    private Long id;

    @ApiModelProperty("题目id")
    private Long questionId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("提交语言")
    private String language;

    @ApiModelProperty("提交内容")
    private String submitContext;

    @ApiModelProperty("判题结果")
    private Integer judgeResult;

    /**
     * 正常运行结束,该值才有意义
     */
    @ApiModelProperty("耗时")
    private Integer timeConsume;
    /**
     * 正常运行结束,该值才有意义
     */
    @ApiModelProperty("内存消耗")
    private Integer memoryConsume;

    @ApiModelProperty("提交时间")
    private Date createTime;

    public static SubmitRecordVo toVo(SubmitRecord submitRecord) {
        SubmitRecordVo submitRecordVo = new SubmitRecordVo();
        BeanUtil.copyProperties(submitRecord,submitRecordVo);
        return submitRecordVo;
    }

    private static final long serialVersionUID = 1L;
}