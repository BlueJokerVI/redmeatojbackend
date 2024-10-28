package com.cct.redmeatojbackend.question.service.impl;

import com.cct.redmeatojbackend.common.utils.RedisUtils;
import com.cct.redmeatojbackend.question.domain.entity.UserRank;
import com.cct.redmeatojbackend.question.service.RankService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.cct.redmeatojbackend.common.constant.RedisConstant.USER_SUBMIT_STATISTIC_KEY;

@SpringBootTest
class RankServiceImplTest {


    @Resource
    private RankService rankService;

    @Resource
    private StringRedisTemplate  stringRedisTemplate;

    @Test
    void test() {
        List<Object> results = stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // 1、通过connection打开pipeline
                connection.openPipeline();
                // 2、给本次pipeline添加一次性要执行的多条命令

                // 2.1、一个 set key value 的操作
                byte[] key = "name".getBytes();
                byte[] value = "qinyi".getBytes();
                connection.set(key, value);

                // 2.2、执行一个错误的命令
                connection.lPop("xx".getBytes());

                // 2.3、mset 操作
                Map<byte[], byte[]> tuple = new HashMap<>();
                tuple.put("id".getBytes(), "1".getBytes());
                tuple.put("age".getBytes(), "19".getBytes());
                connection.mSet(tuple);

                /**
                 * 1、不能关闭pipeline
                 * 2、返回值为null
                 */
                // 3. 关闭 pipeline
                connection.closePipeline();
                return null;
            }
        });

        // 处理结果
        for (Object result : results) {
            System.out.println(result);
        }

    }
}