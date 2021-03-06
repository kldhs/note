package com.utils.aop;

/**
 * @author xs
 * @date 2021/11/10 11:02
 */

import com.utils.txtutil.TxtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogIntercept{
    private static Logger logger = LoggerFactory.getLogger(LogIntercept.class);
    @Pointcut("execution(public * com.utils.aop.*Action.*(..))")
    public void actionLog(){}


    @Before("actionLog()")
    public void before() {
        this.printLog(" @Before  actionLog() 准备打印action层日志...  ");
    }

    @Around("actionLog()")
    public void around(ProceedingJoinPoint pjp) throws Throwable{
        this.printLog(" @Around  actionLog() 准备打印action层日志... ");
        pjp.proceed();
        this.printLog(" @Around  actionLog() action层逻辑已经执行完成  ");
    }


    @After("actionLog()")
    public void after() {
        this.printLog(" @After  actionLog() action层逻辑已经执行完成  ");
    }


    private void printLog(String str){
        logger.info(str);
    }

}