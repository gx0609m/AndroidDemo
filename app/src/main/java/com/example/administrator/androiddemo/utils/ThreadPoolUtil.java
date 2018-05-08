package com.example.administrator.androiddemo.utils;

import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池单例类
 * <p>
 * Created by gx on 2018/5/8 0008
 */

public class ThreadPoolUtil {

    private static volatile ThreadPoolUtil threadPoolUtil;

    private static volatile ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolUtil() {
        threadPoolExecutor = new ThreadPoolExecutor(
                5,
                5,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new FifoPriorityThreadPoolExecutor.DefaultThreadFactory());
    }

    public static ThreadPoolExecutor getInstance() {
        if (threadPoolUtil == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPoolUtil == null)
                    threadPoolUtil = new ThreadPoolUtil();
            }
        }
        return threadPoolExecutor;
    }
}
