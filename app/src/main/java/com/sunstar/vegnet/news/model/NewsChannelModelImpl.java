package com.sunstar.vegnet.news.model;

import com.sunstar.vegnet.data.ApiUrls;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.HttpRequestManagerFactory;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.vegnet.news.bean.NewsChannelBean;
import com.sunstar.vegnet.news.contract.NewsChannelContract;

/**
 * Created by louisgeek on 2016/12/07
 */

public class NewsChannelModelImpl implements NewsChannelContract.Model<BaseBeanShowApi<NewsChannelBean>> {

    @Override
    public void loadData(final BaseCallBack<BaseBeanShowApi<NewsChannelBean>> baseCallBack) {
       /* new OkHttpHelper.Builder().callback(new GsonOkHttpCallback<BaseBeanShowApi<NewsChannelBean>>() {


            @Override
            public BaseBeanShowApi<NewsChannelBean> OnSuccess(String result, int statusCode) {
                return BaseBeanShowApi.fromJson(result,NewsChannelBean.class);
            }

            @Override
            public void OnSuccessOnUI(BaseBeanShowApi<NewsChannelBean> baseBean, int statusCode) {
                baseCallBack.onSuccess(baseBean);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                baseCallBack.onError(errorMsg);
            }
        }).build().doPostUrl(ApiUrls.newsChannelUrl);*/

        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(ApiUrls.newsChannelUrl,
                null, new GsonHttpRequestCallback<BaseBeanShowApi<NewsChannelBean>>() {
                    @Override
                    public BaseBeanShowApi<NewsChannelBean> OnSuccess(String result) {
                        return BaseBeanShowApi.fromJson(result, NewsChannelBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(BaseBeanShowApi<NewsChannelBean> baseBean) {
                        baseCallBack.onSuccess(baseBean);
                    }

                    @Override
                    public void OnError(String errorMsg) {
                        baseCallBack.onError(errorMsg);
                    }
                });


    }
}