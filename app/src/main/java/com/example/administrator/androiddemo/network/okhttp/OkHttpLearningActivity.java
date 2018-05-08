package com.example.administrator.androiddemo.network.okhttp;

import android.os.Bundle;
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

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp
 * <p>
 * Created by gx on 2018/5/7 0007
 */

public class OkHttpLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "OkHttpLearningActivity";

    private Button syncGetReq;
    private Button asyncGetReq;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.syncGetReq:
                syncGetRequest();
                break;
            case R.id.asyncGetReq:
                asyncGetRequest();
                break;
        }
    }

    private void initView() {
        syncGetReq = (Button) findViewById(R.id.syncGetReq);
        syncGetReq.setOnClickListener(this);

        asyncGetReq = (Button) findViewById(R.id.asyncGetReq);
        asyncGetReq.setOnClickListener(this);
    }
}
