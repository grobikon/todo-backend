package ru.grobikon.backend.todo.aop

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class LoggingAspect {
    companion object{
        val log: Logger = LogManager.getLogger(LoggingAspect::class.java.name)
    }

    //аспект будет выполняться для всех методов из пакета контроллеров
    @Around("execution(* ru.grobikon.backend.todo.controller..*(..)))")
    fun profileControllerMethods(proceedingJoinPoint: ProceedingJoinPoint): Any {
        val methodSignature = proceedingJoinPoint.signature as MethodSignature

        // получаем информацию о том, какой класс и метод выполняется
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name
        log.info("-------- Executing: {}. {} --------", className, methodName)
        val countdown = StopWatch()

        // засекаем время
        countdown.start()
        val result = proceedingJoinPoint.proceed()
        countdown.stop()
        log.info("-------- Executing time of {}. {} :: {} ms --------", className, methodName, countdown.totalTimeMillis)
        return result
    }
}