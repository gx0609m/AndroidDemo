package com.example.administrator.androiddemo.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.androiddemo.MainActivity;
import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by gx on 2018/4/19 0019
 */

public class WebViewLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "WebViewLearningActivity";

    private WebView webView;
    private Button callJS;  // Android调JS方法 ——— 要设置JS支持


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_learning);

        initView();
        loadUrl();
        // 定义一个与JS对象映射的Android类，方便JS调用Android方法
        webView.addJavascriptInterface(new JsCallAndroid(getApplicationContext()), "test");
    }

    private void loadUrl() {
        /*
         * WebViewClient和WebChromeClient的功能都是帮助WebView分担一些工作，
         * 这样一来WebView就只需专注于自己的加载网页的工作，
         * 而加载网页过程中所需处理的一些其他事宜便交给WebViewClient和WebChromeClient去处理；
         *
         * 这种设计一定程度上体现了单一职责原则；
         */
        webSettings();
        webViewClient();
        webChromeClient();
        webView.loadUrl("file:///android_asset/test.html"); //  http://wanandroid.com/index    http://192.168.102.114:8093/login/test   file:///android_asset/XX.html
    }

    private void webSettings() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        /*
         * JS相关
         */
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        /*
         * Web页适配屏幕
         */
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        /*
         * 缩放处理
         */
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        /*
         * 内容布局
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows(); //多窗口
        /*
         * 文件缓存
         *
         * 使用缓存 LOAD_CACHE_ELSE_NETWORK
         * 不使用缓存 LOAD_NO_CACHE
         */
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //webView中的缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        /*
         * 其他设置
         */
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码格式
        webSettings.setPluginState(WebSettings.PluginState.OFF); //设置是否支持flash插件
        webSettings.setDefaultFontSize(20); //设置默认字体大小
        webSettings.setNeedInitialFocus(true); //当webView调用requestFocus时为webView设置节点
    }

    /**
     * WebViewClient ——— 监听网页加载状态（开始、完成之类的）、处理各种通知、请求事件
     */
    private void webViewClient() {
        webView.setWebViewClient(new WebViewClient() {
            /**
             * 通过拦截Url来实现JS调用Android方法
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*
                 * 根据协议的参数，判断是否是所需要的url
                 * 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                 * 假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                 */
                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议，就解析往下解析参数
                if (uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    if (uri.getAuthority().equals("webview")) {
                        /*
                         * 执行JS所需要调用的逻辑
                         * 可以在协议上带有参数并传递到Android上
                         */
                        // 获取参数值
                        String param1Value = uri.getQueryParameter("arg1");
                        String param2Value = uri.getQueryParameter("arg2");
                        Log.e(TAG, "两个参数值分别为：" + "参数1：" + param1Value + "参数2：" + param2Value); // 两个参数值分别为111、222
                        // 获取参数名
                        Set<String> collection = uri.getQueryParameterNames();
                        // 从set中取出指定位置的元素，通过将Set转换成List，再取值
                        List<String> list = new ArrayList<String>(collection);
                        if (list.size() >= 2) {
                            JSCallAndroidWithUrlIntercept(list.get(0), list.get(1)); // 两个参数名分别为arg1、arg2
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            /**
             * 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器，而是在当前WebView中显示
             *
             * 返回false ——— 当前 WebView 处理URL；
             * 返回true ——— Android 系统唤起系统浏览器处理URL；
             * 默认放回false
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*if (Uri.parse(url).getHost().equals("github.com/gx0609m")) {
                    //如果是自己站点的链接, 则用本地WebView跳转
                    return false;
                }
                //如果不是自己的站点则launch别的Activity来处理
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(intent);
                return true;*/

                /*view.loadUrl(request.getUrl().toString());*/

                return super.shouldOverrideUrlLoading(view, request);
            }

            /**
             * 当WebView的缩放因子改变时这个方法会被回调
             */
            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
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
             * 在api 23里面 上面方法已经deprecation
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
     * WebChromeClient ——— 监听网页加载进度、处理 Javascript 的对话框,网站图标,网站标题等等
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
             * 支持javascript的警告框 ——— 需要webSettings设置支持 JS ——— 无返回值
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
             * 支持javascript的确认框 ——— 两个返回值 true、false，点击“确认”返回true，点击“取消”，返回“false”
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

            /**
             * 支持javascript输入框 ——— 设置任意返回值 ，点击“确认”返回输入框中的值，点击“取消”返回null
             *
             * 参数message:代表prompt('')的内容（'xxx'中的内容xxx）
             */
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                /*
                 * 根据协议的参数，判断是否是所需要的url
                 * 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                 * 假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                 */
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("js")) { // 如果url的协议 = 预先约定的 js 协议，就解析往下解析参数
                    if (uri.getAuthority().equals("webview")) {  // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                        /*
                         * 执行JS所需要调用的逻辑
                         * 可以在协议上带有参数并传递到Android上
                         */
                        // 获取参数值
                        String param1Value = uri.getQueryParameter("arg1");
                        String param2Value = uri.getQueryParameter("arg2");
                        Log.e(TAG, "两个参数值分别为：" + "参数1：" + param1Value + "参数2：" + param2Value); // 两个参数值分别为111、222
                        // 获取参数名
                        Set<String> collection = uri.getQueryParameterNames();
                        // 从set中取出指定位置的元素，通过将Set转换成List，再取值
                        List<String> list = new ArrayList<String>(collection);
                        if (list.size() >= 2) {
                            JSCallAndroidWithUrlIntercept(list.get(0), list.get(1)); // 两个参数名分别为arg1、arg2
                        }
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);

                /*final EditText et = new EditText(WebViewLearningActivity.this);
                et.setText(defaultValue);
                new AlertDialog.Builder(WebViewLearningActivity.this)
                        .setTitle(message)
                        .setView(et)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm(et.getText().toString());
                                Toast.makeText(WebViewLearningActivity.this, et.getText().toString(), Toast.LENGTH_SHORT).show();
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
                return true;*/
            }
        });
    }

    /**
     * 通过shouldOverrideUrlLoading()拦截Url，来实现JS调用Android方法
     */
    private void JSCallAndroidWithUrlIntercept(String param1, String param2) {
        Log.e(TAG, "通过拦截Url的方式，使JS调用了Android的方法，并传了两个参数名：" + param1 + "和" + param2);
        Toast.makeText(this, "通过拦截Url的方式，使JS调用了Android的方法，并传了两个参数名：" + param1 + "和" + param2, Toast.LENGTH_LONG).show();
    }

    //点击返回上一Web页而不是从当前WebActivity退出 ——— 如果当前Web页能返回的话canGoBack
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*
             * 注意：
             *     1.JS代码调用一定要在 onPageFinished() 回调之后才能调用，否则不会被调用；
             *       因此，如果是在onPageFinished()回调之前，evaluateJavascript()是获取不到JS的返回值的；
             *     2.evaluateJavascript需要minSDKVersion >= 19
             */
            case R.id.callJS:  // Android调JS方法    ———    1.loadUrl()；  2.evaluateJavascript()；
//                webView.loadUrl("javascript:callJSWithLoadUrl()");
                webView.evaluateJavascript("javascript:callJSWithEvaluateJavascript()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.e(TAG, "JS方法的返回值是：" + value);
                        Toast.makeText(WebViewLearningActivity.this, "JS方法的返回值是：" + value, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);

        callJS = (Button) findViewById(R.id.callJS);
        callJS.setOnClickListener(this);
    }

    // js通信类
    public class JsCallAndroid {
        Context context;

        public JsCallAndroid(Context c) {
            context = c;
        }

        @JavascriptInterface
        public void callMethod(String type) {
            Log.e("tag", "JS调了Android的XX()方法，同时传了一个String类型的值：" + type);
            Toast.makeText(context, "JS调了Android的XX()方法，同时传了一个String类型的值：" + type, Toast.LENGTH_SHORT).show();
        }
    }

}
