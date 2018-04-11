package com.example.administrator.androiddemo.thread.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gx on 2018/3/28 0028
 */

public class ThreadLearningActivity extends BaseActivity implements View.OnClickListener {

    /*
     * 创建新Thread的两种方式：
     *      1.继承Thread类；
     *      2.实现Runnable接口；
     */

    private static final String TAG = "ThreadLearningActivity";

    private Button extendsThread; // 继承Thread类 开启线程
    private Button implementsRunnable; // 实现Runnable接口 开启线程

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_learning);

        initView();
    }

    /**
     * 继承Thread方式来新建线程
     */
    private void withExtendsThread() {
        Thread thread = new MyThread("thread1");
        thread.start();
//        new MyThread("thread1").start();
    }

    private class MyThread extends Thread {

        private String params;

        public MyThread(String param) {
            this.params = param;
        }

        @Override
        public void run() {
            // doSth(params)
            /*
             * 注意，这里不能直接在run()方法中Toast，否则会造成如下异常：
             *      java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
             * 如果想弹吐司，需要通过Handler来处理，
             *             或者是通过runOnUiThread()，不过此时在runOnUiThread中的 Thread.currentThread().getName() 就是 main thread了；
             */
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(ThreadLearningActivity.this, Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
//                }
//            });

//            Toast.makeText(ThreadLearningActivity.this, Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
            Log.d(TAG, Thread.currentThread().getName());
        }
    }

    /**
     * 实现Runnable接口方式来新建线程
     */
    private void withImplementsRunnable() {
        MyRunnable myRunnable = new MyRunnable("thread2");
        Thread thread = new Thread(myRunnable);
        thread.start();
//        new Thread(new MyRunnable("thread2")).start();
    }

    private class MyRunnable implements Runnable {

        private String params;

        public MyRunnable(String params) {
            this.params = params;
        }

        @Override
        public void run() {
            // doSth(params)
             /*
             * 注意，这里不能直接在run()方法中Toast，否则会造成如下异常：
             *      java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
             * 如果想弹吐司，需要通过Handler来处理，
             *             或者是通过runOnUiThread()，不过此时在runOnUiThread中的 Thread.currentThread().getName() 就是 main thread了；
             */
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(ThreadLearningActivity.this, Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
//                }
//            });

//            Toast.makeText(ThreadLearningActivity.this, Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
            Log.d(TAG, Thread.currentThread().getName());
        }
    }

    /**
     * 销毁线程方法
     */
    private void destroyThread(Thread thread) {
        try {
            if (null != thread && Thread.State.RUNNABLE == thread.getState()) {
                try {
                    Thread.sleep(500);
                    thread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            thread = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.extendsThread:
                withExtendsThread();
                break;
            case R.id.implementsRunnable:
                withImplementsRunnable();
                break;
        }
    }

    private void initView() {
        extendsThread = (Button) findViewById(R.id.extendsThread);
        extendsThread.setOnClickListener(this);

        implementsRunnable = (Button) findViewById(R.id.implementsRunnable);
        implementsRunnable.setOnClickListener(this);
    }

}
