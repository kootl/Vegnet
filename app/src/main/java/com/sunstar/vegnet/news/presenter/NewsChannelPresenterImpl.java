package com.sunstar.vegnet.news.presenter;

import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.tool.NetWorkTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.news.bean.NewsChannelBean;
import com.sunstar.vegnet.news.contract.NewsChannelContract;
import com.sunstar.vegnet.news.model.NewsChannelModelImpl;

/**
 * Created by louisgeek on 2016/12/07
 */

public class NewsChannelPresenterImpl implements NewsChannelContract.Presenter<NewsChannelContract.View> {


    private NewsChannelContract.Model mModel;
    private NewsChannelContract.View mView;

    public NewsChannelPresenterImpl(NewsChannelContract.View view) {
        this.attachView(view);
        mModel = new NewsChannelModelImpl();
    }

    @Override
    public void attachView(NewsChannelContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {
        if (!NetWorkTool.isNetWorkConnected()){
            ToastTool.showImageWarn("无网络连接,请检查后重试!");
            mView.showNoDataNotice();
            return;
        }
        mView.showProgress();
        mModel.loadData(new BaseCallBack<BaseBeanShowApi<NewsChannelBean>>() {

            @Override
            public void onSuccess(BaseBeanShowApi<NewsChannelBean> data) {
                mView.hideProgress();
                if (data!=null&&data.getShowapi_res_body()!=null&&data.getShowapi_res_body().getChannelList()!=null
                        &&data.getShowapi_res_body().getChannelList().size()>0)
                {
                    mView.setupData(data);
                    //
                    mView.hideNoDataNotice();
                }else {
                    mView.showNoDataNotice();
                }
            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }
}