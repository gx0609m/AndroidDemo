package com.example.administrator.androiddemo.animation.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 属性动画 ——— ObjectAnimator
 * <p>
 * Created by gx on 2018/5/17 0017
 */

public class ObjectAnimatorLearningActivity extends BaseActivity implements View.OnClickListener {

    private TextView changeTv;

    private Button changeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator_learning);

        initView();
    }

    /**
     * ObjectAnimator 的 Java代码设置 方式介绍
     * 1.获取 要设置动画对象 的实例；
     * 2.获取 ObjectAnimator 对象实例，同时设置...；
     * 3.设置动画的属性；
     * 4.启动动画；
     */
    private void objectAnimatorByJava() {
        /*
         * 1.获取 要设置动画对象 的实例；
         */
        changeTv = (TextView) findViewById(R.id.changeTv);
        float curTranslationX = changeTv.getTranslationX();
        /*
         * 2.获取 ObjectAnimator 对象实例，同时设置了：
         *     a.动画的对象，如 changeTv；
         *     b.在动画对象的哪个属性上进行动画，如 “translationX”、“rotation”、“scaleX” 等；
         *     c.动画的效果，如 这里是先从当前位置沿X轴平移到 x=400，在平移回当前位置；
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(changeTv, "translationX", curTranslationX, 400, curTranslationX);
        /*
         * 3.设置动画的属性
         */
        objectAnimator.setDuration(2000);                               // 设置动画运行的时长
        objectAnimator.setStartDelay(500);                              // 设置动画延迟播放时间
        objectAnimator.setRepeatCount(0);                               // 设置动画重复播放次数 = 重放次数+1，动画播放次数 = infinite时,动画无限重复
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);           // 设置重复播放动画模式，ValueAnimator.RESTART(默认):正序重放，ValueAnimator.REVERSE:倒序回放
        /*
         * 4.启动动画
         */
        objectAnimator.start();
    }

    /**
     * ObjectAnimator 的 XML代码设置 方式介绍
     */
    private void objectAnimatorByXML() {
        changeTv = (TextView) findViewById(R.id.changeTv);
        Animator objectAnimator = AnimatorInflater.loadAnimator(this, R.animator.object_animator);          // 载入XML动画
        objectAnimator.setTarget(changeTv);                                                                 // 设置动画对象
        objectAnimator.start();                                                                             // 启动动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeBtn:
                objectAnimatorByJava();
                objectAnimatorByXML();
                break;
        }
    }

    private void initView() {
        changeBtn = (Button) findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(this);
    }
}
