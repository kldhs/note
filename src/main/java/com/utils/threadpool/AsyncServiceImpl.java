package com.utils.threadpool;

import org.springframework.scheduling.annotation.Async;

/**
 * @author xs
 * @date 2021/04/01 22:07
 */
public class AsyncServiceImpl implements AsyncService {
    @Override

    @Async("asyncServiceExecutor")

    public void executeAsync() {

        System.err.println("start executeAsync");

        try{

            Thread.sleep(1000);

        }catch(Exception e){

            e.printStackTrace();

        }

        System.err.println("end executeAsync");

    }
}
