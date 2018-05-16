package com.example.administrator.androiddemo.animation.interpolator;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 插值器
 * <p>
 * Created by gx on 2018/5/15 0015
 */

public class InterpolatorLearningActivity extends BaseActivity implements View.OnClickListener {

    private TextView doNotChange;
    private Button beginAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator_learning);

        initView();
    }

    /**
     * Java代码 设置 系统自带插值器 的动画
     * 1.创建 需要设置动画的 视图View；
     * 2.创建 透明度动画的对象 & 设置动画效果；
     * 3.创建对应的插值器类对象；
     * 4.给动画设置插值器；
     * 5.播放动画；
     */
    private void animateWithSetSystemInterpolator() {
        doNotChange = (TextView) findViewById(R.id.doNotChange);   // 创建 需要设置动画的 视图View
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);  // 创建 透明度动画的对象 & 设置动画效果 （补间动画）
        alphaAnimation.setDuration(2000);
        Interpolator interpolator = new OvershootInterpolator();   // 创建对应的插值器类对象
        alphaAnimation.setInterpolator(interpolator);              // 给动画设置插值器
        doNotChange.startAnimation(alphaAnimation);                // 播放动画
    }

    /**
     * Java代码 设置 自定义插值器 的动画
     * <p>
     * 步骤：
     * 与设置 系统自带插值器无二致，只需将设置的插值器兑现修改为自定的插值器即可（这里使用的是 属性动画）；
     */
    private void animateWithSetCustomInterpolator() {
        doNotChange = (TextView) findViewById(R.id.doNotChange);                 // 创建 需要设置动画的 视图View
        float curTranslationX = doNotChange.getTranslationX();                   // 获得当前按钮的位置
        /*
         * 创建动画对象 & 设置动画，表示的是:
         *     动画作用对象是mButton；
         *     动画作用的对象的属性是X轴平移；
         *     动画效果是:从当前位置平移到 x=400 再平移到初始位置；
         */
        ObjectAnimator animator = ObjectAnimator.ofFloat(doNotChange, "translationX", curTranslationX, 400, curTranslationX);
        animator.setDuration(5000);
        animator.setInterpolator(new DecelerateAccelerateInterpolator());        // 设置 自定义插值器
        animator.start();                                                        // 启动动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beginAnim:
//                animateWithSetSystemInterpolator();
                animateWithSetCustomInterpolator();
                break;
        }
    }

    private void initView() {
        beginAnim = (Button) findViewById(R.id.beginAnim);
        beginAnim.setOnClickListener(this);
    }
}
