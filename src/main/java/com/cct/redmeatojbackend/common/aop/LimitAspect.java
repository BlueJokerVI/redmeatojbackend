package com.cct.redmeatojbackend.common.aop;

import com.cct.redmeatojbackend.common.annotation.Limit;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import com.cct.redmeatojbackend.user.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 限流拦截切面
 */
@Aspect
@Component
public class LimitAspect {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private UserService userService;

    @Around("@annotation(limit)")
    public Object login(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {
        //1.构造限流器key
        String limitKey = limit.keyPrefix();
        switch (limit.target()) {
            case UID:
                limitKey += userService.getCurrentUser().getId();
                break;
            case IP:
                //todo 待时现依据ip限流
                break;
            default:
        }
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(limitKey);
        //2.设置限流器参数
        rateLimiter.trySetRate(RateType.OVERALL, limit.count(), limit.interval(), limit.intervalUnit());
        //3.设置限流器过期时间
        if (!rateLimiter.expire(Duration.ofDays(limit.expireTime()))) {
            throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "限流器过期时间设置失败");
        }
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        }
        throw new BusinessException(RespCodeEnum.ACCESS_TOO_FAST_ERROR);
    }

}
