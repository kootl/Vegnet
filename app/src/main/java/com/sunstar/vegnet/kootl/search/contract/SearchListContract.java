package com.sunstar.vegnet.kootl.search.contract;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.base.BaseModel;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.base.BaseView;

/**
 * Created by louisgeek on 2016/12/12.
 */

public class SearchListContract {

    public interface View<T> extends BaseView<T> {
        String getQueryKey();
    }

    public interface Presenter<V> extends BasePresenter<V> {
    }

    public interface Model<T> extends BaseModel<T> {
        void queryValue(String key,BaseCallBack<T> baseCallBack);
    }

}