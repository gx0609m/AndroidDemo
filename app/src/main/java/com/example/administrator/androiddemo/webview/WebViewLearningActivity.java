package com.example.administrator.androiddemo.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * Created by gx on 2018/4/19 0019
 */

public class WebViewLearningActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_learning);

        initView();
        loadUrl();
    }

    private void loadUrl() {
        webView.loadUrl("http://wanandroid.com/article/list/0?cid=98");
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }
}
