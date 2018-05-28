package com.example.administrator.androiddemo.refreshload;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private int mlastMoveY;       // MOTION_EVENT事件中（因为肯定会从ACTION_DOWN事件开始），不断的记录 最新的MOTION_EVENT事件  y 坐标值
    private int mLastYIntercept;  // MOTION_EVENT事件后，将 mlastMoveY 值赋给 mLastYIntercept，为的是在下一次 MOTION_EVENT事件中（本类中是ACTION_MOVE） ，将二者的值进行比较，来判断时 向下/向上 滑动

    private int lastChildIndex;
    // 所有content的高度，即所有child加起来的高度，是一个不断累加的值，添加一个child就添加一些，但是不包括header和footer
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

    /*
     * 处理滑动事件，我们需要注意两个函数：
     *
     * onInterceptTouchEvent
     *     ——— 在 onTouchEvent 前面执行，在这里需要判断是否应该拦截这个事件，然后交由 我的onTouchEvent 处理；
     *         一旦 onInterceptTouchEvent 返回 true 表示拦截，后续事件都会交给 onTouchEvent 处理，onInterceptTouchEvent 不会再去执行下一次按下事件；
     *
     * onTouchEvent
     *     ——— 处理touch事件，如按下，滑动，松开等；
     */

    /**
     * 在这个函数中判断是否应该拦截滑动事件；
     * <p>
     * 例如child是一个ListView，
     * ——— 那么，当它没有滑到头或者没有滑到尾的时候，我们都不应该拦截；
     * ——— ACTION_DOWN 和 ACTION_UP和不需要拦截；
     * ——— 当事件为 ACTION_MOVE 时，
     * 如果是向下滑动，判断第一个child是否滑倒最上面，如果是，则更新状态为 TRY_REFRESH；
     * 如果是向上滑动，则判断最后一个child是否滑动最底部，如果是，则更新状态为TRY_LOADMORE；
     * 然后返回 intercept = true；
     * 这样，接下来的滑动事件就会传给本类的 onTouchEvent 处理；
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int y = (int) ev.getY();    // 如果是 ACTION_MOVE 事件的话，那么 y 值是不断改变的，因为不断的getY()

        // 正在刷新 或 加载更多，避免重复
        if (status == Status.REFRESHING || status == Status.LOADING) {
            // 返回true，表示在此消费了，不在往下分发
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mlastMoveY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 向下滑动
                if (y > mLastYIntercept) {
                    // 判断 刷新 是否拦截
                    View child = getChildAt(lastChildIndex); // 如果 内部放置的都是 ListView、RecyclerView之类的，那么 getChildAt(lastChildIndex) 和 getChildAt(0) 效果应该是一样的
                    intercept = getRefreshIntercept(child);
                    if (intercept) {
                        updateStatus(Status.TRY_REFRESH);
                    }
                }
                // 向上滑动
                else if (y < mLastYIntercept) {
                    // 判断 加载 是否拦截
                    View child = getChildAt(lastChildIndex); // 如果 内部放置的都是 ListView、RecyclerView之类的，那么 getChildAt(lastChildIndex) 和 getChildAt(0) 效果应该是一样的
                    intercept = getLoadMoreIntercept(child);
                    if (intercept) {
                        updateStatus(Status.TRY_LOADMORE);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        Log.e(TAG, "onInterceptTouchEvent");

        mLastYIntercept = mlastMoveY;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    /*汇总判断 刷新&加载 是否拦截*/
    private boolean getRefreshIntercept(View child) {
        boolean ifIntercept = false;
        if (child instanceof RecyclerView) {
            ifIntercept = recyclerViewRefreshIntercept(child);
        } else if (child instanceof AdapterView) {
            ifIntercept = adapterViewRefreshIntercept(child);
        } else if (child instanceof ScrollView) {
            ifIntercept = scrollViewRefreshIntercept(child);
        }
        return ifIntercept;
    }

    private boolean getLoadMoreIntercept(View child) {
        boolean ifIntercept = false;
        if (child instanceof RecyclerView) {
            ifIntercept = recyclerViewLoadMoreIntercept(child);
        } else if (child instanceof AdapterView) {
            ifIntercept = adapterViewLoadMoreIntercept(child);
        } else if (child instanceof ScrollView) {
            ifIntercept = scrollViewLoadMoreIntercept(child);
        }
        return ifIntercept;
    }
    /*汇总判断 刷新&加载 是否拦截*/


    /*具体判断 各种View 是否应该拦截*/
    // 判断 RecyclerView刷新 是否拦截
    private boolean recyclerViewRefreshIntercept(View child) {
        boolean ifIntercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollOffset() < 0) {  // 拦截
            ifIntercept = true;
        }
        return ifIntercept;
    }

    // 判断RecyclerView加载更多是否拦截
    private boolean recyclerViewLoadMoreIntercept(View child) {
        boolean ifIntercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()) {
            ifIntercept = true;
        }
        return ifIntercept;
    }

    // 判断AdapterView下拉刷新是否拦截
    private boolean adapterViewRefreshIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {
            intercept = false;
        }
        return intercept;
    }

    // 判断AdapterView加载更多是否拦截
    private boolean adapterViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1 &&
                (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() >= getMeasuredHeight())) {
            intercept = true;
        }
        return intercept;
    }

    // 判断ScrollView刷新是否拦截
    private boolean scrollViewRefreshIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    // 判断ScrollView加载更多是否拦截
    private boolean scrollViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);

        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }
    /*具体判断 各种View 是否应该拦截*/
}
