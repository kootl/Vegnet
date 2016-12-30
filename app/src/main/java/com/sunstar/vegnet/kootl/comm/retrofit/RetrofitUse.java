package com.sunstar.vegnet.kootl.comm.retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.sunstar.vegnet.kootl.comm.retrofit.bean.GitHubUserBean;
import com.sunstar.vegnet.kootl.comm.retrofit.bean.UserFollowerBean;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louisgeek on 2016/12/11.
 */
@Deprecated
public class RetrofitUse {
    private final static String BASE_URL = " https://api.github.com/";

    public static void doNormal() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<ResponseBody> call = gitHubService.getUserString("louisgeek");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GitHubUserBean gitHubUserBean = new Gson().fromJson(result, GitHubUserBean.class);
                KLog.d(gitHubUserBean.getName());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public static void main(String[] args) {
        doRx2();
    }

    public static void doSimple() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<GitHubUserBean> call = gitHubService.getUserBean("louisgeek");
        call.enqueue(new Callback<GitHubUserBean>() {
            @Override
            public void onResponse(Call<GitHubUserBean> call, Response<GitHubUserBean> response) {
                GitHubUserBean gitHubUserBean = response.body();
                KLog.d(gitHubUserBean.getName());
            }

            @Override
            public void onFailure(Call<GitHubUserBean> call, Throwable t) {

            }
        });

    }

    public static void doEasy() {
        GitHubService gitHubService = RetrofitHelper.createService(GitHubService.class);
        Call<GitHubUserBean> call = gitHubService.getUserBean("louisgeek");
        call.enqueue(new Callback<GitHubUserBean>() {
            @Override
            public void onResponse(Call<GitHubUserBean> call, Response<GitHubUserBean> response) {
                GitHubUserBean gitHubUserBean = response.body();
                KLog.d(gitHubUserBean.getName());
            }

            @Override
            public void onFailure(Call<GitHubUserBean> call, Throwable t) {

            }
        });
    }

    public static void doRx() {
        GitHubService gitHubService = RetrofitHelper.createService(GitHubService.class);
        final Call<GitHubUserBean> rCall = gitHubService.getUserBean("louisgeek");

        Observable observable = Observable.create(new Observable.OnSubscribe<GitHubUserBean>() {

            @Override
            public void call(Subscriber<? super GitHubUserBean> subscriber) {
                try {
                    Response<GitHubUserBean> response = rCall.execute();
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GitHubUserBean, GitHubUserBean>() {
                    @Override
                    public GitHubUserBean call(GitHubUserBean gitHubUserBean) {
                        if (TextUtils.isEmpty(gitHubUserBean.getBio())) {
                            gitHubUserBean.setBio("bio is empty");
                        }
                        return gitHubUserBean;
                    }
                }).subscribe(new Subscriber<GitHubUserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GitHubUserBean bean) {
                KLog.d("xxxx" + bean.getName());
            }
        });

    }

    public static void doRx2() {
        GitHubService gitHubService = RetrofitSingleton.getInstance().createService(GitHubService.class);
        Observable<GitHubUserBean> observable = gitHubService.getUserBeanRx("louisgeek");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GitHubUserBean, GitHubUserBean>() {
                    @Override
                    public GitHubUserBean call(GitHubUserBean gitHubUserBean) {
                        if (TextUtils.isEmpty(gitHubUserBean.getBio())) {
                            gitHubUserBean.setBio("bio is empty");
                        }
                        return gitHubUserBean;
                    }
                }).subscribe(new Subscriber<GitHubUserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GitHubUserBean bean) {
                KLog.d("xxxx" + bean.getName());
            }
        });

    }

    public static void doRxlist() {
        GitHubService gitHubService = RetrofitHelper.createService(GitHubService.class);
        final Call<List<UserFollowerBean>> rCall = gitHubService.getFollowers("louisgeek");

        Observable observable = Observable.create(new Observable.OnSubscribe<List<UserFollowerBean>>() {

            @Override
            public void call(Subscriber<? super List<UserFollowerBean>> subscriber) {
                try {
                    Response<List<UserFollowerBean>> response = rCall.execute();
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<List<UserFollowerBean>, List<UserFollowerBean>>() {
                    @Override
                    public List<UserFollowerBean> call(List<UserFollowerBean> list) {

                        /**
                         * 比较器
                         * 排序
                         * 如果o1小于o2,返回一个负数
                         * 如果o1大于o2，返回一个正数
                         * 如果他们相等，则返回0
                         */
                        Collections.sort(list, new Comparator<UserFollowerBean>() {
                            @Override
                            public int compare(UserFollowerBean o1, UserFollowerBean o2) {
                                return o1.getId() - o2.getId();
                            }
                        });
                        return list;
                    }
                })
                .subscribe(new Subscriber<List<UserFollowerBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<UserFollowerBean> list) {
                        KLog.d("xxxx" + list.size());
                    }
                });

    }
}
