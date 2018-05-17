package com.example.administrator.androiddemo.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.animation.frameanimation.FrameAnimationLearningActivity;
import com.example.administrator.androiddemo.animation.interpolator.InterpolatorLearningActivity;
import com.example.administrator.androiddemo.animation.propertyanimation.AnimatorSetLearningActivity;
import com.example.administrator.androiddemo.animation.propertyanimation.ObjectAnimatorLearningActivity;
import com.example.administrator.androiddemo.animation.propertyanimation.ValueAnimatorLearningActivity;
import com.example.administrator.androiddemo.animation.tweenanimation.AnimationListenerLearningActivity;
import com.example.administrator.androiddemo.animation.tweenanimation.TweenAnimationLearningActivity;
import com.example.administrator.androiddemo.animation.typeevaluator.TypeEvaluatorLearningActivity;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 动画相关
 * <p>
 * Created by gx on 2018/5/11 0011
 */

public class AnimationLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button frameAnimation;
    private Button tweenAnimation;
    private Button valueAnimator;
    private Button objectAnimator;
    private Button animatorSet;

    private Button interpolator;
    private Button typeEvaluator;

    private Button animationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_learning);

        initView();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.frameAnimation:
                intent = new Intent(AnimationLearningActivity.this, FrameAnimationLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.tweenAnimation:
                intent = new Intent(AnimationLearningActivity.this, TweenAnimationLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.valueAnimator:
                intent = new Intent(AnimationLearningActivity.this, ValueAnimatorLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.objectAnimator:
                intent = new Intent(AnimationLearningActivity.this, ObjectAnimatorLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.animatorSet:
                intent = new Intent(AnimationLearningActivity.this, AnimatorSetLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.animationListener:
                intent = new Intent(AnimationLearningActivity.this, AnimationListenerLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.interpolator:
                intent = new Intent(AnimationLearningActivity.this, InterpolatorLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.typeEvaluator:
                intent = new Intent(AnimationLearningActivity.this, TypeEvaluatorLearningActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        frameAnimation = (Button) findViewById(R.id.frameAnimation);
        frameAnimation.setOnClickListener(this);

        tweenAnimation = (Button) findViewById(R.id.tweenAnimation);
        tweenAnimation.setOnClickListener(this);

        valueAnimator = (Button) findViewById(R.id.valueAnimator);
        valueAnimator.setOnClickListener(this);

        objectAnimator = (Button) findViewById(R.id.objectAnimator);
        objectAnimator.setOnClickListener(this);

        animationListener = (Button) findViewById(R.id.animationListener);
        animationListener.setOnClickListener(this);

        interpolator = (Button) findViewById(R.id.interpolator);
        interpolator.setOnClickListener(this);

        typeEvaluator = (Button) findViewById(R.id.typeEvaluator);
        typeEvaluator.setOnClickListener(this);

        animatorSet = (Button) findViewById(R.id.animatorSet);
        animatorSet.setOnClickListener(this);
    }
}
