package com.sunstar.vegnet.kootl.comm.base;

/**
 * Created by louisgeek on 2016/12/4.
 */

public interface BasePresenter<V> {
    void attachView(V view);
    void detachView();
    void gainData();
}