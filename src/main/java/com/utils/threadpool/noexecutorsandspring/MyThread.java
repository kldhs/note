package com.utils.threadpool.noexecutorsandspring;

/**
 * @author xs
 * @date 2021/04/05 23:50
 */
public class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始执行..." + System.currentTimeMillis());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "结束执行..." + System.currentTimeMillis());
    }
}
