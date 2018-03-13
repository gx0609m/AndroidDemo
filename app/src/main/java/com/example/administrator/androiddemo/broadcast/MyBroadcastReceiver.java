package com.example.administrator.androiddemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by gx on 2018/3/13 0013
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    /**
     * Android开发中，我们通过广播可以进行数据的传递、通知等，广播的注册、发送、接收类似于 观察者模式
     *
     * 广播--按不同的功能可以进行多种区分：
     *      有序广播、无序广播；
     *      静态广播、动态广播；
     *      全局广播、本地广播；
     *
     */

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * 可以接收到广播之后来处理相应的逻辑，例如可以启动一个Service或者发出一个Notification
         *
         * Android系统在开机、网络状态变化、收到短信、电量低等情况发生时，系统都会发送广播，
         * 因此我们可以通过接收这些 系统广播 来进行我们想要的操作
         */
        Log.e(TAG, "onReceive() executed" + intent.getStringExtra("data"));
    }
}
