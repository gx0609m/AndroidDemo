package com.example.administrator.androiddemo.imageload.glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/16 0016
 */

public class GlideLearningActivity extends AppCompatActivity {

    /**
     * Glide使用：
     *      在app的build.gradle文件中加入如下依赖：
     *           compile 'com.github.bumptech.glide:glide:3.7.0'
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_learning);
    }
}
