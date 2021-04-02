package com.utils.threadpool;

import com.utils.config.ThreadPoolConfigPoJo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xs
 * @date 2021/04/01 22:05
 */

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Autowired
    ThreadPoolConfigPoJo threadPoolConfigPoJo;

    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new VisiableThreadPoolTaskExecutor();
        //ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(threadPoolConfigPoJo.getCorePoolSize());
        //配置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolConfigPoJo.getMaxPoolSize());
        //配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(threadPoolConfigPoJo.getQueueCapacity());
        //配置线程池中的线程的名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix(threadPoolConfigPoJo.getThreadNamePrefix());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
