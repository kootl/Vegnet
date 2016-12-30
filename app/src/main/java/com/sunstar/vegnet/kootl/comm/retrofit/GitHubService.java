package com.sunstar.vegnet.kootl.comm.retrofit;

import com.sunstar.vegnet.kootl.comm.retrofit.bean.GitHubUserBean;
import com.sunstar.vegnet.kootl.comm.retrofit.bean.UserFollowerBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by louisgeek on 2016/12/11.
 */

public interface GitHubService {
    @GET("users/{user}")
    Call<ResponseBody> getUserString(@Path("user") String user);

    @GET("users/{user}")
    Call<GitHubUserBean> getUserBean(@Path("user") String user);

    @GET("users/{user}")
    Observable<GitHubUserBean> getUserBeanRx(@Path("user") String user);

    @GET("users/{user}/followers")
    Call<List<UserFollowerBean>> getFollowers(@Path("user") String user);
}
