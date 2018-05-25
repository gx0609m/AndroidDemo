package com.example.administrator.androiddemo.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可设置 是否支持滑动 的 自定义ViewPager
 * <p>
 * Created by gx on 2018/5/25 0025
 */

public class XViewPager extends ViewPager {

    private boolean scrollEnabled = true;

    /**
     * true：支持滑动；
     * false：不支持滑动；
     */
    public void setScrollEnabled(boolean scrollEnabled) {
        this.scrollEnabled = scrollEnabled;
    }

    public XViewPager(Context context) {
        super(context);
    }

    public XViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollEnabled && super.onTouchEvent(ev);
    }
}
