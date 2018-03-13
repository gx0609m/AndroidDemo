package com.example.administrator.androiddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/8 0008
 */

public class ActivityLearningActivity2 extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "ActivityLearning2";

    private Button back; // 返回上一级

    private Button goback; // 跳转到上一级

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_learning2);
        Log.d(TAG,"onCreate()执行了");

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);

        goback = (Button) findViewById(R.id.goback);
        goback.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.goback:
                Intent intent = new Intent(ActivityLearningActivity2.this, ActivityLearningActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 当前activity未destroy,
     * 从其他activity到当前activity时，
     * 执行onRestart-->onStart-->onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart()执行了");
    }

    /**
     * 可见，不可交互
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart()执行了");
    }

    /**
     * 可见，可交互
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()执行了");
    }

    /**
     * 可见，不可交互
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()执行了");
    }

    /**
     * 不可见
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()执行了");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()执行了");
    }
}
