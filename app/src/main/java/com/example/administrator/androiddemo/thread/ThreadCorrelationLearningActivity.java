package com.example.administrator.androiddemo.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.thread.thread.ThreadLearningActivity;
import com.example.administrator.androiddemo.thread.threadpool.ThreadPoolLearningActivity;

/**
 * Created by gx on 2018/3/28 0028
 */

public class ThreadCorrelationLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button thread;
    private Button threadPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_correlation_learning);

        initView();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.thread:
                intent = new Intent(ThreadCorrelationLearningActivity.this, ThreadLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.threadPool:
                intent = new Intent(ThreadCorrelationLearningActivity.this, ThreadPoolLearningActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        thread = (Button) findViewById(R.id.thread);
        thread.setOnClickListener(this);

        threadPool = (Button) findViewById(R.id.threadPool);
        threadPool.setOnClickListener(this);
    }
}
