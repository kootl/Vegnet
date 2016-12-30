package com.sunstar.vegnet.wxapi;


import android.content.Intent;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/20.
 */
//完整版路径是com.umeng.socialize.weixin.view.WXCallbackActivity)

public class WXEntryActivity extends WXCallbackActivity {


    /**
     * 微信授权防杀死
     * 对于的低端手机可能会有如下问题，从开发者app调到qq或者微信的授权界面，后台把开发者app杀死了，这样，
     * 授权成功没有回调了，可以用如下方式避免：（一般不需要添加，如有特殊需要，可以添加）
     *
     * @param intent
     */
    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        mWxHandler.setAuthListener(new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Log.e("UMWXHandler onComplete");
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        });

    }
}
