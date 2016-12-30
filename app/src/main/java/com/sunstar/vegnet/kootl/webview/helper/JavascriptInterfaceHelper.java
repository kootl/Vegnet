package com.sunstar.vegnet.kootl.webview.helper;

import android.content.Context;

import com.socks.library.KLog;
import com.sunstar.vegnet.kootl.imageshow.helper.ImageShowDataHelper;

/**
 * Created by louisgeek on 2016/11/14.
 */

public class JavascriptInterfaceHelper {

    public JavascriptInterfaceHelper(Context context) {
        this.context = context;
    }

    private Context context;

    /**
     * 必须添加 @android.webkit.JavascriptInterface注解
     * @param imgUrl
     */
    @android.webkit.JavascriptInterface
    public void jsWillCallJavaFun_openImageShow(String imgUrl) {
        KLog.d("jsWillCallJavaFun_openImageShow:"+imgUrl);
        ImageShowDataHelper.setDataAndToImageShow(context, imgUrl);
    }
}
