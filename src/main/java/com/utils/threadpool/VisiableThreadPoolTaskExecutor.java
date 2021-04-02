package com.utils.threadpool;

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
        System.err.println(this.getThreadNamePrefix() + " " +
                prefix + " " +
                "taskCount: " + threadPoolExecutor.getTaskCount() +
                "completedTaskCount: " + threadPoolExecutor.getCompletedTaskCount() +
                "activeCount: " + threadPoolExecutor.getActiveCount() +
                "queueSize: " + threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("execute(Runnable task)");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("execute(Runnable task,long startTimeout)");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("submit(Runnable task)");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("submit(Callable<T> task)");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("submitListenable(Runnable task)");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("submitListenable(Callable<T> task)");
        return super.submitListenable(task);
    }
}
