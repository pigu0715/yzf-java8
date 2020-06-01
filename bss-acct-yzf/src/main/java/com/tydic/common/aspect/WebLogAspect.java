package com.tydic.common.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebLogAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("(execution(public * com.tydic.*.controller..*.*(..))) || (execution(public * com.tydic.*.api..*.*(..)))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    	startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.debug("请求URL : " + request.getRequestURL().toString());
        logger.debug("请求方式 : " + request.getMethod());
        logger.debug("请求IP : " + request.getRemoteAddr());
        logger.debug("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.debug("请求报文 : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.debug("返回报文 : " + ret);
        logger.debug("响应时间 : " + (System.currentTimeMillis() - startTime.get()));
    }

}
