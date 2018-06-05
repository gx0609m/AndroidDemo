package com.example.administrator.androiddemo.messagemechanism;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * Handler相关
 * <p>
 * Created by gx on 2018/6/4 0004
 */

public class MessageMechanismLearningActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_mechanism_learning);
    }

    class MyThread extends Thread {

        Handler handler;

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };
            Looper.loop();
        }
    }
}
