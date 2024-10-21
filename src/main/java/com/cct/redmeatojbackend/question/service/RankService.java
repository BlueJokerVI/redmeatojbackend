package com.cct.redmeatojbackend.question.service;

import com.cct.redmeatojbackend.question.domain.entity.UserRank;

import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 实时统计每天用户做题排名
 * @Version: 1.0
 */
public interface RankService {

    /**
     * 添加用户通过题目数
     * @param userId
     * @return
     */
    boolean addSubmitNum(Long userId);


    /**
     * 获取提交ac数前count位用户
     * @param count
     * @return
     */
    List<UserRank> getRank(int count);




}
