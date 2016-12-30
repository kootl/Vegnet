package com.sunstar.vegnet.kootl.comm.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by louisgeek on 2016/12/11.
 */

public class RetrofitSingleton {
    private String BASE_URL = " https://api.github.com/";
    private Retrofit mRetrofit;
    /**
     * ====================================================
     */
    private static volatile RetrofitSingleton mInstance;

    /* 私有构造方法，防止被实例化 */
    private RetrofitSingleton() {
/**
 * 自定日志拦截器
 */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)//日志拦截器
                .addNetworkInterceptor(mAddHeaderInterceptor)
                .build();


        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                //小集合的converter放前面，大集合的converter放后面
                .addConverterFactory(ScalarsConverterFactory.create())//基础数据转换
                .addConverterFactory(GsonConverterFactory.create())//json数据转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//将Callable接口转换成Observable接口
                .build();
    }

    public static RetrofitSingleton getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitSingleton.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitSingleton();
                }
            }
        }
        return mInstance;
    }

    /**
     * ==========================================
     */
    /**
     * 拦截器  给所有的请求添加消息头
     */
    private static Interceptor mAddHeaderInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("userid", "userid1")
                    .addHeader("token", "token1")
                    .build();
            Response response = chain.proceed(request);
            return response;
        }
    };

    public <S> S createService(Class<S> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
