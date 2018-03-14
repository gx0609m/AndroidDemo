package com.example.administrator.androiddemo.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.Constant;
import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/13 0013
 */

public class BroadcastLearningActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "BroadcastLearningActivity";

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    private Button sendBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_learning);

        initView();
        registerReceiver();
    }

    /**
     * 动态注册广播(不要忘记unRegisterReceiver)
     *
     * 静态注册广播只需将此段代码替换成AndroidManifest.xml中的：（静态注册就是在AndroidManifest.xml配置文件中注册）
     *      <receiver android:name="com.example.administrator.androiddemo.broadcast.MyBroadcastReceiver">
     *           <intent-filter>
     *                  <action android:name="com.example.administrator.androiddemo.MY_BROADCAST_RECEIVER_ACTION"/>
     *           </intent-filter>
     *      </receiver>
     *
     * 需要注意的是：
     *      动态注册的广播是跟随当前Context的生命周期的，这种广播具有组件级别的效果，
     *      即当我们调用unregisterReceiver()方法或组件销毁广播就无法被接收处理了；
     *
     *      静态注册的广播是常驻型的，即意味着，即使当前应用关闭了，
     *      如果有带有Constant.MY_BROADCAST_RECEIVER_ACTION 这个action的广播发来，MyBroadcastReceiver也会被调用运行（此特性有很多用处）；
     */
    private void registerReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.MY_BROADCAST_RECEIVER_ACTION); // 接收带有这个action的广播
        registerReceiver(myBroadcastReceiver,intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBroadcast:
                // 发送广播 action可以理解为广播地址
                Intent intent = new Intent();
                intent.setAction(Constant.MY_BROADCAST_RECEIVER_ACTION);
                // 发送广播时可以传送数据
                intent.putExtra("data","data");
                // 发送广播可以通过Context的sendBroadcast()或sendOrderedBroadcast()发送普通广播或有序广播
                sendBroadcast(intent);
                break;
        }
    }

    /**
     * 在适合的生命周期中，解除注册
     *
     * 在实际应用中，我们在Activity或Service中注册了一个BroadcastReceiver，
     * 当这个Activity或Service被销毁时如果没有解除注册，
     * 系统会报一个异常，提示我们是否忘记解除注册了，所以，记得在特定的地方执行解除注册操作
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 动态注册的广播一定要解注册
        unregisterReceiver(myBroadcastReceiver);
    }

    private void initView(){
        sendBroadcast = (Button) findViewById(R.id.sendBroadcast);
        sendBroadcast.setOnClickListener(this);
    }

}
