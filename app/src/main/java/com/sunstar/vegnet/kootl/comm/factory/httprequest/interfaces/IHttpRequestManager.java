package com.sunstar.vegnet.kootl.comm.factory.httprequest.interfaces;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/28.
 */

public interface IHttpRequestManager {
    void getUrlBackStr(String url, IHttpRequestCallback httpRequestCallback);

    void postUrlBackStr(String url, Map<String, String> paramsMap, IHttpRequestCallback httpRequestCallback);


}
