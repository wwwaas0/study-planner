package com.example.studyPlanner.aop;

import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlerAspect {
    @Pointcut("execution(* com.example.studyPlanner.*.service.*.*(..))")
    public void allServiceMethods() {
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void handleNumberFormatException(EntityNotFoundException ex) {
        System.out.println("Wrong EntityNotFoundException Occurred!");
        System.out.println(ex.getMessage());
    }

    //TODO: 다른 exception 사용하면 추가하기
}
