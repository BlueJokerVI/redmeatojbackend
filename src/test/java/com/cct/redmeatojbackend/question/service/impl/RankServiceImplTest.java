package com.cct.redmeatojbackend.question.service.impl;

import com.cct.redmeatojbackend.common.utils.RedisUtils;
import com.cct.redmeatojbackend.question.domain.entity.UserRank;
import com.cct.redmeatojbackend.question.service.RankService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cct.redmeatojbackend.common.constant.RedisConstant.USER_SUBMIT_STATISTIC_KEY;

@SpringBootTest
class RankServiceImplTest {


    @Resource
    private RankService rankService;

    @Test
    void test() {
        Long k4 = RedisUtils.inc("k4", 100L);
        System.out.println(k4);
    }
}