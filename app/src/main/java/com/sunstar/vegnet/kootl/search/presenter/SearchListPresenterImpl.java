package com.sunstar.vegnet.kootl.search.presenter;

import com.socks.library.KLog;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.search.bean.SearchListBean;
import com.sunstar.vegnet.kootl.search.contract.SearchListContract;
import com.sunstar.vegnet.kootl.search.model.SearchListModelImpl;

/**
 * Created by louisgeek on 2016/12/12
 */

public class SearchListPresenterImpl implements SearchListContract.Presenter<SearchListContract.View> {

    private SearchListContract.Model mModel;
    private SearchListContract.View mView;

    public SearchListPresenterImpl(SearchListContract.View view) {
        this.attachView(view);
        mModel = new SearchListModelImpl();
    }

    @Override
    public void attachView(SearchListContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


    @Override
    public void gainData() {
        mView.showProgress();

        String queryKey = "";
        if (mView.getQueryKey() != null) {
            queryKey = mView.getQueryKey();
        }

        mModel.queryValue(queryKey, new BaseCallBack<BaseBeanShowApi<SearchListBean>>() {
            @Override
            public void onSuccess(BaseBeanShowApi<SearchListBean> data) {
                // KLog.d("onRefresh data"+data);
                mView.hideProgress();
                //
                if (data != null && data.getShowapi_res_body() != null && data.getShowapi_res_body().getPagebean() != null
                        && data.getShowapi_res_body().getPagebean().getContentlist() != null
                        ) {
                    mView.setupData(data);
                } else {
                    mView.showMessage("数据有误");
                }
            }

            @Override
            public void onError(String msg) {
                KLog.d("onRefresh data" + msg);
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }
}