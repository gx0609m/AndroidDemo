package com.example.administrator.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 后台Service
 * Created by gx on 2018/3/8 0008
 */

public class MyService extends Service {

    /**
     * 即使Activity被销毁，或者程序被关闭，只要进程还在，Service就可以继续运行。
     * 比如说一些应用程序，始终需要与服务器之间始终保持着心跳连接，就可以使用Service来实现。
     * 为了不阻塞主线程，我们可以在Service中再创建一个子线程，然后在这里去处理耗时逻辑就没问题了。
     *
     * 那既然在Service里也要创建一个子线程，为什么不直接在Activity里创建呢？
     * 这是因为Activity很难对Thread进行控制，当Activity被销毁之后，就没有任何其它的办法可以再重新获取到之前创建的子线程的实例。
     * 而且在一个Activity中创建的子线程，另一个Activity无法对其进行操作。
     * 但是Service就不同了，所有的Activity都可以与Service进行关联，然后可以很方便地操作其中的方法，
     * 即使Activity被销毁了，之后只要重新与Service建立关联，就又能够获取到原有的Service中Binder的实例。
     * 因此，使用Service来处理后台任务，Activity就可以放心地finish，完全不需要担心无法对后台任务进行控制的情况。
     *
     */

    public static final String TAG = "MyService";

    private MyBinder myBinder = new MyBinder();

    /**
     * （startService方式、bindService方式）启动Service后调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // 和activity的线程id比较，会发现Service其实是运行在主线程里的，
        // 也就是说如果你在Service里编写了非常耗时的代码，程序必定会出现ANR的。
        Log.d(TAG, "MyService thread id is " + Thread.currentThread().getId());

        Log.d(TAG, "onCreate() executed");
    }

    /**
     * （startService方式）启动Service后调用，顺序在onCreate()之后
     *  如果在此Service没有销毁，并且再次调用startService方法，此时只有onStartCommand会被调用，onCreate并不会调用，
     *  当前Service已经被创建过了，不管怎样调用startService()方法，onCreate()方法都不会再执行【除非Service是被销毁后重新创建了】
     *
     *  bindService方式启动Service,不会调用此方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行具体的耗时任务
            }
        }).start();
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 一个Service必须要在既没有和任何Activity关联又处于停止状态的时候才会被销毁
     *
     * 试想下：如果我们既点击了Start Service按钮，又点击了Bind Service按钮会怎么样呢？
     * 这个时候你会发现，不管你是单独点击Stop Service按钮还是Unbind Service按钮，Service都不会被销毁，
     * 必要将两个按钮都点击一下，Service才会被销毁
     *
     * 也就是说，点击Stop Service按钮只会让Service停止，点击Unbind Service按钮只会让Service和Activity解除关联
     * 但是，“ 一个Service必须要在既没有和任何Activity关联又处于停止状态的时候才会被销毁 ”
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    /**
     * （bindService方式）启动Service后调用，顺序在onCreate之后
     *  如果在此Service没有销毁时，并且再次调用bindService方法,和onStartCommand方法不同的是，onBind方法不会再次调用
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return myBinder;  // 此处一定要返回MyBinder实例（？）
    }

    class MyBinder extends Binder{
        public void startDoSomething(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 执行具体的耗时任务
                }
            }).start();
            Log.d(TAG,"startDoSomething() executed");
        }
    }
}
