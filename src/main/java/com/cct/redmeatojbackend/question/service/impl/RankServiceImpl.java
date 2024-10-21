package com.cct.redmeatojbackend.question.service.impl;

import com.cct.redmeatojbackend.common.utils.JsonUtils;
import com.cct.redmeatojbackend.common.utils.RedisUtils;
import com.cct.redmeatojbackend.question.domain.entity.UserRank;
import com.cct.redmeatojbackend.question.service.RankService;
import com.cct.redmeatojbackend.user.dao.UserDao;
import com.cct.redmeatojbackend.user.dao.mapper.UserMapper;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cct.redmeatojbackend.common.constant.RedisConstant.USER_SUBMIT_STATISTIC_KEY;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: TODO
 */

@Service
public class RankServiceImpl implements RankService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean addSubmitNum(Long userId) {
        String userRankJson = JsonUtils.toStr(userId);
        Double score = RedisUtils.zScore(USER_SUBMIT_STATISTIC_KEY, userRankJson);
        if (score == null) {
            return RedisUtils.zAdd(USER_SUBMIT_STATISTIC_KEY, userRankJson, 1);
        }
        return RedisUtils.zAdd(USER_SUBMIT_STATISTIC_KEY, userRankJson, score + 1);
    }

    @Override
    public List<UserRank> getRank(int count) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = RedisUtils.zReverseRangeWithScores(USER_SUBMIT_STATISTIC_KEY, count);

        List<Long> userIds = typedTuples.stream().map((v) -> Long.parseLong(Objects.requireNonNull(v.getValue()))).collect(Collectors.toList());
        List<UserRank> ranks = userMapper.getUserRank(userIds);
        Map<Long, Long> idToSubmittedNum = typedTuples.stream().
                map((v) -> UserRank.builder().id(Long.parseLong(Objects.requireNonNull(v.getValue()))).submittedNumb(Objects.requireNonNull(v.getScore()).longValue()).build())
                .collect(Collectors.toMap(UserRank::getId, UserRank::getSubmittedNumb));

        for (UserRank rank : ranks) {
            rank.setSubmittedNumb(idToSubmittedNum.get(rank.getId()));
        }
        return ranks;
    }
}

