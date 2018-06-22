package com.rjs.miaosha.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description: 面向切面
 * @Author: Justyn
 * @Date: 2018/6/12 13:47
 */
@Aspect
@Order(1) // 控制多个Aspect的执行顺序，越小越先执行
@Component
@Slf4j
public class HttpAspect {

    // 切点
    @Pointcut("execution(public * com.rjs.miaosha.controller.*.*(..))")
    public void log() {}

    /**
     * 前置通知，方法调用前被调用
     * @param joinPoint
     */
    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint) {
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        String methodName = signature.getName();
        log.info("【发起请求】,method={},args={}", methodName, joinPoint.getArgs());
    }

    @AfterReturning(value = "log()", returning = "object")
    public void doAfterReturning(JoinPoint joinPoint, Object object) {
        // object为方法return的数据
        log.info("【返回数据】,response={}", object);
    }

}