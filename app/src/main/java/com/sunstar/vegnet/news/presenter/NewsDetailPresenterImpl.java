package com.sunstar.vegnet.news.presenter;


import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.contract.NewsDetailContract;
import com.sunstar.vegnet.news.model.NewsDetailModelImpl;

/**
 * Created by louisgeek on 2016/12/04
 */

public class NewsDetailPresenterImpl implements NewsDetailContract.Presenter<NewsDetailContract.View> {

    private NewsDetailContract.Model<NewsListBean.PagebeanBean.ContentlistBean> mModel;

    private NewsDetailContract.View<NewsListBean.PagebeanBean.ContentlistBean> mView;

    public NewsDetailPresenterImpl(NewsDetailContract.View view) {
        this.attachView(view);
        mModel = new NewsDetailModelImpl();
    }

    @Override
    public void gainData() {
        mView.showProgress();
        mModel.loadData(new BaseCallBack<NewsListBean.PagebeanBean.ContentlistBean>() {
            @Override
            public void onSuccess(NewsListBean.PagebeanBean.ContentlistBean data) {
                mView.hideProgress();

                if (data!=null){
                    mView.setupData(data);
                }else {
                    mView.showMessage("数据有误");
                }
            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }

    @Override
    public void attachView(NewsDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}