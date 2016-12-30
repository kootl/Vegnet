package com.sunstar.vegnet.kootl.comm.factory.httprequest.impls;

import com.sunstar.vegnet.kootl.comm.factory.httprequest.interfaces.IHttpRequestCallback;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.interfaces.IHttpRequestManager;
import com.sunstar.vegnet.kootl.comm.okhttp.OkHttpHelper;
import com.sunstar.vegnet.kootl.comm.okhttp.StringOkHttpCallback;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class OKHttpRequestManager implements IHttpRequestManager {

    @Override
    public void getUrlBackStr(String url, final IHttpRequestCallback httpRequestCallback) {
        //
        new OkHttpHelper.Builder().callback(new StringOkHttpCallback() {
            @Override
            public void OnSuccess(String result, int statusCode) {
                httpRequestCallback.onSuccess(result);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                httpRequestCallback.onFailure(errorMsg);
            }
        }).build().doGetUrl(url);
    }

    @Override
    public void postUrlBackStr(String url, Map<String, String> paramsMap, final IHttpRequestCallback httpRequestCallback) {
        //
        new OkHttpHelper.Builder().params(paramsMap).callback(new StringOkHttpCallback() {
            @Override
            public void OnSuccess(String result, int statusCode) {
                httpRequestCallback.onSuccess(result);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                httpRequestCallback.onFailure(errorMsg);
            }
        }).build().doPostUrl(url);
    }
}
