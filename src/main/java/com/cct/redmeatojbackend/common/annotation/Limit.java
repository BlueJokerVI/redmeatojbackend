package com.cct.redmeatojbackend.common.annotation;

import org.redisson.api.RateIntervalUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合Redisson实现限流注解
 * @author cct
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * 限流器key前缀
     * @return
     */
    String keyPrefix() default "";

    /**
     * 限流器构造依据key后缀
     */
    Target target() default Target.UID;


    /**
     * 限流次数
     * @return
     */
     long count();

    /**
     * 限流时间间隔
     * @return
     */
    long interval();

    /**
     * 限流时间间隔单位
     * @return
     */
    RateIntervalUnit intervalUnit() default RateIntervalUnit.SECONDS;

    /**
     * 限流器过期时间，单位天
     * @return
     */
    long expireTime() default 3;

    enum Target {
        UID, IP
    }

}
