package com.example.myapplication.demo;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Description:
 * @Author: ch
 * @CreateDate: 2019/7/25 21:09
 */
public class NetUtil {

    private Retrofit retrofit;

    public NetUtil() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("okhttp","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).addInterceptor(loggingInterceptor);


        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://cn.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public interface ImageService {
        @GET("HPImageArchive.aspx")
        Observable<ImageBean> getBingImage(@Query("format") String format,
                                     @Query("idx") int idx,
                                     @Query("n") int n);
    }

    public Observable<ImageBean> getBingImage(String format, int idx, int n) {
        return retrofit.create(ImageService.class).getBingImage(format, idx, n);
    }

}

