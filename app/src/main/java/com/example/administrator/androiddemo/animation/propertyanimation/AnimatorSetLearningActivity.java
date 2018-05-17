package com.example.administrator.androiddemo.animation.propertyanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 属性动画 ——— AnimatorSet
 * <p>
 * Created by gx on 2018/5/17 0017
 */

public class AnimatorSetLearningActivity extends BaseActivity implements View.OnClickListener {

    private TextView animatorSetTv;
    private TextView viewPropertyAnimator;

    private Button moveBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_set_learning);

        initView();
    }

    /**
     * 属性动画的 本质是对值 操作，无论是 ValueAnimator 还是 ObjectAnimator
     * 而 Java是面向对象的，所以 Google 团队增加了 面向对象操作的属性动画使用类 ——— ViewPropertyAnimator
     * <p>
     * 特别注意：
     * a.通过 ViewPropertyAnimator 的动画会 自动启动，无需调用start()方法，因为新的接口中使用了隐式启动动画的功能，只要我们将动画定义完成后，动画就会自动启动；
     * b.该机制对于组合动画也同样有效，只要不断地连缀新的方法，那么动画就不会立刻执行，等到所有在ViewPropertyAnimator上设置的方法都执行完毕后，动画就会自动启动；
     * c.如果不想使用这一默认机制，也可以显式地调用start()方法来启动动画；
     */
    private void useViewPropertyAnimator() {
        viewPropertyAnimator = (TextView) findViewById(R.id.viewPropertyAnimator);
//        viewPropertyAnimator.animate().alpha(0f);                                                                // 单个动画设置:将按钮变成透明状态
//        viewPropertyAnimator.animate().alpha(0f).setDuration(5000).setInterpolator(new BounceInterpolator());    // 单个动画效果设置 & 参数设置
        viewPropertyAnimator.animate().alpha(0f).x(500).y(500).setDuration(5000);                                  // 组合动画:将按钮变成透明状态，再移动到(500,500)处
    }

    /**
     * （属性动画中）组合动画的 Java代码设置方式
     * 1.创建 要设置动画对象 的实例；
     * 2.设置需要组合的动画效果；
     * 3.创建组合动画的对象；
     * 4.根据需求组合动画；
     * 5.设置 组合动画 的相关属性；
     * 6.启动动画；
     */
    private void animatorSetByJava() {
        /*
         * 1.创建 要设置动画对象 的实例；
         */
        animatorSetTv = (TextView) findViewById(R.id.animatorSetTv);
        /*
         * 2.设置需要组合的动画效果；
         */
        float curTranslationX = animatorSetTv.getTranslationX();
        ObjectAnimator translation = ObjectAnimator.ofFloat(animatorSetTv, "translationX", curTranslationX, 300, curTranslationX);  // 平移动画
        ObjectAnimator rotate = ObjectAnimator.ofFloat(animatorSetTv, "rotation", 0f, 360f);                                        // 旋转动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(animatorSetTv, "alpha", 1f, 0f, 1f);                                          // 透明度动画
        /*
         * 3.创建组合动画的对象；
         */
        AnimatorSet animSet = new AnimatorSet();
        /*
         * 4.根据需求组合动画；
         */
        animSet.play(translation).with(rotate).before(alpha);
        /*
         * 5.设置 组合动画 的相关属性；
         */
        animSet.setDuration(5000);
        /*
         * 6.启动动画；
         */
        animSet.start();
    }

    /**
     * （属性动画中）组合动画的 XML代码设置方式
     */
    private void animatorSetByXML() {
        animatorSetTv = (TextView) findViewById(R.id.animatorSetTv);
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_set);      // 创建组合动画对象 & 加载XML动画
        animator.setTarget(animatorSetTv);                                                                      // 设置动画作用对象
        animator.start();                                                                                       // 启动动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moveBtn:
//                animatorSetByJava();
                animatorSetByXML();

                useViewPropertyAnimator();
                break;
        }
    }

    private void initView() {
        moveBtn = (Button) findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(this);
    }
}
