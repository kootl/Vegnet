package com.sunstar.sociallib.config;

import android.content.Context;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;

/**
 * Created by louisgeek on 2016/12/20.
 */

public class SocialConfigHelper {
    //在项目 App onCreate 时调用
    public static void initConfig(Context context, boolean isDebug) {
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = isDebug;
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";//后台设置的回调
        Log.LOG = false;//日志开关
        //
        /**
         * 对应平台没有安装的时候跳转转到应用商店下载，其中qq 微信会跳转到下载界面进行下载，其他应用会跳到应用商店进行下载
         */
        // Config.isJumptoAppStore = true;


        PlatformConfig.setWeixin(CommFinalData.weixin_app_id, CommFinalData.weixin_app_secret);
        PlatformConfig.setSinaWeibo(CommFinalData.weibo_app_key, CommFinalData.weibo_app_secret);
        PlatformConfig.setQQZone(CommFinalData.tencent_app_id, CommFinalData.tencent_app_key);
        UMShareAPI.get(context);
    }
}
