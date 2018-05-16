package com.example.administrator.androiddemo.animation.propertyanimation;

import android.animation.TypeEvaluator;

import com.example.administrator.androiddemo.model.object.Point;

/**
 * 自定义 估值器
 * <p>
 * Created by gx on 2018/5/15 0015
 */

public class PointEvaluator implements TypeEvaluator {

    /*
     * 实现 TypeEvaluator 接口，并复写 evaluate()
     *      fraction：表示动画完成度（根据它来计算当前动画的值）
     *      startValue、endValue：动画的初始值和结束值；
     */
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // 将 动画初始值startValue 和 动画结束值endValue 强制类型转换成 Point 对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        // 根据fraction来计算当前动画的 x & y 的值
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        // 将计算后的坐标封装到一个新的Point对象中并返回
        Point point = new Point(x, y);
        return point;
    }
}
