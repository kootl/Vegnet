package com.sunstar.vegnet.kootl.comm.factory.httprequest.impls;

import com.louisgeek.checkappupdatelib.tool.HttpTool;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.interfaces.IHttpRequestCallback;
import com.sunstar.vegnet.kootl.comm.factory.httprequest.interfaces.IHttpRequestManager;
import com.sunstar.vegnet.kootl.comm.okhttp.ParamsTool;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class SystemHttpRequestManager implements IHttpRequestManager {

    @Override
    public void getUrlBackStr(String url, final IHttpRequestCallback httpRequestCallback) {
        //
        HttpTool.getUrlBackString(url, new HttpTool.OnUrlBackStringCallBack() {
            @Override
            public void onSuccess(String backStr) {
                httpRequestCallback.onSuccess(backStr);
            }

            @Override
            public void onError(String errorMsg) {
                httpRequestCallback.onFailure(errorMsg);
            }
        });
    }

    @Override
    public void postUrlBackStr(String url, Map<String, String> paramsMap, final IHttpRequestCallback httpRequestCallback) {
        //
        HttpTool.postUrlBackString(url, ParamsTool.paramsMapToStr(paramsMap), new HttpTool.OnUrlBackStringCallBack() {
            @Override
            public void onSuccess(String backStr) {
                httpRequestCallback.onSuccess(backStr);
            }

            @Override
            public void onError(String errorMsg) {
                httpRequestCallback.onFailure(errorMsg);
            }
        });
    }

}
