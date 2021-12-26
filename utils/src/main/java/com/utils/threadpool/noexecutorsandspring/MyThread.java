package com.utils.threadpool.noexecutorsandspring;

import com.utils.socket.client.SocketLongConnectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xs
 * @date 2021/04/05 23:50
 */
public class MyThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MyThread.class);
    @Override
    public void run() {
        logger.info(Thread.currentThread().getName() + "开始执行..." + System.currentTimeMillis());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(Thread.currentThread().getName() + "结束执行..." + System.currentTimeMillis());
    }
}
