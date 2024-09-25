package com.cct.redmeatojbackend.coderunbox.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 用于在代码运行后，删除tmp文件
 * @author cct
 */
@Aspect
@Component
@Slf4j
public class RunCodeAspect {

    @AfterReturning(pointcut = "execution(* com.cct.redmeatojbackend.coderunbox.service.impl.JavaRunCodeServiceImpl.run(..))", returning = "result")
    public void afterRun(JoinPoint joinPoint, Object result) {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 处理正常返回
        System.out.println("After run: " + Arrays.toString(args) + ", Result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.cct.redmeatojbackend.coderunbox.service.impl.JavaRunCodeServiceImpl.run(..))", throwing = "exception")
    public void afterRunException(JoinPoint joinPoint, Throwable exception) {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 处理异常情况
        System.out.println("After run with exception: " + Arrays.toString(args) + ", Exception: " + exception.getMessage());
    }
}
