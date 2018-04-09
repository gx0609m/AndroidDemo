package com.example.administrator.androiddemo.thread.threadpool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gx on 2018/3/28 0028
 */

public class ThreadPoolLearningActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_learning);

    }

    /**
     * 使用自定义线程池（这里的自定义是相对于没有使用Executors的五个工厂方法创建线程池来说的，使用了不同的workQueue）
     * 同时也使用了自定义的runnable--PriorityRunnable
     */
    private void customThreadPool() {
        /*
         * 创建一个优先级线程池非常有用，它可以在线程池中线程数量不足或系统资源紧张时，优先处理我们想要先处理的任务，
         * 而优先级低的则放到后面再处理，这极大改善了系统默认线程池以FIFO方式处理任务的不灵活
         */

        ExecutorService priorityThreadPool = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS,
                new PriorityBlockingQueue<Runnable>());
        for (int i = 1; i <= 10; i++) {
            final int priority = 1;
            priorityThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程：" + threadName + ",正在执行优先级为：" + priority + "的任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
