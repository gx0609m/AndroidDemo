package com.example.administrator.androiddemo.thread.threadpool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gx on 2018/3/28 0028
 */

public class ThreadPoolLearningActivity extends BaseActivity implements View.OnClickListener {

    /*
     * 创建线程池的两种方式：
     *      1.new ThreadPoolExecutor(…)方式；
     *      2.Executors 的工厂方法；
     */

    private static final String TAG = "ThreadPoolLearning";

    private Button fixedThreadPool; // 固定线程数量的线程池
    private Button cachedThreadPool; // 线程数量可动态变化的线程池
    private Button scheduledThreadPool; // 固定线程数量的线程池，支持定时及周期性的执行任务
    private Button singleThreadExecutor; // 单线程的线程池
    private Button singleThreadScheduledExecutor; // 单线程的线程池，支持定时及周期性的执行任务

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_learning);

        initView();
    }

    /**
     * 创建固定线程数量的线程池
     */
    private void createFixedThreadPool() {
        // 构建线程池服务executorService，线程池大小为 5
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            // 构建Runnable对象
            Runnable runnable = new Runnable() {   // 每次循环都 new 一个Runnable对象，注意，是Runnable，不是Thread
                @Override
                public void run() {
                    Log.e(TAG, Thread.currentThread().getName());
                }
            };
            // 线程池服务executorService ——> 执行execute ——> runnable
            executorService.execute(runnable);
        }
    }

    /**
     * 创建线程数量可动态变化的线程池
     */
    private void createCachedThreadPool() {
        // 构建线程池服务executorService，因为线程池数量是动态调整的，所以没有指定线程个数
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            // 构建Runnable对象
            Runnable syncRunnable = new Runnable() { // 每次循环都 new 一个Runnable对象，注意，是Runnable，不是Thread
                @Override
                public void run() {
                    Log.e(TAG, Thread.currentThread().getName());
                }
            };
            // 线程池服务executorService ——> 执行execute ——> runnable
            executorService.execute(syncRunnable);
        }
    }

    /**
     * 创建固定线程数量的线程池，支持定时及周期性的执行任务
     */
    private void createScheduledThreadPool() {
        /*
         * 通过ScheduledExecutorService执行的周期任务，如果任务执行过程中抛出了异常，那么ScheduledExecutorService就会
         * 停止执行任务，且不会再周期地执行该任务了；
         * 所以你如果想保住任务都一直被周期执行，那么catch一切可能的异常；
         */

        // 构建线程池服务scheduledExecutorService，线程池大小为 5，注意 ScheduledExecutorService 与 ExecutorService 的区别
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 20; i++) {
            // 构建Runnable对象
            Runnable syncRunnable = new Runnable() { // 每次循环都 new 一个Runnable对象，注意，是Runnable，不是Thread
                @Override
                public void run() {
                    Log.e(TAG, Thread.currentThread().getName());
                }
            };
            // 定时线程池服务scheduledExecutorService ——> 执行execute ——> runnable
//            scheduledExecutorService.schedule(syncRunnable, 5000, TimeUnit.MILLISECONDS); // 延时5秒执行runnable
            scheduledExecutorService.scheduleAtFixedRate(syncRunnable,5000,2000,TimeUnit.MILLISECONDS); // 延迟5秒后执行，之后每隔2秒执行一次

            /*
             * scheduleAtFixedRate ——— 由rate可理解为，不论前一个任务是否执行完，每隔2秒执行；
             * scheduleWithFixedDelay ——— 由delay可理解为，需要等待前一个任务执行完，再隔2秒执行；
             *
             * scheduledExecutorService.scheduleAtFixedRate(syncRunnable,5000,2000,TimeUnit.MILLISECONDS); // 延迟5秒后执行，之后每隔2秒执行一次
             * scheduledExecutorService.scheduleWithFixedDelay(syncRunnable,5000,2000,TimeUnit.MILLISECONDS); // 延迟5秒后执行，之后每隔2秒执行一次
             */
        }
    }

    /**
     * 创建单线程的线程池
     * <p>
     * 只用唯一的工作线程来执行任务，保证所有任务按照指定顺序（FIFO）执行
     */
    private void createSingleThreadExecutor() {
        // 构建线程池服务executorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 20; i++) {
            // 构建Runnable对象
            Runnable syncRunnable = new Runnable() { // 每次循环都 new 一个Runnable对象，注意，是Runnable，不是Thread
                @Override
                public void run() {
                    Log.e(TAG, Thread.currentThread().getName());
                }
            };
            // 线程池服务executorService ——> 执行execute ——> runnable
            executorService.execute(syncRunnable);
        }
    }

    /**
     * 创建单线程的线程池，支持定时及周期性的执行任务
     */
    private void createSingleThreadScheduledExecutor() {
        // 构建线程池服务scheduledExecutorService
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < 10; i++) {
            // 构建Runnable对象
            Runnable runnable = new Runnable() { // 每次循环都 new 一个Runnable对象，注意，是Runnable，不是Thread
                @Override
                public void run() {
                    Log.e(TAG, Thread.currentThread().getName());
                }
            };
            scheduledExecutorService.schedule(runnable, 5000, TimeUnit.MILLISECONDS);
//            scheduledExecutorService.scheduleAtFixedRate(runnable,5000,2000,TimeUnit.MILLISECONDS);
//            scheduledExecutorService.scheduleWithFixedDelay(runnable,5000,2000,TimeUnit.MILLISECONDS);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fixedThreadPool:
                createFixedThreadPool();
                break;
            case R.id.cachedThreadPool:
                createCachedThreadPool();
                break;
            case R.id.scheduledThreadPool:
                createScheduledThreadPool();
                break;
            case R.id.singleThreadExecutor:
                createSingleThreadExecutor();
                break;
            case R.id.singleThreadScheduledExecutor:
                createSingleThreadScheduledExecutor();
                break;
        }
    }

    private void initView() {
        fixedThreadPool = (Button) findViewById(R.id.fixedThreadPool);
        fixedThreadPool.setOnClickListener(this);

        cachedThreadPool = (Button) findViewById(R.id.cachedThreadPool);
        cachedThreadPool.setOnClickListener(this);

        scheduledThreadPool = (Button) findViewById(R.id.scheduledThreadPool);
        scheduledThreadPool.setOnClickListener(this);

        singleThreadExecutor = (Button) findViewById(R.id.singleThreadExecutor);
        singleThreadExecutor.setOnClickListener(this);

        singleThreadScheduledExecutor = (Button) findViewById(R.id.singleThreadScheduledExecutor);
        singleThreadScheduledExecutor.setOnClickListener(this);
    }

}
