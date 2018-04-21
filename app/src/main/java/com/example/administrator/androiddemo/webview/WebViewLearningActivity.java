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
import android.widget.ProgressBar;

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

    private void webSettings() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setJavaScriptEnabled(true); //支持JS
    }

    /**
     * WebViewClient ——— 处理各种通知、请求事件
     */
    private void webViewClient() {
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
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     */
    private void webChromeClient() {
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * 获得网页的加载进度并显示，可以通过progressBar来显示
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            /**
             * 获取Web页中的标题
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            /**
             * 支持javascript的警告框 ——— 需要webSettings设置支持 JS
             *
             * 可以将网页中的alert框，替换成Android中的Toast、AlertDialog之类的，由于是alert框，所以换成AlertDialog的话，只写一个setPositiveButton
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewLearningActivity.this)
                        .setTitle("JsAlert")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .show();
                // If the client returns true, WebView will assume that the client will handle the dialog.
                // If the client returns false, it will continue execution.
                // 如果这里return false，那么会先弹出网页alert，再弹出Android中的AlertDialog
                return true;
            }

            /**
             * 支持javascript的确认框
             *
             * 可以将网页中的confirm，替换成AlertDialog，由于confirm框和alert框不一样，需要写setPositiveButton和setNegativeButton
             */
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewLearningActivity.this)
                        .setTitle("JsConfirm")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    private void loadUrl() {
        webSettings();
        webViewClient();
        webChromeClient();
        webView.loadUrl("file:///android_asset/test.html"); //  http://wanandroid.com/index    http://192.168.102.114:8093/login/test   file:///android_asset/XX.html
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
