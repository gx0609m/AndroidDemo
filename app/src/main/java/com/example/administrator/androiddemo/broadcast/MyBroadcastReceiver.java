package com.example.administrator.androiddemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
     *          有序广播需要在Manifest中设置android:priority属性，越大优先级越高，这个值介于[-1000,1000]；
     *          发送时通过sendOrderedBroadcast(**,**)方法；
     *          可以通过abortBroadcast()终止广播，来实现拦截；
     *              （关于 有序广播 和 监听系统广播 可以参考：http://blog.csdn.net/liuhe688/article/details/6955668）
     *      静态广播、动态广播；
     *      全局广播、本地广播；
     *          本地广播须使用LocalBroadcastManager
     *
     * 使用广播的整体流程大概是：
     *      1.自定义一个类继承自BroadcastReceiver,重写onReceive()方法；（单独类文件、内部类形式）
     *      2.在Activity/Service等context中创建自定义类--new MyBroadcastReceiver();
     *      3.注册此广播（
     *              动态注册、静态注册；
     *              动态注册的广播需要在context中解除注册；
     *              注册时,通过IntentFilter指定此广播接收的action）；
     *                  【在创建完我们的BroadcastReceiver之后，还不能够使它进入工作状态，我们需要为它注册一个指定的广播地址--action；
     *                  没有注册广播地址的BroadcastReceiver就像一个缺少选台按钮的收音机，虽然功能俱备，但也无法收到电台的信号。】
     *      4.Activity/Service中发送广播（发送时，通过Intent携带action）；
     *      5.如果action与我们定义的MyBroadcastReceiver中的action相同，则此广播会接收，然后我们可在onReceive()中进行逻辑处理；
     *
     *  需要注意的是：
     *      如果我们使用内部类的形式来实现应用级别的广播（静态注册），
     *      就必须消除内部类对外部类（组件）的持有引用依赖，因此我们最好使用静态内部类，即这种形式：
     *          public static class MyBroadcastReceiver extends BroadcastReceiver{
     *              ...
     *          }
     *      然后我们在Manifest文件中进行注册：
     *          <receiver android:name="com.***.***.BroadcastLearningActivity$MyBroadcastReceiver">
     *              <intent-filter>
     *                  <action android:name="com.dsw.data"/>
     *              </intent-filter>
     *          </receiver>
     *      这里需要注意：内部类"."需要替换成"$"符号
     *
     *  另外，细心点可以发现，我们在注册BroadcastReceiver的时候，会指定这样一个属性：
     *      android:exported="false"，
     *  这个属性就是指定我们的广播在本进程中进行使用，当我们的广播将这个属性设置为true的时候，
     *  则其他应用中就可以发送到广播让我们的这个应用接收到，
     *  通过这个特性，也就可以实现  进程间通信  了；
     *
     *  粘性广播（Sticky Broadcast），由于在Android5.0 & API 21中已经失效，所以不建议使用
     */

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * 在onReceive方法内，我们可以获取随广播而来的Intent中的数据，这非常重要，就像无线电一样，包含很多有用的信息，
         * 在接收到广播之后来，可以根据intent中数据的不同，来进行不同的处理，例如可以启动一个Service或者发出一个Notification
         *
         * Android系统在开机、网络状态变化、收到短信、电量低等情况发生时，系统都会发送广播，
         * 因此我们可以通过接收这些 系统广播 来进行我们想要的操作
         *
         * BroadcastReceiver的生命周期只有十秒左右，如果在 onReceive() 内做超过十秒内的事情，就会报错 ，
         * 如果需要处理耗时的业务，我们可以开启Service进行处理，
         *
         * 谨记一点：
         *      广播主要目的是监听获取通知，不要处理繁杂的业务
         */

//        setResultData("");  // 可以通过这两个方法将修改后的数据放回广播中去
//        setResultExtras(new Bundle());

        Log.e(TAG, "onReceive() executed" + intent.getStringExtra("data"));
    }
}
