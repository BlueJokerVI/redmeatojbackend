package com.cct.redmeatojbackend.question.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: redis储存用户排名信息视图
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRank {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户账户
     */
    @TableField(value = "account")
    private String account;



    /**
     * 用户头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;


    /**
     * 提交通过数
     */
    @TableField(exist = false)
    Long submittedNumb;
}
