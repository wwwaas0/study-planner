package com.example.studyPlanner.aop;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerAspect {
    @Pointcut("execution(* com.example.studyPlanner.*.service.*.*(..))")
    public void allServiceMethods() {
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void handleNumberFormatException(EntityNotFoundException ex) {
        log.warn("Wrong EntityNotFoundException Occurred!");
        log.warn(ex.getMessage());
    }

    //TODO: 다른 exception 사용하면 추가하기
}
