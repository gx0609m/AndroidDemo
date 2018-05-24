package com.example.administrator.androiddemo.view.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androiddemo.R;

/**
 * 继承View的 自定义View类
 * <p>
 * Created by gx on 2018/5/24 0024
 */

public class CircleView extends View {

    Paint mPaint1; // 设置 自定义CircleView 画圆的画笔颜色
    Paint mPaint2; // 设置 自定义CircleView 写字的画笔颜色
    int color;
    String text;

    /*
     * 自定义View有四个构造函数
     * 如果View是在Java代码里面new的，则调用第一个构造函数
     */
    public CircleView(Context context) {
        super(context);
        init();
    }

    /*
     * 如果View是在.xml里声明的，则调用第二个构造函数
     * 自定义属性是从AttributeSet参数传进来的
     */
    public CircleView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init();
        this(context, attrs, 0);
    }

    /*
     * 不会自动调用
     * 一般是在第二个构造函数里主动调用，如View有style属性时
     */
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载自定义属性集合CircleView
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        // 解析集合中的属性circle_color属性
        // 该属性的id为:R.styleable.CircleView_circle_color
        // 将解析的属性传入到画圆的画笔颜色变量当中（本质上是自定义画圆画笔的颜色）
        // 第二个参数是默认设置颜色（即无指定circle_color情况下使用）
        color = a.getColor(R.styleable.CircleView_circle_color, Color.RED);
        text = a.getString(R.styleable.CircleView_circle_text);

        // 解析后释放资源
        a.recycle();

        init();
    }

    /*
     * API21之后才使用
     * 不会自动调用
     * 一般是在第二个构造函数里主动调用
     * 如View有style属性时
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /*
     * 画笔初始化
     */
    private void init() {
        // 设置 自定义CircleView 画圆的画笔颜色
        mPaint1 = new Paint();                  // 创建画笔
        mPaint1.setColor(color);                // 设置画笔颜色为从XML中解析的颜色
        mPaint1.setStrokeWidth(5f);             // 设置画笔宽度为10px
        mPaint1.setStyle(Paint.Style.FILL);     // 设置画笔模式为填充

        // 设置 自定义CircleView 写字的画笔颜色
        mPaint2 = new Paint();
        mPaint2.setTextSize(48);                // 设置画笔默认写出的文字大小（这里注意，Paint.setTextSize(**)与TextView.setTextSize(**)中参数代表的意思是不同的 px/sp）
        mPaint2.setColor(Color.BLUE);           // 设置画笔颜色为蓝色
    }

    /*
     * 复写onDraw()进行绘制
     * 如需支持 padding 属性，则计算相关值时要把 padding 也计算入
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取传入的padding值
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        // 获取控件的高度和宽度（XML中 layout_width、layout_height属性 设置的；同时，如果需要支持padding属性，则 须考虑 四个方向上的padding值）
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        // 设置圆的半径 = 宽,高最小值的2分之1
        int r = Math.min(width, height) / 2;
        // 画出圆（蓝色），圆心 = 控件的中央，半径 = 宽/高最小值的2分之1
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, r, mPaint1);

        // 画出 通过 circle_text 属性 设置的文字
        canvas.drawText(text, paddingLeft + width / 2, paddingTop + height / 2, mPaint2);
    }

    /*
     * 重写 onMeasure()
     * 给一个默认的 宽/高 值，让 自定义View的 宽/高 属性设置成 wrap_content 起作用，
     * 否则 宽/高 属性设置成 wrap_content 与 match_parent 效果一致
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 400;
        int mHeight = 400;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }
}
