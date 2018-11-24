package com.tianyi.datacenter.common.aop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 以controller为关注点做切面
 *
 * @author wenxinyan
 * @version 0.1
 */
@Aspect
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private Gson gson = new Gson();

    @Pointcut("execution(public * com.tianyi.datacenter.server.controller.*..*Controller.*(..))")
    public void controllerAspectFunction(){}

    @Before("controllerAspectFunction()")
    public void methodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        logger.info("==========请求内容==========");
        logger.info("请求地址:" + request.getRequestURL().toString());
        logger.info("请求方式:" + request.getMethod());
        logger.info("请求类方法:" + joinPoint.getSignature());
        logger.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        logger.info("==========请求内容==========");
    }

    @AfterReturning(returning = "o", pointcut = "controllerAspectFunction()")
    public void methodAfterReturning(Object o){
        logger.info("----------返回内容----------");
        logger.info("Response内容:" + gson.toJson(o));
        logger.info("----------返回内容----------");
    }

}