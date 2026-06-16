package com.example.hcmk24cntt1_phamcongthanh_002.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.hcmk24cntt1_phamcongthanh_002.controller.*.*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.example.hcmk24cntt1_phamcongthanh_002.service.*.*(..))", returning = "result")
    public void logAfterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        if (result != null) {
            log.info("Method {} returned: {}", joinPoint.getSignature().getName(), result.toString());
        }
    }

    @Around("execution(* com.example.hcmk24cntt1_phamcongthanh_002.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        log.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }
}