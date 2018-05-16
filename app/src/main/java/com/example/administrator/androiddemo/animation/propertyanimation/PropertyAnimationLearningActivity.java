package com.example.administrator.androiddemo.animation.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 属性动画
 * <p>
 * Created by gx on 2018/5/11 0011
 */

public class PropertyAnimationLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PropertyAnimation";

    private TextView valueAnimatorOfInt;

    private Button startAnimate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertyanimation_learning);

        initView();
    }

    /**
     * ValueAnimator.ofInt(**) Java代码设置 使用介绍
     * 1.设置动画属性的 初始值 & 结束值；
     * 2.设置动画的各种播放属性；
     * 3.将改变的值手动赋值给对象的属性值 ——— 通过动画的更新监听器 AnimatorUpdateListener；
     * 4.将改变后的值赋给对象的属性值 ——— View.setProperty(currentValue)；
     * 5.刷新视图，即重新绘制，从而实现动画效果 ——— View.requestLayout()；
     * 6.启动动画；
     */
    private void valueAnimatorWithOfIntByJava() {
        /*
         * 1.设置动画属性的 初始值 & 结束值：
         *     ofInt()作用有两个：
         *     1>.创建动画实例；
         *     2>.将传入的多个Int参数进行平滑过渡：
         *          此处传入0和10,表示将值从0平滑过渡到10；
         *          如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推；
         *          ValueAnimator.ofInt()内置了整型估值器,直接采用默认的，不需要设置，即默认设置了如何从 初始值 过渡到 结束值；
         */
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 10);
        /*
         * 2.设置动画的各种播放属性：
         */
        valueAnimator.setDuration(1000);                        // 设置动画运行的时长
        valueAnimator.setStartDelay(500);                       // 设置动画延迟播放时间
        valueAnimator.setRepeatCount(0);                        // 设置动画重复播放次数 = 重放次数+1，动画播放次数 = infinite 时,动画无限重复
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);     // 设置重复播放动画模式，ValueAnimator.RESTART(默认):正序播放；ValueAnimator.REVERSE:倒序回放
        /*
         * 3.将改变的值手动赋值给对象的属性值 ——— 通过动画的更新监听器 AnimatorUpdateListener；
         */
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();  // 获得改变后的值
                Log.d(TAG, currentValue + "");                              // 输出改变后的值
                /*
                 * 4.将改变后的值赋给对象的属性值 ——— View.setProperty(currentValue)
                 */
                valueAnimatorOfInt.setTextSize(currentValue);
                /*
                 * 5.刷新视图，即重新绘制，从而实现动画效果 ——— View.requestLayout()
                 */
                valueAnimatorOfInt.requestLayout();
            }
        });
        /*
         * 6.启动动画
         */
        valueAnimator.start();
    }

    /**
     * ValueAnimator.ofInt(**) XML代码设置 使用介绍
     * 1.载入XML动画；
     * 2.设置动画对象；
     * 3.启动动画；
     */
    private void valueAnimatorWithOfIntByXml() {
        /*
         * 1.载入XML动画
         */
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.value_animation_ofint);
        /*
         * 2.设置动画对象
         */
        animator.setTarget(valueAnimatorOfInt);
        /*
         * 3.启动动画
         */
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startAnimate:
//                valueAnimatorWithOfIntByJava();
                valueAnimatorWithOfIntByXml();
                break;
        }
    }

    private void initView() {
        valueAnimatorOfInt = (TextView) findViewById(R.id.valueAnimatorOfInt);

        startAnimate = (Button) findViewById(R.id.startAnimate);
        startAnimate.setOnClickListener(this);
    }
}
