package com.utils.threadpool;

import com.utils.spring.SpringBootUtil;
import com.utils.threadpool.executorsandspring.AsyncService;

/**
 * @author xs
 * @date 2021/8/19 9:08
 * 线程池
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i <=100 ; i++) {
            AsyncService asyncService = (AsyncService) SpringBootUtil.getBean(AsyncService.class);
            System.err.println(i+"----start submit");
            //调用service层的任务
            asyncService.executeAsync();
            System.err.println(i+"----end submit");
        }
    }
}
