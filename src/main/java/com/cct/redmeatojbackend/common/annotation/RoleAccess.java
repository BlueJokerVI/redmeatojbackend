package com.cct.redmeatojbackend.common.annotation;


import com.cct.redmeatojbackend.common.domain.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于用户权限拦用户访问某些接口
 *
 * @author cct
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAccess {

    /**
     * 获取用户权限
     * @return
     */
    UserRoleEnum role() default UserRoleEnum.COMMON_USER;
}
