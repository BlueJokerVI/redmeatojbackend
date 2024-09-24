package com.cct.redmeatojbackend.question.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目提交记录表
 * @TableName submit_record
 */
@TableName(value ="submit_record")
@Data
public class SubmitRecord implements Serializable {
    /**
     * 提交记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目id
     */
    @TableField(value = "question_id")
    private Long questionId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 提交内容
     */
    @TableField(value = "submit_context")
    private String submitContext;

    /**
     * 判题结果，0 判题中，1 通过 ，2 失败 ，3 编译失败，4格式错误，5内存超限，6时间超限
     */
    @TableField(value = "judge_result")
    private Integer judgeResult;

    /**
     * 代码运行时间，单位毫秒
     */
    @TableField(value = "time_consume")
    private Integer timeConsume;

    /**
     * 代码运行时内存消耗
     */
    @TableField(value = "memory_consume")
    private Integer memoryConsume;

    /**
     * 提交时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}