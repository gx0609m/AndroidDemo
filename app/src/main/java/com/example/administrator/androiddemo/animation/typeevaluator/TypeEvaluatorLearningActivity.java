package com.example.administrator.androiddemo.animation.typeevaluator;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.animation.propertyanimation.PointEvaluator;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.model.object.Point;

/**
 * 估值器
 * <p>
 * Created by gx on 2018/5/15 0015
 */

public class TypeEvaluatorLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "TypeEvaluatorLearning";

    private TextView animTv;

    private Button beginAnimate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeevaluator_learning);

        initView();
    }

    /**
     * ValueAnimator.ofObject(**) 使用介绍（只有 Java代码设置 方式）
     * <p>
     * 由于.ofObject(**)没有系统内置的估值器，
     * 因此，需要 自定义估值器 ——— PointEvaluator；
     * <p>
     * 具体步骤：
     * 整体上 与 .ofInt() 等的 Java代码设置方式 差别不大，
     * 主要差别处在：
     * 1>.ofObject(**) 中的参数 与 ofInt(**) 等方法中的参数不同，需要提供一个 自定义估值器 ——— PointEvaluator，以及 动画开始 & 动画结束 时的Object对象,
     * 大部分情况下，这个Object对象需要自定义，如此处的Point对象，Point包含了两个属性 x，y；
     * 2>.在 AnimatorUpdateListener 的回调方法 onAnimationUpdate() 中，设置 要进行动画的对象 的逻辑可能有点细微差别，
     * 因为ofInt()、ofFloat()等其实都是对单一属性的改变，而ofObject()其实是对多个属性值的改变，只不过是将这多个值封装到一个对象Object中了，
     * 大部分情况下，这个Object对象需要自定义，如此处的Point对象，Point包含了两个属性 x，y；
     */
    private void valueAnimatorWithOfObject() {
        animTv = (TextView) findViewById(R.id.animTv);
        Point startPoint = new Point(animTv.getTranslationX(), animTv.getTranslationY());   // 开始动画时的坐标位置为当前位置
        Point endPoint = new Point(400, 600);                                               // 结束动画是的坐标位置为（400,600）
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.setDuration(4000);
        valueAnimator.setStartDelay(500);                       // 设置动画延迟播放时间
        valueAnimator.setRepeatCount(0);                        // 设置动画重复播放次数 = 重放次数+1，动画播放次数 = infinite 时,动画无限重复
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);     // 设置重复播放动画模式，ValueAnimator.RESTART(默认):正序播放；ValueAnimator.REVERSE:倒序回放
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point currentPoint = (Point) animation.getAnimatedValue();
                Log.d(TAG, "X:" + currentPoint.getX() + "Y:" + currentPoint.getY());
                /*
                 * 这段代码中，animTv.getLayoutParams().width/.height，
                 * 只是设置了控件的宽高，并不是坐标位置，
                 * 所以，并不会产生 坐标位置变化 的动画效果；
                 *
                 * 但是，由于是 宽、高 值的变化，而且是 从0到400、600 的变化，即 从无到有 ，
                 * 因此，会出现一个TextView上的 文字逐渐显示 的动画效果 ~
                 */
//                animTv.getLayoutParams().height = (int) currentPoint.getY();
//                animTv.getLayoutParams().width = (int) currentPoint.getX();
//                animTv.requestLayout();

                /*
                 * View 控件的坐标位置 会从 (0,0)——>(400,600) 逐渐变化
                 */
                animTv.setTranslationX(currentPoint.getX());
                animTv.setTranslationY(currentPoint.getY());
//                animTv.invalidate();
                animTv.requestLayout();
            }
        });
        valueAnimator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beginAnimate:
                valueAnimatorWithOfObject();
                break;
        }
    }

    private void initView() {
        beginAnimate = (Button) findViewById(R.id.beginAnimate);
        beginAnimate.setOnClickListener(this);
    }
}
