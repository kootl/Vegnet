package com.sunstar.vegnet.kootl.comm.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by louisgeek on 2016/12/11.
 */

@Deprecated
public class RetrofitHelper {
    private static String BASE_URL = " https://api.github.com/";
    private static Retrofit mRetrofit;
    static {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//json数据转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//将Callable接口转换成Observable接口
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
