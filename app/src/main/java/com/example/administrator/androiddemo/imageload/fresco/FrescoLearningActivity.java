package com.example.administrator.androiddemo.imageload.fresco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/15 0015
 */

public class FrescoLearningActivity extends AppCompatActivity {

    /**
     * 使用Fresco：
     *      在app的build.gradle中添加依赖:
     *          dependencies {
     *               // 其他依赖
     *               compile 'com.facebook.fresco:fresco:0.12.0'
     *          }
     *      根据需求添加以下依赖：
     *          dependencies {
     *               // 在 API < 14 上的机器支持 WebP 时，需要添加
     *               compile 'com.facebook.fresco:animated-base-support:0.12.0'
     *               // 支持 GIF 动图，需要添加
     *               compile 'com.facebook.fresco:animated-gif:0.12.0'
     *               // 支持 WebP （静态图+动图），需要添加
     *               compile 'com.facebook.fresco:animated-webp:0.12.0'
     *               compile 'com.facebook.fresco:webpsupport:0.12.0'
     *               // 仅支持 WebP 静态图，需要添加
     *               compile 'com.facebook.fresco:webpsupport:0.12.0'
     *           }
     *       在加载图片之前，须初始化Fresco类。只需要调用Fresco.initialize一次即可完成初始化，
     *       在 Application 里面做这件事再适合不过了，注意多次的调用初始化是无意义的
     *
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_learning);
    }
}
