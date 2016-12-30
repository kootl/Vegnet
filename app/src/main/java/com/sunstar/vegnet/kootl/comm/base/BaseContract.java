package com.sunstar.vegnet.kootl.comm.base;

/**
 * Created by louisgeek on 2016/12/4.
 */

public class BaseContract {

    public interface View<T> extends BaseView<T> {
    }

    public interface Presenter<V> extends BasePresenter<V> {
    }

    public interface Model<T> extends BaseModel<T> {
    }


}