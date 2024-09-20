package com.cct.redmeatojbackend.common.aop;

import com.cct.redmeatojbackend.common.annotation.AllowAccessWithNotLogin;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.user.domain.vo.UserVo;
import com.cct.redmeatojbackend.user.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 根据登入状态，配合注解实现接口的访问拦截
 */
@Aspect
@Component
public class AccessWithNotLoginAspect {

    @Resource
    private UserService userService;


    @Around("@annotation(allowAccessWithNotLogin)")
    public Object accessWithNotLogin(ProceedingJoinPoint joinPoint, AllowAccessWithNotLogin allowAccessWithNotLogin) throws Throwable {
        boolean allow = allowAccessWithNotLogin.allow();
        if (!allow) {
            UserVo currentUser = userService.getCurrentUser();
            ThrowUtils.throwIf(currentUser == null, RespCodeEnum.NOT_LOGIN_ERROR);
        }
        return joinPoint.proceed();
    }

}
