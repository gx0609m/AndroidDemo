package com.example.administrator.androiddemo.animation.frameanimation;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 逐帧动画
 * <p>
 * Created by gx on 2018/5/11 0011
 */

public class FrameAnimationLearningActivity extends BaseActivity implements View.OnClickListener {

    private ImageView xmlAnimImv, javaAnimImv;  // xml 方式播放、java代码 方式播放
    private Button startAnim, stopAnim;

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frameanimation_learning);

        initView();
    }

    /**
     * 通过 XML 来 启动 逐帧动画
     * 1.设置动画；
     * 2.获取动画对象；
     * 3.启动动画
     */
    private void frameAnimWithXmlStart() {
        xmlAnimImv.setImageResource(R.drawable.anim_circle);
        animationDrawable = (AnimationDrawable) xmlAnimImv.getDrawable();
        animationDrawable.start();
    }

    /**
     * 通过 XML 来 停止 逐帧动画
     * 1.设置动画；
     * 2.获取动画对象；
     * 3.停止动画
     * <p>
     * 如果 animationDrawable 是全局变量的话，可以直接 animationDrawable.stop()，而省去前两步
     */
    private void frameAnimWithXmlStop() {
        xmlAnimImv.setImageResource(R.drawable.anim_circle);
        animationDrawable = (AnimationDrawable) xmlAnimImv.getDrawable();
        animationDrawable.stop();
    }

    /**
     * 通过 Java代码 来 启动动画
     */
    private void frameAnimWithJavaStart() {
        /*
         * 从 drawable 文件夹中获取 动画资源文件
         */
        animationDrawable = new AnimationDrawable();
        for (int i = 0; i <= 19; i++) {
            int id;
            if (i < 10) {
                id = getResources().getIdentifier("img0000" + i, "drawable", getPackageName());
            } else {
                id = getResources().getIdentifier("img000" + i, "drawable", getPackageName());
            }
            Drawable drawable = getResources().getDrawable(id);
            animationDrawable.addFrame(drawable, 100);
            animationDrawable.setOneShot(true);
        }
        // 获取资源对象
        javaAnimImv.setImageDrawable(animationDrawable);
        // 特别注意：在动画start()之前要先stop()，不然在第一次动画之后会停在最后一帧，这样动画就只会触发一次
        animationDrawable.stop();
        // 启动动画
        animationDrawable.start();
    }

    /**
     * 通过 Java代码 来 关闭动画
     */
    private void frameAnimWithJavaStop() {
        /*
         * 从 drawable 文件夹中获取 动画资源文件
         */
        animationDrawable = new AnimationDrawable();
        for (int i = 0; i <= 19; i++) {
            int id;
            if (i < 10) {
                id = getResources().getIdentifier("img0000" + i, "drawable", getPackageName());
            } else {
                id = getResources().getIdentifier("img000" + i, "drawable", getPackageName());
            }
            Drawable drawable = getResources().getDrawable(id);
            animationDrawable.addFrame(drawable, 100);
            animationDrawable.setOneShot(true);
        }
        animationDrawable.setOneShot(true);
        javaAnimImv.setImageDrawable(animationDrawable);
        animationDrawable.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startAnim:
                frameAnimWithXmlStart();
                frameAnimWithJavaStart();
                break;
            case R.id.stopAnim:
                frameAnimWithXmlStop();
                frameAnimWithJavaStop();
                break;
        }
    }

    private void initView() {
        xmlAnimImv = (ImageView) findViewById(R.id.xmlAnimImv);
        javaAnimImv = (ImageView) findViewById(R.id.javaAnimImv);

        startAnim = (Button) findViewById(R.id.startAnim);
        startAnim.setOnClickListener(this);

        stopAnim = (Button) findViewById(R.id.stopAnim);
        stopAnim.setOnClickListener(this);
    }

}
