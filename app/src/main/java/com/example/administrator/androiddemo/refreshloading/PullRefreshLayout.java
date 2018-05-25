package com.example.administrator.androiddemo.refreshloading;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/5/25 0025
 */

public class PullRefreshLayout extends ViewGroup {

    private static final String TAG = "PullRefreshLayout";

    private View mHeader;
    private TextView mHeaderText;
    private ProgressBar mHeaderProgressBar;
    private ImageView mHeaderArrow;

    private View mFooter;
    private TextView mFooterText;
    private ProgressBar mFooterProgressBar;

    private int mlastMoveY;  // 起始点的y坐标

    private int lastChildIndex;
    private int mLayoutContentHeight = 0;

    boolean intercept = false;

    private Status status = Status.NORMAL;

    private enum Status {
        NORMAL,
        TRY_REFRESH,
        REFRESHING,
        TRY_LOADMORE,
        LOADING
    }

    private void updateStatus(Status status) {
        this.status = status;
    }

    public PullRefreshLayout(Context context) {
        super(context);
        Log.e(TAG, "PullRefreshLayout(*)");
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "PullRefreshLayout(*,*)");
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "PullRefreshLayout(*,*,*)");
    }

    /**
     * PullRefreshLayout的XML文件中 所有的子View 被初始化后 调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 因为 index 计数是从0开始的
        lastChildIndex = getChildCount() - 1;

        addHeader();
        addFooter();

        Log.e(TAG, "onFinishInflate");
    }

    private void addHeader() {
        mHeader = LayoutInflater.from(getContext()).inflate(R.layout.pull_header, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mHeader, params);
        mHeaderText = (TextView) findViewById(R.id.header_text);
        mHeaderProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);
        mHeaderArrow = (ImageView) findViewById(R.id.header_arrow);
    }

    private void addFooter() {
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.pull_footer, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mFooter, params);
        mFooterText = (TextView) findViewById(R.id.footer_text);
        mFooterProgressBar = (ProgressBar) findViewById(R.id.footer_progressbar);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

        Log.e(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeader) {
                child.layout(0, -child.getMeasuredHeight(), getMeasuredWidth(), 0);
            } else if (child == mFooter) {
                child.layout(0, mLayoutContentHeight, getMeasuredWidth(), mLayoutContentHeight + getMeasuredHeight());
            } else {
                child.layout(0, mLayoutContentHeight, getMeasuredWidth(), mLayoutContentHeight + getMeasuredHeight());
                if (child instanceof ScrollView) {
                    mLayoutContentHeight = getMeasuredHeight();
                    // 满足条件时，忽略本次（for）循环剩下的语句，
                    // 即如果 child 为 ScrollView，则 mLayoutContentHeight += child.getMeasuredHeight() 不执行
                    continue;
                }
                mLayoutContentHeight += child.getMeasuredHeight();
            }
        }

        Log.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int y = (int) ev.getY();

        // 正在刷新 或 加载更多，避免重复
        if (status == Status.REFRESHING || status == Status.LOADING) {
            // 返回true，表示在此消费了，不在往下分发
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mlastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        Log.e(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

}
