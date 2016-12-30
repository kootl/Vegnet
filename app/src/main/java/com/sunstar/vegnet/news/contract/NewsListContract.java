package com.sunstar.vegnet.news.contract;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.base.BaseModel;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.base.BaseView;

/**
 * Created by louisgeek on 2016/12/6.
 */

public class NewsListContract {
    public interface View<T> extends BaseView<T> {
        void showMoreDataLoading();
        void hideMoreDataLoading();
        void showMoreDataLoadComplete();
        void setupMoreData(T data);
    }

    public interface Presenter<V> extends BasePresenter<V> {
        void gainMoreData(int page);
    }

    public interface Model<T> extends BaseModel<T> {
        void loadMoreData(int page,BaseCallBack<T> baseCallBack);
    }
}