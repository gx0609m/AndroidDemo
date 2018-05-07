package com.example.administrator.androiddemo.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.network.okhttp.OkHttpLearningActivity;
import com.example.administrator.androiddemo.network.retrofit.RetrofitLearningActivity;
import com.example.administrator.androiddemo.network.retrofit_rxjava.RetrofitWithRxJavaLearningActivity;

/**
 * 网络请求相关
 * <p>
 * Created by gx on 2018/5/7 0007
 */

public class NetworkLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button okHttp;
    private Button retrofit;
    private Button retrofitWithRxJava;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_learning);

        initView();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.okHttp:
                intent = new Intent(NetworkLearningActivity.this, OkHttpLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.retrofit:
                intent = new Intent(NetworkLearningActivity.this, RetrofitLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.retrofitWithRxJava:
                intent = new Intent(NetworkLearningActivity.this, RetrofitWithRxJavaLearningActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        okHttp = (Button) findViewById(R.id.okHttp);
        okHttp.setOnClickListener(this);

        retrofit = (Button) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);

        retrofitWithRxJava = (Button) findViewById(R.id.retrofitWithRxJava);
        retrofitWithRxJava.setOnClickListener(this);
    }
}
