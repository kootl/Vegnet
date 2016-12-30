package com.sunstar.vegnet.news.model;

import com.sunstar.vegnet.data.ApiUrls;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.HttpRequestManagerFactory;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.contract.NewsListContract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisgeek on 2016/12/06
 */

public class NewsListModelImpl implements NewsListContract.Model<BaseBeanShowApi<NewsListBean>> {

    @Override
    public void loadData(final BaseCallBack<BaseBeanShowApi<NewsListBean>> baseCallBack) {

        final Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channelId", "5572a108b3cdc86cf39001cd");//国内
        paramMap.put("page", "1");
        paramMap.put("needHtml", "1");
        paramMap.put("needContent", "1");
        paramMap.put("needAllList", "0");//有问题 先不要1

        /*new OkHttpHelper.Builder().params(paramMap).callback(new GsonOkHttpCallback<BaseBeanShowApi<NewsListBean>>() {
            @Override
            public BaseBeanShowApi<NewsListBean> OnSuccess(String result, int statusCode) {
                // KLog.d("result:"+result);
                return BaseBeanShowApi.fromJson(result, NewsListBean.class);
            }

            @Override
            public void OnSuccessOnUI(BaseBeanShowApi<NewsListBean> baseBean, int statusCode) {
                baseCallBack.onSuccess(baseBean);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                baseCallBack.onError(errorMsg);
            }
        }).build().doPostUrl(ApiUrls.newsListUrl);*/

        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(ApiUrls.newsListUrl, paramMap, new GsonHttpRequestCallback<BaseBeanShowApi<NewsListBean>>() {
            @Override
            public BaseBeanShowApi<NewsListBean> OnSuccess(String result) {
                return BaseBeanShowApi.fromJson(result, NewsListBean.class);
            }

            @Override
            public void OnSuccessOnUI(BaseBeanShowApi<NewsListBean> baseBean) {
                baseCallBack.onSuccess(baseBean);
            }

            @Override
            public void OnError(String errorMsg) {
                baseCallBack.onError(errorMsg);
            }
        });
    }


    @Override
    public void loadMoreData(int page, final BaseCallBack<BaseBeanShowApi<NewsListBean>> baseCallBack) {
        final Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channelId", "5572a108b3cdc86cf39001cd");//国内
        paramMap.put("page", String.valueOf(page));
        paramMap.put("needHtml", "1");
        paramMap.put("needContent", "1");
        paramMap.put("needAllList", "0");//有问题 先不要1

        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(ApiUrls.newsListUrl, paramMap, new GsonHttpRequestCallback<BaseBeanShowApi<NewsListBean>>() {
            @Override
            public BaseBeanShowApi<NewsListBean> OnSuccess(String result) {
                return BaseBeanShowApi.fromJson(result, NewsListBean.class);
            }

            @Override
            public void OnSuccessOnUI(BaseBeanShowApi<NewsListBean> baseBean) {
                baseCallBack.onSuccess(baseBean);
            }

            @Override
            public void OnError(String errorMsg) {
                baseCallBack.onError(errorMsg);
            }
        });
    }
}