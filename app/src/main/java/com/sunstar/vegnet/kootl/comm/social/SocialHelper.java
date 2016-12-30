package com.sunstar.vegnet.kootl.comm.social;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.sunstar.sociallib.config.CommFinalData;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;

/**
 * Created by louisgeek on 2016/12/21.
 */

public class SocialHelper {
    private static final int SOCIAL_SHOW_FROM_BOTTOM = 11;
    private static final int SOCIAL_SHOW_FROM_CENTER = 22;
    private static final int SOCIAL_SHOW_FROM_BOTTOM_BIG_ICON = 33;
    private static final int SOCIAL_SHOW_FROM_CENTER_BIG_ICON = 44;
    private static ShareAction mShareAction;
    // private static Context mContext;
    private static UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                //Toast.makeText(KooApplication.getAppContext(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
                //ToastTool.showImageOk("收藏成功！");
                ToastTool.showShort("收藏成功！");
            } else if (platform != SHARE_MEDIA.MORE
                    && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                //&& platform != SHARE_MEDIA.SINA
                    ) {
                // Toast.makeText(KooApplication.getAppContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                // ToastTool.showShort("分享成功！");
                ToastTool.showImageOk("分享成功！");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            String message = "";
            if (t != null) {
                message = t.getMessage();
                Log.d("zfq", "zfq:" + t.getMessage());
            }
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                // Toast.makeText(KooApplication.getAppContext(), platform + " 收藏失败啦", Toast.LENGTH_SHORT).show();
                ToastTool.showImageWarn("收藏失败！");
            } else if (platform != SHARE_MEDIA.MORE
                    && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL) {
                /**
                 * 错误码：2008 错误信息：没有安装应用
                 */
                if (message.contains("错误码")
                        && message.contains("2008")) {
                    if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE || platform == SHARE_MEDIA.WEIXIN_FAVORITE) {
                        ToastTool.showImageWarn("未安装微信客户端！");
                    } else if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
                        ToastTool.showImageWarn("未安装QQ客户端！");
                    } else {
                        ToastTool.showImageWarn("未安装" + platform + "客户端！");
                    }
                } else {
                    //ToastTool.showImageWarn(platform + "分享失败！");
                    ToastTool.showImageWarn("分享失败！");
                }


            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //Toast.makeText(KooApplication.getAppContext(), platform + " 操作取消了", Toast.LENGTH_SHORT).show();
        }
    };
    public static ProgressDialog mProgressDialog;
    public static void initConfig(Activity activity) {
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
        /**
         * 初始化umeng config
         */
        //
            /*无自定按钮的分享面板*/
        mShareAction = new ShareAction(activity).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                //SHARE_MEDIA.ALIPAY, SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.SMS,
         /*       SHARE_MEDIA.EMAIL, SHARE_MEDIA.YNOTE,
                SHARE_MEDIA.EVERNOTE, SHARE_MEDIA.LAIWANG, SHARE_MEDIA.LAIWANG_DYNAMIC,
                SHARE_MEDIA.LINKEDIN, SHARE_MEDIA.YIXIN, SHARE_MEDIA.YIXIN_CIRCLE,
                SHARE_MEDIA.TENCENT, SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.TWITTER,
                SHARE_MEDIA.WHATSAPP, SHARE_MEDIA.GOOGLEPLUS, SHARE_MEDIA.LINE,
                SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.KAKAO, SHARE_MEDIA.PINTEREST,
                SHARE_MEDIA.POCKET, SHARE_MEDIA.TUMBLR, SHARE_MEDIA.FLICKR,
                SHARE_MEDIA.FOURSQUARE, */
                SHARE_MEDIA.MORE)
                .withTitle(CommFinalData.shareTitle)
                .withText(CommFinalData.shareText)
                .withTargetUrl(CommFinalData.shareUrl)
                .withMedia(new UMImage(activity, "https://www.sogou.com/images/baidu/baiduicon.gif"))
                .setCallback(mUMShareListener);
    }

    public static void callAtOnActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        UMShareAPI.get(KooApplication.getAppContext()).onActivityResult(requestCode, resultCode, dataIntent);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    public static void callAtOnConfigurationChanged() {
        mShareAction.close();
    }

    public static void openShareView() {
        openShareView(SOCIAL_SHOW_FROM_BOTTOM);
    }

    public static void openShareView(int socialShowStyle) {
        ShareBoardConfig config;
        switch (socialShowStyle) {
            case SOCIAL_SHOW_FROM_BOTTOM:
                //mShareAction.open();//default
                config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE);
                //config.setTitleVisibility(false); // 隐藏title
                config.setCancelButtonVisibility(false); // 隐藏取消按钮
                config.setIndicatorColor(Color.parseColor("#E9EFF2"), Color.parseColor("#E9EFF2"));//
                mShareAction.open(config);
                break;
            case SOCIAL_SHOW_FROM_BOTTOM_BIG_ICON:
                config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
                mShareAction.open(config);
                break;
            case SOCIAL_SHOW_FROM_CENTER:
                config = new ShareBoardConfig();
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                config.setTitleVisibility(false); // 隐藏title
                config.setCancelButtonVisibility(false); // 隐藏取消按钮
                mShareAction.open(config);
                break;
            case SOCIAL_SHOW_FROM_CENTER_BIG_ICON:
                config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                mShareAction.open(config);
                break;
        }

    }

}
