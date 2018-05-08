package com.example.administrator.androiddemo.network.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.model.MovieEntity;
import com.example.administrator.androiddemo.utils.ThreadPoolUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * OkHttp
 * <p>
 * Created by gx on 2018/5/7 0007
 */

public class OkHttpLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "OkHttpLearningActivity";

    private Button syncGetReq;   // 同步Get请求
    private Button asyncGetReq;  // 异步Get请求
    private Button postAsyncUploadKeyValue;  // Post异步上传键值对
    private Button postAsyncUploadFile;  // Post异步上传文件
    private Button postAsyncUploadStream;  // Post异步上传流

    private String baseUrl = "https://api.douban.com/v2/movie/";
    private int start = 0;
    private int count = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_learning);

        initView();
    }

    /**
     * 同步的get请求（1、2、3、4）
     */
    private void syncGetRequest() {
        /*
         * 1.创建 okHttpClient 实例
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        /*
         * 2.构建 request 对象实例
         */
        Request request = new Request.Builder()
                .method("GET", null)                         // OkHttp 默认使用get请求
                .url(baseUrl + "top250?start=" + start + "&count=" + count)  // 与Retrofit相比，如果需要传参的话，需要手动拼接url
                .build();
        /*
         * 3.构建 call 实例
         */
        final okhttp3.Call call = okHttpClient.newCall(request);  // OkHttp中是Call,而Retrofit中是Call<T>
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                     * 4.进行同步网络请求，获取response
                     */
                    Response response = call.execute();  // 同步请求
                    String jsonData = response.body().string();
                    Log.e(TAG, jsonData);       // 打印返回的网络请求内容
                    Gson gson = new Gson();
                    MovieEntity movieEntity = gson.fromJson(jsonData, MovieEntity.class);
                    List<MovieEntity.SubjectsBean> subjectsBeenList
                            = movieEntity.getSubjects();
                    for (MovieEntity.SubjectsBean
                            subjectsBean : subjectsBeenList) {
                        String title = subjectsBean.getTitle();
                        Log.e(TAG, title);      // 列表展示返回实体中的title属性
//                        Log.e(TAG, Thread.currentThread().getName());  // 打印当前所在的线程名
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadPoolUtil.getInstance().execute(runnable);  // 使用的是OkHttp的同步请求，为了防止阻塞UI，因此放在线程池中执行
    }

    /**
     * 异步的get请求（1、2、3、4）
     */
    private void asyncGetRequest() {
        /*
         * 1.创建 okHttpClient 实例
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        /*
         * 2.构建 request 对象实例
         */
        Request request = new Request.Builder()                              // OkHttp 默认使用get请求
                .url(baseUrl + "top250?start=" + start + "&count=" + count)  // 与Retrofit相比，如果需要传参的话，需要手动拼接url
                .build();
        /*
         * 3.构建 call 实例
         */
        final okhttp3.Call call = okHttpClient.newCall(request);             // OkHttp中是Call,而Retrofit中是Call<T>
        /*
         * 4.异步网络请求
         */
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(OkHttpLearningActivity.this, "Http Request Failure ~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Log.e(TAG, jsonData);                                        // 打印返回的网络请求内容
                Gson gson = new Gson();
                MovieEntity movieEntity = gson.fromJson(jsonData, MovieEntity.class);
                List<MovieEntity.SubjectsBean> subjectsBeenList
                        = movieEntity.getSubjects();
                for (MovieEntity.SubjectsBean
                        subjectsBean : subjectsBeenList) {
                    String title = subjectsBean.getTitle();
                    Log.e(TAG, title);                                       // 列表展示返回实体中的title属性
                }
            }
        });
    }

    /**
     * Post异步上传键值对（1、2、3、4、5）
     */
    private void asyncUploadKeyValueWithPost() {
        /*
         * 1.创建 okHttpClient 实例
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        /*
         * 2.构建表单 formBody 实例 ——— 相较于Get请求，多了此步骤
         */
        FormBody formBody = new FormBody.Builder()
                .add("name", "gx")
                .build();
        /*
         * 3.构建请求 request 实例
         */
        Request request = new Request.Builder().url("http://www.baidu.com")
                .post(formBody)
                .build();
        /*
         * 4.构建 call 对象实例
         */
        Call call = okHttpClient.newCall(request);
        /*
         * 5.异步网络请求
         */
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功
            }
        });
    }

    /**
     * Post异步上传文件（1、2、3、4、5）
     */
    private void asyncUploadFileWithPost() {
        /*
         * 1.创建 okHttpClient 实例
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        /*
         * 2.创建 RequestBody 以及所需的参数 ——— 相较于Get请求，多了此步骤
         *    a.获取文件
         *    b.创建 MediaType 设置上传文件类型
         *    c.获取请求体
         */
        File file = new File(Environment.getExternalStorageDirectory() + "test.txt");
        MediaType MEDIATYPE = MediaType.parse("text/plain; charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIATYPE, file);
        /*
         * 3.构建请求 request 实例
         */
        Request request = new Request.Builder().url("http://www.baidu.com")
                .post(requestBody)
                .build();
        /*
         * 4.构建 call 对象实例
         */
        Call call = okHttpClient.newCall(request);
        /*
         * 5.异步网络请求
         */
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功
            }
        });
    }

    /**
     * Post异步上传流（1、2、3、4、5）
     */
    private void asyncUploadStreamWithPost() {
        /*
         * 1.创建 okHttpClient 实例
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        /*
         * 2.创建 RequestBody 以及所需的参数 ——— 相较于Get请求，多了此步骤
         */
        final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
        final String postBody = "Hello World";
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_TEXT;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(postBody);
            }

            @Override
            public long contentLength() throws IOException {
                return postBody.length();
            }
        };
        /*
         * 3.构建请求 request 实例
         */
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .post(requestBody)
                .build();
         /*
         * 4.构建 call 对象实例
         */
        Call call = okHttpClient.newCall(request);
        /*
         * 5.异步网络请求
         */
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.syncGetReq:
                syncGetRequest();
                break;
            case R.id.asyncGetReq:
                asyncGetRequest();
                break;
            case R.id.postAsyncUploadKeyValue:
                asyncUploadKeyValueWithPost();
                break;
            case R.id.postAsyncUploadFile:
                asyncUploadFileWithPost();
                break;
            case R.id.postAsyncUploadStream:
                asyncUploadStreamWithPost();
                break;
        }
    }

    private void initView() {
        syncGetReq = (Button) findViewById(R.id.syncGetReq);
        syncGetReq.setOnClickListener(this);

        asyncGetReq = (Button) findViewById(R.id.asyncGetReq);
        asyncGetReq.setOnClickListener(this);

        postAsyncUploadKeyValue = (Button) findViewById(R.id.postAsyncUploadKeyValue);
        postAsyncUploadKeyValue.setOnClickListener(this);

        postAsyncUploadFile = (Button) findViewById(R.id.postAsyncUploadFile);
        postAsyncUploadFile.setOnClickListener(this);

        postAsyncUploadStream = (Button) findViewById(R.id.postAsyncUploadStream);
        postAsyncUploadStream.setOnClickListener(this);
    }
}
