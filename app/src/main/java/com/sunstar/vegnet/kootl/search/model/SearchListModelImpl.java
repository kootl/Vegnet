package com.sunstar.vegnet.kootl.search.model;

import com.sunstar.vegnet.data.ApiUrls;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.okhttp.GsonOkHttpCallback;
import com.sunstar.vegnet.kootl.comm.okhttp.OkHttpHelper;
import com.sunstar.vegnet.kootl.search.bean.SearchListBean;
import com.sunstar.vegnet.kootl.search.contract.SearchListContract;

import java.util.HashMap;
import java.util.Map;

/**
* Created by louisgeek on 2016/12/12
*/

public class SearchListModelImpl implements SearchListContract.Model<BaseBeanShowApi<SearchListBean>>{

    @Override
    public void loadData(BaseCallBack<BaseBeanShowApi<SearchListBean>> baseCallBack) {

    }

    @Override
    public void queryValue(String key, final BaseCallBack<BaseBeanShowApi<SearchListBean>> baseCallBack) {
        final Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channelId", "5572a108b3cdc86cf39001cd");//国内
        paramMap.put("page", "1");
        paramMap.put("needHtml", "1");
        paramMap.put("needContent", "1");
        paramMap.put("needAllList", "0");//有问题 先不要1
        paramMap.put("title", key);

        new OkHttpHelper.Builder().params(paramMap).callback(new GsonOkHttpCallback<BaseBeanShowApi<SearchListBean>>() {
            @Override
            public BaseBeanShowApi<SearchListBean> OnSuccess(String result, int statusCode) {
                // KLog.d("result:"+result);
                return BaseBeanShowApi.fromJson(result, SearchListBean.class);
            }

            @Override
            public void OnSuccessOnUI(BaseBeanShowApi<SearchListBean> baseBean, int statusCode) {
                baseCallBack.onSuccess(baseBean);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                baseCallBack.onError(errorMsg);
            }
        }).build().doPostUrl(ApiUrls.newsListUrl);
    }
}