package com.cct.redmeatojbackend.common.constant;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 使用redis用到的常量
 * @Version: 1.0
 */
public interface RedisConstant {

    /**
     * 项目前缀
     */
    String PROJECT_PREFIX = "redmeatoj:";

    /**
     * 每天用户提交做题记录排名Key
     */
    String USER_SUBMIT_STATISTIC_KEY = PROJECT_PREFIX + "rank:USER_SUBMIT_STATISTIC_KEY";

    /**
     * 题目点赞key前缀
     */
    String THUMBS_UP_KEY_PREFIX = PROJECT_PREFIX + "thumbsUp:";

    /**
     * redisson限流器前缀
     */
    String LIMITER_PREFIX = PROJECT_PREFIX +"limit:";
}
