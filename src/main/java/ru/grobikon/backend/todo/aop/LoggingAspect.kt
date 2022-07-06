package ru.grobikon.backend.todo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //аспект будет выполняться для всех методов из пакета контроллеров
    @Around("execution(* ru.grobikon.backend.todo.controller..*(..)))")
    public Object profileControllerMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // получаем информацию о том, какой класс и метод выполняется
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("-------- Executing: {}. {} --------",className, methodName);

        StopWatch countdown = new StopWatch();

        // засекаем время
        countdown.start();
        Object result = proceedingJoinPoint.proceed();
        countdown.stop();

        log.info("-------- Executing time of {}. {} :: {} ms --------",className, methodName, countdown.getTotalTimeMillis());

        return result;
    }
}
