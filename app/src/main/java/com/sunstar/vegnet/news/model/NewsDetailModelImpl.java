package com.sunstar.vegnet.news.model;


import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.contract.NewsDetailContract;

/**
 * Created by louisgeek on 2016/12/04
 */

public class NewsDetailModelImpl implements NewsDetailContract.Model<NewsListBean.PagebeanBean.ContentlistBean> {


    @Override
    public void loadData(BaseCallBack<NewsListBean.PagebeanBean.ContentlistBean> baseCallBack) {
        NewsListBean.PagebeanBean.ContentlistBean contentlistBean = (NewsListBean.PagebeanBean.ContentlistBean) DataHolderSingleton.getInstance().getData("contentlistBean");
        if (contentlistBean != null) {
            baseCallBack.onSuccess(contentlistBean);
        } else {
            baseCallBack.onError("contentlistBean is null");
        }
    }
}