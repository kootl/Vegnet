package com.sunstar.vegnet.kootl.comm.okhttp;

import com.socks.library.KLog;
import com.sunstar.vegnet.data.ApiUrls;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;

/**
 * Created by louisgeek on 2016/12/6.
 */

public class OkHttpHelperSimple {

    private static final String TAG = "OkHttpHelperSimple";
    private Map<String, String> mDefaultHeadersMap;
    private Map<String, String> mCustomHeadersMap;
    private StringBuffer mParamsStringBuffer;
    private Callback mResponseCallback;

    public OkHttpHelperSimple() {
        /**
         * 默认headers
         */
        if (mDefaultHeadersMap == null) {
            mDefaultHeadersMap = new HashMap<>();
        }
        mDefaultHeadersMap.put("userKey", "XXX");
        mDefaultHeadersMap.put("userToken", "XXX");
        /**
         *
         */
        if (mCustomHeadersMap == null) {
            mCustomHeadersMap = new HashMap<>();
        }

        /**
         * 默认params
         */
        Map<String, String> defaultParamsMap = new HashMap<>();
        defaultParamsMap.put("showapi_appid", ApiUrls.SHOWAPI_APPID);
        defaultParamsMap.put("showapi_sign", ApiUrls.SHOWAPI_SIGN_SIMPLE);

        mParamsStringBuffer = new StringBuffer();
        mParamsStringBuffer.append(ParamsTool.paramsMapToStr(defaultParamsMap));
    }

    public OkHttpHelperSimple headers(Map<String, String> headersMap) {
        mCustomHeadersMap.clear();
        mCustomHeadersMap.putAll(headersMap);
        return this;
    }

    public OkHttpHelperSimple headers(String headersStr) {
        return headers(ParamsTool.paramsStrToMap(headersStr));
    }

    public OkHttpHelperSimple params(String params) {
        /**
         * 最后一个是&
         */
        if (mParamsStringBuffer.length() > 0 && mParamsStringBuffer.lastIndexOf("&") == mParamsStringBuffer.length() - 1) {
            /* do nothing */
        } else {
            mParamsStringBuffer.append("&");
        }
        mParamsStringBuffer.append(params);
        KLog.d("params:" + mParamsStringBuffer.toString());
        return this;
    }

    public OkHttpHelperSimple params(Map<String, String> paramsMap) {
        return params(ParamsTool.paramsMapToStr(paramsMap));
    }

    public OkHttpHelperSimple callback(Callback responseCallback) {
        mResponseCallback = responseCallback;
        return this;
    }

    public void doGetUrl(String webUrl) {
        KLog.d("url:" + webUrl);
        OkHttpSingleton.getInstance().doGet(webUrl, mDefaultHeadersMap, mCustomHeadersMap, mResponseCallback);
    }

    public void doPostUrl(String webUrl) {
        KLog.d("url:" + webUrl);
        OkHttpSingleton.getInstance().doPost(webUrl, mDefaultHeadersMap, mCustomHeadersMap, mParamsStringBuffer.toString(), mResponseCallback);
    }
}
