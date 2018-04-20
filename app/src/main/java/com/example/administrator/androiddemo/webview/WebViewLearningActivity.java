package com.example.administrator.androiddemo.webview;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * Created by gx on 2018/4/19 0019
 */

public class WebViewLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "WebViewLearningActivity";

    private WebView webView;
    private Button callJS;
    private Button callAndroid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_learning);

        initView();
        loadUrl();
    }

    private void loadUrl() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webView.setWebViewClient(new WebViewClient() {
            /**
             * 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器，而是在当前WebView中显示
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            /**
             * 开始载入页面调用的，可以设定一个loading页面，告诉用户程序在等待网络响应
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 在页面加载结束时调用。我们可以关闭loading 条，切换程序动作
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            /**
             * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
             */
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            /**
             * onReceivedError方法捕获的是 文件找不到，网络连不上，服务器找不到等问题，并不能捕获 http errors
             *
             * google官网提供的文档，onReceivedError可以捕获http Error。但是使用这个方法的时候，并没有捕获到404错误，
             * 应该是google以前的文档存在bug，这里是这个bug的google issue链接：
             *                              https://code.google.com/p/android/issues/detail?id=968
             *
             * @SuppressWarnings("deprecation") 不检测方法是否过期
             */
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            /**
             * 在api 23里面 已经上面方法已经deprecation
             *
             * Redirect to deprecated method, so you can use it in all SDK versions
             *
             * TargetApi是为了防止代码检查工具android Lint 检测出现提示性错误而设计的，
             * 例如高版本的api所需sdk版本 > minSdkVersion 时，就会出现提示性错误
             *
             * 需要注意的是：TargetApi 不等于是做了兼容性判断，只是避免出现提示性错误；
             *              即使使用了TargetApi，兼容性判断也不可少；
             */
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
            }

            /**
             * 捕获 http errors
             *
             * 加载页面的服务器出现错误时（如404）调用 -- 由于api版本的原因，此方法只能在Android M 以上调用
             *
             * App里面使用webView控件的时候遇到了诸如404这类的错误的时候，若也显示浏览器里面的那种错误提示页面
             * 就显得很丑陋了，那么这个时候我们的app就需要加载一个本地的错误提示页面，即webView如何加载一个本地的页面
             */
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.e(TAG, "errorCode:" + errorResponse.getStatusCode());
                switch (errorResponse.getStatusCode()) {
                    case 404:
                        view.loadUrl("file:///android_asset/test.html");
                        break;
                }
            }

            /**
             * 处理https请求
             *
             * webView默认是不处理https请求的，页面会显示空白
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
        webView.loadUrl("https://www.baidu.com"); //  http://wanandroid.com/index    http://192.168.102.114:8093/login/test
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callJS:
                break;
            case R.id.callAndroid:
                break;
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);

        callJS = (Button) findViewById(R.id.callJS);
        callJS.setOnClickListener(this);

        callAndroid = (Button) findViewById(R.id.callAndroid);
        callAndroid.setOnClickListener(this);
    }

}
