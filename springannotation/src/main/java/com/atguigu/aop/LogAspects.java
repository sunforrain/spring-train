package com.atguigu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 视频27 AOP-AOP功能测试
 * 切面类
 * @author lfy
 *
 * @Aspect： 告诉Spring当前类是一个切面类
 *
 */
@Aspect
public class LogAspects {

    //抽取公共的切入点表达式(两种写法,一种是全部方法,一种是只有div
    //1、本类引用,如本类@Before的用法,单写方法名就可
    //2、其他的切面引用,如本类@After的用法,写方法全名
//    @Pointcut("execution(public int com.atguigu.aop.MathCalculator.*(..))")
    @Pointcut("execution(public int com.atguigu.aop.MathCalculator.div(int, int))")
    public void pointCut(){};

    //@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(""+joinPoint.getSignature().getName()+"运行。。。@Before:参数列表是：{"+ Arrays.asList(args)+"}");
    }

    //@After在目标方法结束时切入(无论正常异常结束都调用)；切入点表达式（指定在哪个方法切入）
    @After("com.atguigu.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(""+joinPoint.getSignature().getName()+"结束。。。@After");
    }

    // @AfterReturning 正常返回时切入
    //JoinPoint一定要出现在参数表的第一位
    @AfterReturning(value="pointCut()",returning="result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println(""+joinPoint.getSignature().getName()+"正常返回。。。@AfterReturning:运行结果：{"+result+"}");
    }

    // @AfterThrowing 异常返回时切入
    // throwing用来告诉spring这个方法是用来接异常的,参数的exception可以拿到方法运行的异常
    @AfterThrowing(value="pointCut()",throwing="exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println(""+joinPoint.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");
    }
}
