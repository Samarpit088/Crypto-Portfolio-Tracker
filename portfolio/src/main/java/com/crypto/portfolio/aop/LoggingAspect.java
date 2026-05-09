package com.crypto.portfolio.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.crypto.portfolio.service.impl.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint){
        log.debug("Entering method: {}",joinPoint.getSignature().toShortString());
    }

    @AfterReturning(
            pointcut = "execution(* com.crypto.portfolio.service.impl.*.*(..))",
            returning = "result"
    )
    public void logAfterMethod(JoinPoint joinPoint,Object result){
        log.debug("Method completed: {}",joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(
            pointcut = "execution(* com.crypto.portfolio.service.impl.*.*(..))",
            throwing = "ex"
    )
    public void logException(JoinPoint joinPoint,Throwable ex){
        log.error("Exception in method: {} | Message: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage()
        );
    }
}
