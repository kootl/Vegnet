package com.sunstar.vegnet.kootl.imageshow.contract;

import com.sunstar.vegnet.kootl.comm.base.BaseModel;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.base.BaseView;

/**
 * Created by louisgeek on 2016/12/8.
 */

public class ImageShowContract {

    public interface View<T> extends BaseView<T> {
    }

    public interface Presenter<V> extends BasePresenter<V> {
    }

    public interface Model<T> extends BaseModel<T> {
    }


}