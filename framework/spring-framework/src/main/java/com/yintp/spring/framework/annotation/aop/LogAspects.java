package com.yintp.spring.framework.annotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 切面类
 */
@Aspect
public class LogAspects {

    /**
     * 公共切入点表达式
     * 1、本类引用：直接写方法名，pointCut()
     * 2、其它的切面类引用：方法全类名，com.andy.java.spring.annotation.aop.LogAspects.pointCut()
     */
    @Pointcut("execution(public int com.andy.java.spring.annotation.aop.MathCalculator.*(..))")
    public void pointCut(){};

    /**
     * @Before:在目标方法之前切入，切入点表达式（指定在哪个方法切入）
     */
//    @Before("public int com.andy.java.spring.annotation.aop.MathCalculator.*(..)")
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() + "运行...@Before,参数列表是：{"+ Arrays.asList(args)+"}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "结束...@After");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName()+"正常返回...@AfterReturning，返回值是：{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println(joinPoint.getSignature().getName()+"异常...@AfterThrowing，异常是：{"+exception+"}");
    }
}
