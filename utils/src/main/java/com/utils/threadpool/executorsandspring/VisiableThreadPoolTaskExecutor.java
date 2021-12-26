package com.utils.threadpool.executorsandspring;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xs
 * @date 2021/04/01 22:10
 */
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        if (null == threadPoolExecutor) {
            return;
        }
        System.err.println(
                "getThreadNamePrefix(): " + this.getThreadNamePrefix() +
                        "。 prefix: " + prefix +
                        "。 getTaskCount(): " + threadPoolExecutor.getTaskCount() +
                        "。 getCompletedTaskCount(): " + threadPoolExecutor.getCompletedTaskCount() +
                        "。 getActiveCount(): " + threadPoolExecutor.getActiveCount() +
                        "。 getQueue().size()(): " + threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("void execute(Runnable task) 方法被调用");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("execute(void execute(Runnable task, long startTimeout) 方法被调用");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("Future<?> submit(Runnable task) 方法被调用");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("Future<T> submit(Callable<T> task) 方法被调用");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("ListenableFuture<?> submitListenable(Runnable task) 方法被调用");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("ListenableFuture<T> submitListenable(Callable<T> task) 方法被调用");
        return super.submitListenable(task);

    }
}
