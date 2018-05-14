package com.example.administrator.androiddemo.animation.tweenanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 四种补间动画 的基本使用
 * <p>
 * Created by gx on 2018/5/11 0011
 */

public class TweenAnimationLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button translateAnimWithXml;  // 平移动画XML实现
    private Button translateAnimWithJava; // 平移动画Java实现

    private Button scaleAnimWithXml;  // 缩放动画XML实现
    private Button scaleAnimWithJava;  // 缩放动画Java实现

    private Button rotateAnimWithXml;  // 旋转动画XML实现
    private Button rotateAnimWithJava;  // 旋转动画Java实现

    private Button alphaAnimWithXml;  // 透明度动画XML实现
    private Button alphaAnimWithJava;  // 透明度动画Java实现

    private Button combinationAnimWithXml;  // 组合动画XML实现
    private Button combinationAnimWithJava;  // 组合动画Java实现

    private Button startAnim;  // 开始动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweenanimation_learning);

        initView();
    }

    /**
     * 平移动画XML实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建 动画对象 并传入设置的动画效果xml文件；
     * 3.播放动画；
     */
    private void getTranslateAnimWithXml() {
        /*
         * 创建 需要设置动画的 视图View
         */
        translateAnimWithXml = (Button) findViewById(R.id.translateAnimWithXml);
        /*
         * 创建 动画对象 并传入设置的动画效果xml文件
         */
        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        /*
         * 播放动画
         */
        translateAnimWithXml.startAnimation(translateAnimation);
    }

    /**
     * 平移动画Java实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建平移动画的对象：平移动画对应的Animation子类为TranslateAnimation；
     * 3.设置属性等，固定属性的设置都是在其属性前加“set”，如setDuration()；
     * 4.播放动画；
     */
    private void getTranslateAnimWithJava() {
        /*
         * 创建 需要设置动画的 视图View
         */
        translateAnimWithJava = (Button) findViewById(R.id.translateAnimWithJava);
        /*
         * 创建平移动画的对象：平移动画对应的Animation子类为TranslateAnimation
         *
         * 参数分别是：
         *      fromXDelta ：视图在水平方向x 移动的起始值；
         *      toXDelta ：视图在水平方向x 移动的结束值；
         *      fromYDelta ：视图在竖直方向y 移动的起始值；
         *      toYDelta：视图在竖直方向y 移动的结束值；
         */
        Animation translateAnimation = new TranslateAnimation(0, 500, 0, 500);
        /*
         * 设置属性，固定属性的设置都是在其属性前加“set”，如setDuration（）；
         */
        translateAnimation.setDuration(3000);
        /*
         * 播放动画
         */
        translateAnimWithJava.startAnimation(translateAnimation);
    }

    /**
     * 缩放动画XML实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建 动画对象 并传入设置的动画效果xml文件；
     * 3.播放动画；
     */
    private void getScaleAnimWithXml() {
        /*
         * 创建 需要设置动画的 视图View
         */
        scaleAnimWithXml = (Button) findViewById(R.id.scaleAnimWithXml);
        /*
         * 创建 动画对象 并传入设置的动画效果xml文件
         */
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        /*
         * 播放动画
         */
        scaleAnimWithXml.startAnimation(scaleAnimation);
    }

    /**
     * 缩放动画Java实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建平移动画的对象：平移动画对应的Animation子类为TranslateAnimation；
     * 3.设置属性等，固定属性的设置都是在其属性前加“set”，如setDuration()；
     * 4.播放动画；
     */
    private void getScaleAnimWithJava() {
        /*
         * 创建 需要设置动画的 视图View
         */
        scaleAnimWithJava = (Button) findViewById(R.id.scaleAnimWithJava);
        /*
         * 创建缩放动画的对象 & 设置动画效果：缩放动画对应的Animation子类为RotateAnimation
         *
         *      参数分别是：
         *           fromX ：动画在水平方向X的结束缩放倍数
         *           toX ：动画在水平方向X的结束缩放倍数
         *           fromY ：动画开始前在竖直方向Y的起始缩放倍数
         *           toY：动画在竖直方向Y的结束缩放倍数
         *           pivotXType:缩放轴点的x坐标的模式
         *           pivotXValue:缩放轴点x坐标的相对值
         *           pivotYType:缩放轴点的y坐标的模式
         *           pivotYValue:缩放轴点y坐标的相对值
         *
         *           pivotXType = Animation.ABSOLUTE:缩放轴点的x坐标 =  View左上角的原点 在x方向 加上 pivotXValue数值的点(y方向同理)
         *           pivotXType = Animation.RELATIVE_TO_SELF:缩放轴点的x坐标 = View左上角的原点 在x方向 加上 自身宽度乘上pivotXValue数值的值(y方向同理)
         *           pivotXType = Animation.RELATIVE_TO_PARENT:缩放轴点的x坐标 = View左上角的原点 在x方向 加上 父控件宽度乘上pivotXValue数值的值 (y方向同理)
         *
         */
        Animation scaleAnimation = new ScaleAnimation(0, 2, 0, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        /*
         * 设置属性，固定属性的设置都是在其属性前加“set”，如setDuration（）；
         */
        scaleAnimation.setDuration(3000);
        /*
         * 播放动画
         */
        scaleAnimWithJava.startAnimation(scaleAnimation);
    }

    /**
     * 旋转动画XML实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建 动画对象 并传入设置的动画效果xml文件；
     * 3.播放动画；
     */
    private void getRotateAnimWithXml() {
        /*
         * 创建 需要设置动画的 视图View
         */
        rotateAnimWithXml = (Button) findViewById(R.id.rotateAnimWithXml);
        /*
         * 创建 动画对象 并传入设置的动画效果xml文件
         */
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        /*
         * 播放动画
         */
        rotateAnimWithXml.startAnimation(scaleAnimation);
    }

    /**
     * 旋转动画Java实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建平移动画的对象：平移动画对应的Animation子类为TranslateAnimation；
     * 3.设置属性等，固定属性的设置都是在其属性前加“set”，如setDuration()；
     * 4.播放动画；
     */
    private void getRotateAnimWithJava() {
        /*
         * 创建 需要设置动画的 视图View
         */
        rotateAnimWithJava = (Button) findViewById(R.id.rotateAnimWithJava);
        /*
         * 创建缩放动画的对象 & 设置动画效果：缩放动画对应的Animation子类为RotateAnimation
         *
         *      参数分别是：
         *          fromDegrees ：动画开始时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
         *          toDegrees ：动画结束时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
         *          pivotXType：旋转轴点的x坐标的模式
         *          pivotXValue：旋转轴点x坐标的相对值
         *          pivotYType：旋转轴点的y坐标的模式
         *          pivotYValue：旋转轴点y坐标的相对值
         *
         *          pivotXType = Animation.ABSOLUTE:旋转轴点的x坐标 =  View左上角的原点 在x方向 加上 pivotXValue数值的点(y方向同理)
         *          pivotXType = Animation.RELATIVE_TO_SELF:旋转轴点的x坐标 = View左上角的原点 在x方向 加上 自身宽度乘上pivotXValue数值的值(y方向同理)
         *          pivotXType = Animation.RELATIVE_TO_PARENT:旋转轴点的x坐标 = View左上角的原点 在x方向 加上 父控件宽度乘上pivotXValue数值的值 (y方向同理)
         */
        Animation rotateAnimation = new RotateAnimation(0, 270, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        /*
         * 设置属性，固定属性的设置都是在其属性前加“set”，如setDuration（）；
         */
        rotateAnimation.setDuration(3000);
        /*
         * 播放动画
         */
        rotateAnimWithJava.startAnimation(rotateAnimation);
    }

    /**
     * 透明度动画XML实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建 动画对象 并传入设置的动画效果xml文件；
     * 3.播放动画；
     */
    private void getAlphaAnimWithXml() {
        /*
         * 创建 需要设置动画的 视图View
         */
        alphaAnimWithXml = (Button) findViewById(R.id.alphaAnimWithXml);
        /*
         * 创建 动画对象 并传入设置的动画效果xml文件
         */
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        /*
         * 播放动画
         */
        alphaAnimWithXml.startAnimation(scaleAnimation);
    }

    /**
     * 透明度动画Java实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建平移动画的对象：平移动画对应的Animation子类为TranslateAnimation；
     * 3.设置属性等，固定属性的设置都是在其属性前加“set”，如setDuration()；
     * 4.播放动画；
     */
    private void getAlphaAnimWithJava() {
        /*
         * 创建 需要设置动画的 视图View
         */
        alphaAnimWithJava = (Button) findViewById(R.id.alphaAnimWithJava);
        /*
         * 创建缩放动画的对象 & 设置动画效果：缩放动画对应的Animation子类为RotateAnimation
         *
         *      参数分别是：
         *         fromAlpha:动画开始时视图的透明度(取值范围: -1 ~ 1)
         *         toAlpha:动画结束时视图的透明度(取值范围: -1 ~ 1)
         */
        Animation alphaAnimation = new AlphaAnimation(1, 0);
        /*
         * 设置属性，固定属性的设置都是在其属性前加“set”，如setDuration（）；
         */
        alphaAnimation.setDuration(3000);
        /*
         * 播放动画
         */
        alphaAnimWithJava.startAnimation(alphaAnimation);
    }

    /**
     * 组合动画XML实现
     * 1.创建 需要设置动画的 视图View；
     * 2.创建 动画对象 并传入设置的动画效果xml文件；
     * 3.播放动画；
     */
    private void getCombinationAnimWithXml() {
        /*
         * 创建 需要设置动画的 视图View
         */
        combinationAnimWithXml = (Button) findViewById(R.id.combinationAnimWithXml);
        /*
         * 创建 动画对象 并传入设置的动画效果xml文件
         */
        Animation combinationAnimation = AnimationUtils.loadAnimation(this, R.anim.combination_anim);
        /*
         * 播放动画
         */
        combinationAnimWithXml.startAnimation(combinationAnimation);
    }

    /**
     * 组合动画Java实现
     * 1.创建 需要设置动画的 视图View；
     * 2.组合动画设置；
     * 3.播放动画；
     */
    private void getCombinationAnimWithJava() {
        /*
         * 创建 需要设置动画的 视图View
         */
        combinationAnimWithJava = (Button) findViewById(R.id.combinationAnimWithJava);

        /*
         * 组合动画设置
         *      1.创建组合动画对象(设置为true)；
         *      2.设置组合动画的属性；
         *      3.逐个创建子动画(方式同单个动画创建方式,此处不作过多描述)；
         *      4.将创建的子动画添加到组合动画里；
         *
         *    特别说明以下情况：
         *        因为在下面的旋转动画设置了无限循环(RepeatCount = INFINITE)
         *        所以动画不会结束，而是无限循环
         *        所以组合动画的下面两行设置是无效的
         */
        AnimationSet setAnimation = new AnimationSet(true);
        setAnimation.setRepeatMode(Animation.RESTART);
        setAnimation.setRepeatCount(1);                 // 设置了循环一次,但无效

        // 子动画1:旋转动画
        Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);

        // 子动画2:平移动画
        Animation translate = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, -0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        translate.setDuration(10000);

        // 子动画3:透明度动画
        Animation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(3000);
        alpha.setStartOffset(7000);

        // 子动画4:缩放动画
        Animation scale1 = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale1.setDuration(1000);
        scale1.setStartOffset(4000);

        // 将创建的子动画添加到组合动画里
        setAnimation.addAnimation(alpha);
        setAnimation.addAnimation(rotate);
        setAnimation.addAnimation(translate);
        setAnimation.addAnimation(scale1);

        /*
         * 播放动画
         */
        combinationAnimWithJava.startAnimation(setAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startAnim:
                // 平移动画
//                getTranslateAnimWithXml();
//                getTranslateAnimWithJava();

                // 缩放动画
//                getScaleAnimWithXml();
//                getScaleAnimWithJava();

                // 旋转动画
//                getRotateAnimWithXml();
//                getRotateAnimWithJava();

                // 透明度动画
//                getAlphaAnimWithXml();
//                getAlphaAnimWithJava();

                // 组合动画
                getCombinationAnimWithXml();
                getCombinationAnimWithJava();
                break;
        }
    }

    private void initView() {
        startAnim = (Button) findViewById(R.id.startAnim);
        startAnim.setOnClickListener(this);
    }

}
