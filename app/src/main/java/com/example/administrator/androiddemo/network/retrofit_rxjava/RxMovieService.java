package com.example.administrator.androiddemo.network.retrofit_rxjava;

import com.example.administrator.androiddemo.model.MovieEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gx on 2018/5/10 0010
 */

public interface RxMovieService {

    // 此处返回的是Observable<T>,如果只使用Retrofit,则返回Call<T>
    @GET("v2/movie/top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start,
                                        @Query("count") int count);
}
