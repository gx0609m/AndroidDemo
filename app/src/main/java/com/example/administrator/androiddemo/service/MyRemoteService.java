package com.example.administrator.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.androiddemo.MyAIDLService;

/**
 * 远程Service (AndroidManifest.xml 中的service标签中加上 android:process=":remote" 属性)
 * Created by gx on 2018/3/9 0009
 */

public class MyRemoteService extends Service {

    public static final String TAG = "MyRemoteService";

    private MyRemoteBinder myRemoteBinder = new MyRemoteBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        /*try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Log.d(TAG, "process id is " + android.os.Process.myPid());
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
//        return myRemoteBinder; // 返回MyRemoteBinder的实例(未牵涉到AIDL)
        return aidlBinder; // 将MyAIDLService.Stub的实现返回(因为Stub其实就是Binder的子类，所以在onBind()方法中可以直接返回Stub的实例)
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    public class MyRemoteBinder extends Binder {
        public void doRemoteThing() {
            Log.d(TAG, "doRemoteThing() executed");
        }
    }

    /**
     * 对MyAIDLService.Stub进行实现
     */
    MyAIDLService.Stub aidlBinder = new MyAIDLService.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            if (str != null) {
                return str.toUpperCase();
            }
            return null;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
