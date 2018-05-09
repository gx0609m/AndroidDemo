package com.example.administrator.androiddemo.network.retrofit;

import com.example.administrator.androiddemo.model.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gx on 2018/5/9 0009
 */

public interface MovieService {

    // https://api.douban.com/v2/movie/top250?start=0&count=10
    @GET("v2/movie/top250")
    Call<MovieEntity> getTopMovie(   // 此处返回的是Call<T>,如果使用了RxJava,则返回Observable<T>
                      @Query("start") int start,
                      @Query("count") int count);
}
