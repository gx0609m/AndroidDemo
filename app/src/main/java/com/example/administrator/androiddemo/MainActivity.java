package com.example.administrator.androiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.activity.ActivityLearningActivity;
import com.example.administrator.androiddemo.broadcast.BroadcastLearningActivity;
import com.example.administrator.androiddemo.service.ServiceLearningActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button activity;

    private Button service;

    private Button broadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.activity:
                intent = new Intent(MainActivity.this, ActivityLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.service:
                intent = new Intent(MainActivity.this, ServiceLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.broadCast:
                intent = new Intent(MainActivity.this, BroadcastLearningActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView(){
        activity = (Button) findViewById(R.id.activity);
        activity.setOnClickListener(this);

        service = (Button) findViewById(R.id.service);
        service.setOnClickListener(this);

        broadCast = (Button) findViewById(R.id.broadCast);
        broadCast.setOnClickListener(this);
    }
}
