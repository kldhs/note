package com.utils.threadpool;

import org.springframework.stereotype.Component;

/**
 * @author xs
 * @date 2021/04/01 22:06
 */
@Component
public interface AsyncService {

    /**
     * 执行异步任务
     */
    void executeAsync();
}
