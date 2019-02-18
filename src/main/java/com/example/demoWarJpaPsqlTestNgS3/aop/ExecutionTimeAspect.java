package com.example.demoWarJpaPsqlTestNgS3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demoWarJpaPsqlTestNgS3.ExpectedReturnDto;

/**
 * @author jaubert
 *
 */
@Aspect
@Component
public class ExecutionTimeAspect {

    private static final Logger log = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    /**
     * 
     */
    private static final long serialVersionUID = -993143400853913415L;

    /**
     * Around invoke.
     *
     * @param ic
     *            the ic
     * @return the object
     * @throws Exception
     *             the exception
     */
    @Around("execution (com.example.demoWarJpaPsqlTestNgS3.ExpectedReturnDto com.example.demoWarJpaPsqlTestNgS3.*ServiceImpl.*(..))")
    // @Around("@annotation(ExecutionTime)")
    public Object aroundInvoke(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.nanoTime();
        Object obj = joinPoint.proceed();
        if (obj instanceof ExpectedReturnDto) {
            long elapsedTime = System.nanoTime() - startTime;
            double ms = elapsedTime / 1000000.0;
            ((ExpectedReturnDto<?>) obj).setExecutionTime(ms);
        }
        return obj;
    }
}
