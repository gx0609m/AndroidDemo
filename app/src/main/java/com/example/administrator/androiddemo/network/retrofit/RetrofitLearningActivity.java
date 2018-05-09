package com.example.administrator.androiddemo.network.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.model.MovieEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit
 * <p>
 * Created by gx on 2018/5/7 0007
 */

public class RetrofitLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RetrofitLearning";

    private Button asyncGetReq;  // Retrofit异步Get请求

    String bserUrl = "https://api.douban.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_learning);

        initView();
    }

    /**
     * Retrofit异步Get请求（a、b、c、d）
     */
    private void asyncGetRequestWithRetrofit() {
        /*
         * a.创建 Retrofit 实例
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(bserUrl)       // 定义baseUrl
                .addConverterFactory(GsonConverterFactory.create())   // 添加转换工厂，将接口返回的数据转换为我们需要的实体类，由于接口返回的是Json数据，所以这里使用Gson转换工厂
                .build();
        /*
         * b.创建网络请求接口 movieService 实例
         */
        MovieService movieService = retrofit.create
                (MovieService.class);
        /*
         * c.创建 call 实例
         */
        Call<MovieEntity> call = movieService.getTopMovie(0, 10);   // 调用MovieService接口对象中的方法，返回一个Call类实体
        /*
         * d.发送网络请求 ——— 异步、同步
         */
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call
                    , Response<MovieEntity> response) {
                List<MovieEntity.SubjectsBean> subjectsBeanList
                        = response.body().getSubjects();
                for (MovieEntity.SubjectsBean
                        subjectsBean : subjectsBeanList) {
                    String title = subjectsBean.getTitle();
                    Log.e(TAG, title);
                }
            }

            @Override
            public void onFailure(
                    Call<MovieEntity> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asyncGetReq:
                asyncGetRequestWithRetrofit();
                break;
        }
    }

    private void initView() {
        asyncGetReq = (Button) findViewById(R.id.asyncGetReq);
        asyncGetReq.setOnClickListener(this);
    }
}
