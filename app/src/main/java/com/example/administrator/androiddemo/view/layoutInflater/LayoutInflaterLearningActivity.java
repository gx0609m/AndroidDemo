package com.example.administrator.androiddemo.view.layoutInflater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * Created by gx on 2018/5/11 0011
 */

public class LayoutInflaterLearningActivity extends BaseActivity {

    private static final String TAG = "LayoutInflaterLearning";

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutinflater_learning);

        initView();
        getMainLayoutParent();
    }

    /*
     * InflaterLayout 动态添加 View（a、b、c、d）
     *
     * 注意：
     *      如果我们想将 inflater_layout 中的 Button 设置 的大点呢？
     *          例如，高度改成80dp，会发现高度并不会变化，但是设置背景颜色什么的却会变化；
     *
     *          其实，
     *               这里不管你将Button的layout_width和layout_height的值修改成多少，
     *               都不会有任何效果的，因为这两个值现在已经完全失去了作用；
     *          平时，
     *               我们经常使用layout_width和layout_height来设置View的大小，并且一直都能正常工作，
     *              就好像这两个属性确实是用于设置View的大小的；
     *          实际上则不然，
     *               它们其实是用于设置View在布局中的大小的；
     *          也就是说，
     *               首先View必须存在于一个布局中，在布局中，
     *              如果将layout_width设置成match_parent表示让View的宽度填充满布局，
     *              如果设置成wrap_content表示让View的宽度刚好可以包含其内容，
     *              如果设置成具体的数值则View的宽度会变成相应的数值；
     *
     *          这也是为什么这两个属性叫作 layout_width和layout_height，而不是 width和height；
     *
     *
     *
     *      如果我们想让这两个属性起作用呢？
     *          最简单的方式就是在Button的外面再嵌套一层布局，如下：
     *          <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
     *               android:layout_width="match_parent"
     *               android:layout_height="match_parent">
     *
     *               <Button
     *                   android:layout_width="wrap_content"
     *                   android:layout_height="80dp"
     *                   android:background="#654321"
     *                   android:text="Button Layout" />
     *           </RelativeLayout>
     *
     *       可以看到，
     *          这里我们加了一个RelativeLayout，此时的Button存在与RelativeLayout之中，layout_width和layout_height属性也就有作用了；
     *       当然，
     *          处于最外层的RelativeLayout，它的layout_width和layout_height则会失去作用；
     *
     *
     *
     *       那为什么 Activity 的最外层布局是可以指定大小的呢（layout_width和layout_height 会起作用）？
     *          这主要是因为，
     *              在setContentView()方法中，Android会自动在布局文件的最外层再嵌套一个FrameLayout，所以layout_width和layout_height属性才会有效果；
     *
     *          我们可以通过 getMainLayoutParent() 验证下：
     *              通过打印日志发现，LinearLayout的父布局确实是一个FrameLayout，而这个FrameLayout就是由系统自动帮我们添加上的；
     *
     *
     *
     *       说到这里，虽然setContentView()方法大家都会用，但实际上Android界面显示的原理要比我们所看到的东西复杂得多，
     *
     *       任何一个Activity中显示的界面其实主要都由 两部分组成 ，
     *              a.标题栏：
     *                  标题栏就是在很多界面顶部显示的那部分内容，比如刚刚我们的那个例子当中就有标题栏，可以在代码中控制让它是否显示；
     *              b.内容布局：
     *                  内容布局就是一个FrameLayout，这个布局的id叫作content，
     *                  我们调用setContentView()方法时所传入的布局其实就是放到这个FrameLayout中的，
     *                  这也是为什么这个方法名叫作setContentView()，而不是叫setView()；
     *
     *
     */
    private void initView() {
        /*
         * a.获取 主布局 实例
         */
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        /*
         * b.获取 layoutInflater 实例
         */
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        /*
         * c.获取要添加到主布局的实例 buttonLayout
         */
        View buttonLayout = layoutInflater.inflate(R.layout.inflater_layout, null);
        /*
         * d.调用LinearLayout的add()将 buttonLayout 填充进去，即可在主布局界面中看到
         */
        linearLayout.addView(buttonLayout);
    }

    /**
     * 获取当前主布局的父布局（Android默认嵌套的父布局）
     */
    private void getMainLayoutParent() {
        ViewParent viewParent = linearLayout.getParent();
        Log.e(TAG, "the parent of mainLayout is " + viewParent);
        Toast.makeText(this, "the parent of mainLayout is " + viewParent, Toast.LENGTH_SHORT).show();
    }
}
