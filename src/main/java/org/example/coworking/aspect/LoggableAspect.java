package org.example.coworking.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect for logging the execution of methods annotated with the {@link org.example.coworking.annotations.Loggable} annotation.
 */
@Aspect
public class LoggableAspect {

    /**
     * Pointcut that matches all methods within types annotated with {@link org.example.coworking.annotations.Loggable}.
     */
    @Pointcut("within(@org.example.coworking.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {}

    /**
     * Advice that logs the execution of methods annotated with {@link org.example.coworking.annotations.Loggable}.
     *
     * @param proceedingJoinPoint the join point representing the method being advised.
     * @return the result of the method execution.
     * @throws Throwable if the method execution throws an exception.
     */
    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Execution of method " + proceedingJoinPoint.getSignature() +
                " finished. Execution time is " + end + " ms.");
        return result;
    }
}
