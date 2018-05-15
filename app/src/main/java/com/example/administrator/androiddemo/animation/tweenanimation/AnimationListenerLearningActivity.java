package com.example.administrator.androiddemo.animation.tweenanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 给动画添加监听
 * <p>
 * Created by gx on 2018/5/14 0014
 */

public class AnimationListenerLearningActivity extends BaseActivity {

    private TextView tv_animate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_listener_learning);

        initView();
        animateTv();
    }

    /**
     * 给 TextView 添加平移动画 并 监听
     */
    private void animateTv() {
        /*
         * 给 textView 添加平移动画 translateAnimation
         */
        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        tv_animate.startAnimation(translateAnimation);
        /*
         * 通过 AnimationListener ，给 translateAnimation 添加监听
         *
         * 注意：
         *      这里是通过 AnimationListener接口，但它会强制实现它的三个方法；
         *      我们可以通过 AnimatorListenerAdapter 来解决 实现接口繁琐 的问题 ——— 未验证；
         *      （Animator.addListener(new AnimatorListenerAdapter() {...}）
         */
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(AnimationListenerLearningActivity.this, "Animation started~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(AnimationListenerLearningActivity.this, "Animation ended~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        tv_animate = (TextView) findViewById(R.id.tv_animate);
    }
}
