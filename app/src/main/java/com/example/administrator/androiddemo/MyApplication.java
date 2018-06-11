package com.example.administrator.androiddemo;

import android.app.Application;

import com.example.administrator.androiddemo.imageload.fresco.ImageLoaderConfig;
import com.example.administrator.androiddemo.utils.CrashHandler;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by gx on 2018/3/15 0015
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, ImageLoaderConfig.getImagePipelineConfig(this));
//        CrashHandler.getInstance().init(getApplicationContext());  // 开发阶段注释掉此行
    }
}
