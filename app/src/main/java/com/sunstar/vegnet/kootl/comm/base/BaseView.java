package com.sunstar.vegnet.kootl.comm.base;

/**
 * Created by louisgeek on 2016/12/4.
 */

public interface BaseView<T> {

    void showProgress();

    void hideProgress();

    void showNoDataNotice();

    void hideNoDataNotice();

    void showMessage(String msg);

    void setupData(T data);

}