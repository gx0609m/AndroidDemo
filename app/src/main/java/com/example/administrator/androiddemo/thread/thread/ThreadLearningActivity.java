package com.example.administrator.androiddemo.thread.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * Created by gx on 2018/3/28 0028
 */

public class ThreadLearningActivity extends BaseActivity {

    /*
     * 创建新Thread的两种方式：
     *      1.继承Thread类；
     *      2.实现Runnable接口；
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_learning);

    }

    /**
     * 继承Thread方式来新建线程
     */
    private void withExtendsThread(){

    }
}
