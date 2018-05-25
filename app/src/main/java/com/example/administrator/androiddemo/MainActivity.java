package com.example.administrator.androiddemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.androiddemo.activity.ActivityLearningActivity;
import com.example.administrator.androiddemo.algorithm.SortLearningActivity;
import com.example.administrator.androiddemo.animation.AnimationLearningActivity;
import com.example.administrator.androiddemo.broadcast.BroadcastLearningActivity;
import com.example.administrator.androiddemo.refreshloading.RefreshLoadingLearningActivity;
import com.example.administrator.androiddemo.view.ViewLearningActivity;
import com.example.administrator.androiddemo.eventdispatch.EventDispatchLearningActivity;
import com.example.administrator.androiddemo.imagecompress.ImageCompressLearningActivity;
import com.example.administrator.androiddemo.imageload.ImageLoadLearningActivity;
import com.example.administrator.androiddemo.network.NetworkLearningActivity;
import com.example.administrator.androiddemo.recycleview.RecycleViewLearningActivity;
import com.example.administrator.androiddemo.service.ServiceLearningActivity;
import com.example.administrator.androiddemo.thread.ThreadCorrelationLearningActivity;
import com.example.administrator.androiddemo.tips.TipsLearningActivity;
import com.example.administrator.androiddemo.viewpager.ViewPagerWithFragmentLearningActivity;
import com.example.administrator.androiddemo.webview.WebViewLearningActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button activity;
    private Button service;
    private Button broadCast;
    private Button recycleview;
    private Button inputDialog;
    private Button imageload;
    private Button imageCompress;
    private Button threadCorrelation;
    private Button algorithm;
    private Button webView;
    private Button tips;
    private Button network;
    private Button customView;
    private Button animation;
    private Button eventDispatch;
    private Button viewPager;
    private Button refreshLoading;

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
            case R.id.recycleview:
                intent = new Intent(MainActivity.this, RecycleViewLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.inputDialog:
                showInputDialog();
                break;
            case R.id.imageload:
                intent = new Intent(MainActivity.this, ImageLoadLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.imageCompress:
                intent = new Intent(MainActivity.this, ImageCompressLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.threadCorrelation:
                intent = new Intent(MainActivity.this, ThreadCorrelationLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.algorithm:
                intent = new Intent(MainActivity.this, SortLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.webView:
                intent = new Intent(MainActivity.this, WebViewLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.tips:
                intent = new Intent(MainActivity.this, TipsLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.network:
                intent = new Intent(MainActivity.this, NetworkLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.customView:
                intent = new Intent(MainActivity.this, ViewLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.animation:
                intent = new Intent(MainActivity.this, AnimationLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.eventDispatch:
                intent = new Intent(MainActivity.this, EventDispatchLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.viewPager:
                intent = new Intent(MainActivity.this, ViewPagerWithFragmentLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.refreshLoading:
                intent = new Intent(MainActivity.this, RefreshLoadingLearningActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 弹出输入对话框（输入短信验证码）
     */
    private void showInputDialog() {
        final EditText editText = new EditText(this);
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);// 弹出数字输入框
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);// 输入类型为数字文本
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("我是一个输入Dialog").setView(editText);
        builder.setCancelable(false); // 屏蔽返回按钮
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false); // 点屏幕其他区域不取消
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void initView() {
        activity = (Button) findViewById(R.id.activity);
        activity.setOnClickListener(this);

        service = (Button) findViewById(R.id.service);
        service.setOnClickListener(this);

        broadCast = (Button) findViewById(R.id.broadCast);
        broadCast.setOnClickListener(this);

        recycleview = (Button) findViewById(R.id.recycleview);
        recycleview.setOnClickListener(this);

        inputDialog = (Button) findViewById(R.id.inputDialog);
        inputDialog.setOnClickListener(this);

        imageload = (Button) findViewById(R.id.imageload);
        imageload.setOnClickListener(this);

        imageCompress = (Button) findViewById(R.id.imageCompress);
        imageCompress.setOnClickListener(this);

        threadCorrelation = (Button) findViewById(R.id.threadCorrelation);
        threadCorrelation.setOnClickListener(this);

        algorithm = (Button) findViewById(R.id.algorithm);
        algorithm.setOnClickListener(this);

        webView = (Button) findViewById(R.id.webView);
        webView.setOnClickListener(this);

        tips = (Button) findViewById(R.id.tips);
        tips.setOnClickListener(this);

        network = (Button) findViewById(R.id.network);
        network.setOnClickListener(this);

        customView = (Button) findViewById(R.id.customView);
        customView.setOnClickListener(this);

        animation = (Button) findViewById(R.id.animation);
        animation.setOnClickListener(this);

        eventDispatch = (Button) findViewById(R.id.eventDispatch);
        eventDispatch.setOnClickListener(this);

        viewPager = (Button) findViewById(R.id.viewPager);
        viewPager.setOnClickListener(this);

        refreshLoading = (Button) findViewById(R.id.refreshLoading);
        refreshLoading.setOnClickListener(this);
    }
}
