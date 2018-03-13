package com.example.administrator.androiddemo.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.androiddemo.R;

/**
 * 前台Service
 * Created by gx on 2018/3/8 0008
 */

public class MyFrontService extends Service {

    /**
     * Service几乎都是在后台运行的，一直以来它都是默默地做着辛苦的工作。
     * 但是Service的系统优先级还是比较低的，当系统出现内存不足情况时，就有可能会回收掉正在后台运行的Service。
     * 如果你希望Service可以一直保持运行状态，而不会由于系统内存不足的原因导致被回收，就可以考虑使用前台Service。
     *
     * 前台Service和普通Service最大的区别就在于:
     * 它会一直有一个正在运行的图标在系统的状态栏显示，
     * 下拉状态栏后可以看到更加详细的信息，非常类似于通知的效果。
     *
     * 当然有时候你也可能不仅仅是为了防止Service被回收才使用前台Service，
     * 有些项目由于特殊的需求会要求必须使用前台Service，比如说墨迹天气，
     * 它的Service在后台更新天气数据的同时，还会在系统状态栏一直显示当前天气的信息
     */

    public static final String TAG = "MyFrontService";

    private MyFrontBinder myFrontBinder = new MyFrontBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("最简单的Notification")
                //设置通知内容
                .setContentText("只有小图标、标题、内容");
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        //notifyManager.notify(1, builder.build());

        startForeground(1, builder.build()); // 调用startForeground()方法就可以让MyService变成一个前台Service
        Log.d(TAG, "onCreate() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myFrontBinder;
    }

    public class MyFrontBinder extends Binder{
        public void doSomething(){
            Log.d(TAG,"Front Service doSomething() executed");
        }
    }
}
