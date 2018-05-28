package com.example.administrator.androiddemo.refreshload;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 上拉刷新 & 下拉加载
 * <p>
 * Created by gx on 2018/5/25 0025
 */

public class RefreshAndLoadLearningActivity extends BaseActivity {

    private PullRefreshLayout pullRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_loading_learning);

        initView();
    }

    private void initView() {
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.pullRefreshLayout);
    }
}
