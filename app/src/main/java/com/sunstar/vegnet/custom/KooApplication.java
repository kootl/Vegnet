package com.sunstar.vegnet.custom;

import android.support.v7.app.AppCompatDelegate;

import com.pgyersdk.crash.PgyCrashManager;
import com.socks.library.KLog;
import com.sunstar.baidulbslib.LocationClientHelper;
import com.sunstar.sociallib.config.SocialConfigHelper;
import com.sunstar.vegnet.kootl.comm.base.BaseApplication;
import com.sunstar.vegnet.kootl.comm.helper.DayNightHelper;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by louisgeek on 2016/11/1.
 */

public class KooApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        //全局tag Koo
        KLog.init(mDebug, "Koo");

        /**
         *App 首次就可以加载 x5 内核
         App 在启动后（例如在 Application 的 onCreate 中）立刻调用 QbSdk 的预加载接口 initX5Environment ，可参考接入示例，
         第一个参数传入 context，第二个参数传入 callback，不需要 callback 的可以传入 null，initX5Environment
         内部会创建一个线程向后台查询当前可用内核版本号，这个函数内是异步执行所以不会阻塞 App 主线程，这个函数
         内是轻量级执行所以对 App 启动性能没有影响，当 App 后续创建 webview 时就可以首次加载 x5 内核了
         */
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                KLog.d("QbSdk app onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                KLog.d("QbSdk app onViewInitFinished " + b);
            }
        });
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                KLog.d("QbSdk onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                KLog.d("QbSdk onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                KLog.d("QbSdk onDownloadProgress:" + i);
            }
        });

        /** pgy错误收集
         *
         */
        PgyCrashManager.register(this);

        /**
         *友盟分享
         */
        SocialConfigHelper.initConfig(this, mDebug);

        /**
         * 初始化夜间模式
         */
        if (DayNightHelper.isNight()) {//当前isNight
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        /**
         *百度定位
         */
        LocationClientHelper.initLocationClient(this);

    }


}
