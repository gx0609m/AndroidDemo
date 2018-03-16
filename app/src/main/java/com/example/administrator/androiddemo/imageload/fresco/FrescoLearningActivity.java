package com.example.administrator.androiddemo.imageload.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androiddemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by gx on 2018/3/15 0015
 */

public class FrescoLearningActivity extends AppCompatActivity {

    /**
     * 使用Fresco（https://www.fresco-cn.org/）：
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
     *       在加载图片之前，须初始化Fresco类：
     *          只需要调用Fresco.initialize一次即可完成初始化，
     *          在 Application 里面做这件事再适合不过了，注意多次的调用初始化是无意义的
     *
     */

    // 加载网络图片前显示一张占位图
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_learning);

        initView();
        simpleLoadImage();
    }

    /**
     * 使用Fresco简单的加载网络图片
     *
     * 剩下的，Fresco会替你完成:
     *      显示占位图直到加载完成；
     *      下载图片；
     *      缓存图片；
     *      图片不再显示时，从内存中移除；
     *      等等等等。
     */
    private void simpleLoadImage(){
        /**
         * 注意不要忘记添加网络权限
         */
        Uri uri = Uri.parse("http://f2.topitme.com/2/b9/71/112660598401871b92l.jpg");
        simpleDraweeView.setImageURI(uri);
    }

    private void initView(){
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
    }
}
