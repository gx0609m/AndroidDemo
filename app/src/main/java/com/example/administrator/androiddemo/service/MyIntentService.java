package com.example.administrator.androiddemo.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.androiddemo.Constant;

/**
 * Created by gx on 2018/3/9 0009
 */

public class MyIntentService extends IntentService {

    /**
     * 我们知道，在Service里面不能直接进行耗时操作，一般都需要去开启子线程去做一些事情，
     * 但我们自己去管理Service的生命周期以及子线程并非是个优雅的做法；好在Android给我们提供了一个类，
     * 叫做IntentService，我们看下注释：
     *      IntentService is a base class for {@link Service}s that handle asynchronous
     *      requests (expressed as {@link Intent}s) on demand.  Clients send requests
     *      through {@link android.content.Context#startService(Intent)} calls; the
     *      service is started as needed, handles each Intent in turn using a worker
     *      thread, and stops itself when it runs out of work.
     * 大概的意思就是：
     *      IntentService是一个基于Service的类，用来处理异步的请求。你可以通过startService(Intent)来提交请求，
     *      该Service会在需要的时候创建，依次在工作线程中处理请求（Intent）,当完成所有的任务后会自己关闭。
     *
     * 因此，我们使用了IntentService最起码有两个好处：
     *      一方面不需要自己去new Thread了；
     *      另一方面不需要考虑在什么时候关闭该Service了。
     *
     * IntentService使用的整体逻辑就是：
     *      继承IntentService，然后复写onHandleIntent方法，根据传入的intent来选择具体的操作，
     *      如果需要intentService后台的线程每处理完一个请求就反馈给Activity，然后Activity去更新UI等，
     *      可以通过service中发送广播，activity中接收广播，然后进行处理的方式来进行
     */

    private static final String TAG = "MyIntentService";

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
          /*  // 根据intent中带String参数的不同，执行不同的任务
            String taskName = intent.getExtras().getString("taskName");
            switch (taskName){
                case "task1":
                    Log.d(TAG,"onHandleIntent() do task1");
                    break;
                case "task2":
                    Log.d(TAG,"onHandleIntent() do task2");
                    break;
            }*/

            // 根据intent中的action的不同，执行不同的任务
            String action = intent.getAction();
            if (Constant.UPLOAD_IMG_ACTION.equals(action)){
                uploadImg(); // 执行耗时任务
            }
        }
        Log.d(TAG,"onHandleIntent() executed");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate() executed");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy() executed");
        super.onDestroy();
    }

    private void uploadImg(){
        try {
            // 模拟上传耗时
            Thread.sleep(3000);

            // （耗时操作成功后）发送广播（接受时，通过action区分）
            Intent intent = new Intent(Constant.UPLOAD_IMG_RESULT_ACTION);
            sendBroadcast(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
