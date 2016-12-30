package com.sunstar.vegnet.news.presenter;

import com.socks.library.KLog;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.tool.NetWorkTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.contract.NewsListContract;
import com.sunstar.vegnet.news.model.NewsListModelImpl;

/**
 * Created by louisgeek on 2016/12/06
 */

public class NewsListPresenterImpl implements NewsListContract.Presenter<NewsListContract.View> {


    private NewsListContract.Model mModel;
    private NewsListContract.View mView;

    public NewsListPresenterImpl(NewsListContract.View view) {
        this.attachView(view);
        mModel = new NewsListModelImpl();
    }

    @Override
    public void attachView(NewsListContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {
        if (!NetWorkTool.isNetWorkConnected()) {
            ToastTool.showImageWarn("无网络连接,请检查后重试!");
            mView.showNoDataNotice();
            return;
        }
        mView.showProgress();
        mModel.loadData(new BaseCallBack<BaseBeanShowApi<NewsListBean>>() {
            @Override
            public void onSuccess(BaseBeanShowApi<NewsListBean> data) {
                // KLog.d("onRefresh data"+data);
                mView.hideProgress();
                //
                if (data != null && data.getShowapi_res_body() != null && data.getShowapi_res_body().getPagebean() != null
                        && data.getShowapi_res_body().getPagebean().getContentlist() != null
                        && data.getShowapi_res_body().getPagebean().getContentlist().size() > 0) {
                    mView.setupData(data);
                    //
                    mView.hideNoDataNotice();
                } else {
                    mView.showNoDataNotice();
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


    @Override
    public void gainMoreData(int page) {
        mView.showMoreDataLoading();
        mModel.loadMoreData(page, new BaseCallBack<BaseBeanShowApi<NewsListBean>>() {
            @Override
            public void onSuccess(BaseBeanShowApi<NewsListBean> data) {
                //
                mView.hideMoreDataLoading();

                if (data != null && data.getShowapi_res_body() != null && data.getShowapi_res_body().getPagebean() != null
                        && data.getShowapi_res_body().getPagebean().getContentlist() != null
                        && data.getShowapi_res_body().getPagebean().getContentlist().size() > 0) {
                    mView.setupMoreData(data);
                } else {
                    mView.showMoreDataLoadComplete();
                }
            }

            @Override
            public void onError(String msg) {
                mView.hideMoreDataLoading();
                mView.showMessage(msg);
            }
        });
    }
}