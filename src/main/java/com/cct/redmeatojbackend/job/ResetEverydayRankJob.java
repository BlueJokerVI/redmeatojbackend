package com.cct.redmeatojbackend.job;

import com.cct.redmeatojbackend.common.utils.RedisUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.cct.redmeatojbackend.common.constant.RedisConstant.USER_SUBMIT_STATISTIC_KEY;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 每天晚上12点重置用户排行榜
 */

@Component
public class ResetEverydayRankJob {

    @Scheduled(cron = "0 0 0 * * ?")
    public void restUserRank(){
        RedisUtils.del(USER_SUBMIT_STATISTIC_KEY);
    }

}
