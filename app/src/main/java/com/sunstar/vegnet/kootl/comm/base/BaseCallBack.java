package com.sunstar.vegnet.kootl.comm.base;

/**
 * Created by louisgeek on 2016/12/4.
 */

public interface BaseCallBack<T> {
    void onSuccess(T data);

    void onError(String msg);
}
