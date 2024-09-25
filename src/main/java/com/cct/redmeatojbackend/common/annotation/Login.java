package com.cct.redmeatojbackend.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于拦截某些接口是否允许未登入访问
 * @author cct
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
    /**
     * 标记未登入是否允许请求接口
     * @return
     */
    boolean allow() default true;
}
