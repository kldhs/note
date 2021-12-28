package com.utils.threadpool.executorsandspring;

import com.utils.Test;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xs
 * @date 2021/04/01 22:07
 */
@Service
public class AsyncServiceImpl implements AsyncService {
    int i = 0;

    @Override
    @Async("asyncServiceExecutor")
    /**
     * 这个注解用于标注某个方法或某个类里面的所有方法都是需要异步处理的。
     * 被注解的方法被调用的时候，会在新线程中执行，而调用它的方法会在原
     * 来的线程中执行。这样可以避免阻塞、以及保证任务的实时性。适用于处
     * 理log、发送邮件、短信……等。
     *
     * 参数：可用于确定执行此操作时要使用的目标执行程序（用于指定使用哪一个线程池执行）。
     * （如果不指定，会首先找当前包下自定义的线程池，如果找不到会自己创建线程池？）
     *
     *需配合@EnableAsync（允许异步执行）使用
     */
    public void executeAsync() {
        ////synchronized ("aa") {
        //i++;
        //System.err.println(i + " start executeAsync");
        //try {
        //    Thread.sleep(3000);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        //System.err.println(i + " end executeAsync");
        ////}

        for (int i = 0; i < 600; i++) {

        }

    }
}
