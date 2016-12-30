package com.sunstar.vegnet.kootl.comm.social;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.socks.library.KLog;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/21.
 * SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA
 */

public class AuthHelper {
    private static UMAuthListener mUMAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data == null) {
                KLog.d("onComplete  onComplete  data==null");
                return;
            }
            for (String key : data.keySet()) {
                String value = data.get(key);
                KLog.d(platform + " key:" + key + " value:" + value);
            }
            //Toast.makeText(KooApplication.getAppContext(), "成功了", Toast.LENGTH_LONG).show();
            ToastTool.showImageOk("登录成功！");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastTool.showImageWarn("登录失败！");
            //Toast.makeText(KooApplication.getAppContext(), "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //Toast.makeText(KooApplication.getAppContext(), "取消了", Toast.LENGTH_LONG).show();

        }
    };
    private static ProgressDialog mProgressDialog;

    private static void loginBase(final SnsPlatform snsPlatform, final Activity activity) {
        /**
         * 自定义dialog
         */
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage("准备中...");
        //
        Config.dialog = mProgressDialog;
        Config.wxdialog = mProgressDialog;

        final boolean isAuth = UMShareAPI.get(activity).isAuthorize(activity, snsPlatform.mPlatform);
        int iconResId = ResContainer.getResourceId(activity, "drawable", snsPlatform.mIcon);
        int strResId = ResContainer.getResourceId(activity, "string", snsPlatform.mShowWord);


              /* ############# if (isAuth) {
                    UMShareAPI.get(activity).deleteOauth(activity, snsPlatform.mPlatform, mUMAuthListener);
                } else {*/
        UMShareAPI.get(activity).doOauthVerify(activity, snsPlatform.mPlatform, mUMAuthListener);
        // }

    }

    public static void qqLogin(Activity activity) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            SnsPlatform snsPlatform = SHARE_MEDIA.QQ.toSnsPlatform();
            loginBase(snsPlatform, activity);
        } else {
            ToastTool.showImageInfo("未安装QQ客户端!");
        }
    }

    public static void weixinLogin(Activity activity) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            SnsPlatform snsPlatform = SHARE_MEDIA.WEIXIN.toSnsPlatform();
            loginBase(snsPlatform, activity);
        } else {
            ToastTool.showImageInfo("未安装微信客户端!");
        }
    }

    public static void weiboLogin(Activity activity) {
        SnsPlatform snsPlatform = SHARE_MEDIA.SINA.toSnsPlatform();
        loginBase(snsPlatform, activity);
    }


    public static void callAtOnActivityResult(Activity activity, int requestCode, int resultCode, Intent dataIntent) {
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, dataIntent);
        /**
         * qq授权防杀死
         对于的低端手机可能会有如下问题，从开发者app调到qq或者微信的授权界面，后台把开发者app杀死了，这样，授权成功没有回调了，
         可以用如下方式避免：（一般不需要添加，如有特殊需要，可以添加）
         */
        UMShareAPI.get(activity).HandleQQError(activity, requestCode, mUMAuthListener);
    }
}
