package com.example.administrator.androiddemo.network.retrofit_rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.model.MovieEntity;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Retrofit + RxJava
 * <p>
 * Created by gx on 2018/5/7 0007
 */

public class RetrofitWithRxJavaLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button asyncGet;  // Retrofit+RxJava异步Get请求

    String baseUrl = "https://api.douban.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rxjava_learning);

        initView();
    }

    /**
     * Retrofit+RxJava异步Get请求
     */
    private void asyncGetWithRetrofitAndRxJava() {
        /*
         * a.创建 Retrofit 实例
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                // 添加一个Call类转换工厂，将返回的Call类转换为RxJava中的Observable
                // 因为我们定义的接口返回的不再是Call<MovieEntity>,而是Observable<MovieEntiy>
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .build();
        /*
         * b.创建网络请求接口 rxMovieService 实例
         */
        RxMovieService rxMovieService = retrofit.create(RxMovieService.class);
        /*
         * c.创建 observable 实例（对网络请求 movieService 的封装）
         */
        Observable<MovieEntity> observable = rxMovieService.getTopMovie(0, 10);
        /*
         * d.通过 observable 进行网络请求的同时，还可 线程切换、数据处理 等，
         *   RxJava中的操作符能让这一切都变得简单、条理
         */
        /*observable.subscribeOn(Schedulers.io())  // 请求数据设置在 IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 更新UI设置在 主线程
                .subscribe(new Observer<MovieEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {

                        *//* 这里使用了for循环来取得数据，要是逻辑复杂点的话，就可能产生迷之缩进，
                         * 因此我们可以使用RxJava中的 flatMap操作符 来避免for循环的操作：
                         *      flatMap操作符：
                         *          主要是用于一对多的对像变换，例如这里的一个MovieEntity对象下可以有多个SubjectsBean对象
                         *//*
                        List<MovieEntity.SubjectsBean>
                                subjectsBeenList
                                = movieEntity.getSubjects();
                        for (MovieEntity.SubjectsBean
                                subjectsBean : subjectsBeenList) {
                            String title = subjectsBean.getTitle();
                            Log.i("Retrofit&RxJava Success", title);
                        }
                    }
                });*/
        /*
         * 使用 flatMap 操作符的写法
         *
         * flatMap() 的原理是这样的：
         *      1.使用传入的事件对象创建一个 Observable 对象；
         *      2.并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
         *      3.每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法；
         *
         * 这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』之后通过统一路径分发了下去。而这个『铺平』就是 flatMap() 所谓的 flat；
         */
        observable.subscribeOn(Schedulers.io())
                // flatMap() 中返回的是个 Observable 对象，并且这个Observable 对象并不是被直接发送到了 Subscriber的回调方法中
                .flatMap(new Func1<MovieEntity, Observable
                        <MovieEntity.SubjectsBean>>() {
                    @Override
                    public Observable<MovieEntity.SubjectsBean> call
                            (MovieEntity movieEntity) {
                        return Observable
                                .from(movieEntity.getSubjects());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntity.SubjectsBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(MovieEntity.SubjectsBean subjectsBean) {
                        String title = subjectsBean.getTitle();
                        Log.i("Retrofit&RxJava flatMap", title);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asyncGet:
                asyncGetWithRetrofitAndRxJava();
                break;
        }
    }

    private void initView() {
        asyncGet = (Button) findViewById(R.id.asyncGet);
        asyncGet.setOnClickListener(this);
    }
}
