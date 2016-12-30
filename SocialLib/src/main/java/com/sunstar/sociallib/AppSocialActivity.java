package com.sunstar.sociallib;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sunstar.sociallib.config.CommFinalData;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;

@Deprecated /** demo */
public class AppSocialActivity extends AppCompatActivity implements View.OnClickListener {
    private ShareAction mShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);


        Button idBtnBottom1 = (Button) findViewById(R.id.id_btn_bottom_1);
        Button idBtnCenter1 = (Button) findViewById(R.id.id_btn_center_1);
        Button idBtnBottom2 = (Button) findViewById(R.id.id_btn_bottom_2);
        Button idBtnCenter2 = (Button) findViewById(R.id.id_btn_center_2);

        idBtnBottom1.setOnClickListener(this);
        idBtnCenter1.setOnClickListener(this);
        idBtnBottom2.setOnClickListener(this);
        idBtnCenter2.setOnClickListener(this);

        //
         /*无自定按钮的分享面板*/
        mShareAction = new ShareAction(this).setDisplayList(
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
                .withMedia(new UMImage(this,"https://www.sogou.com/images/baidu/baiduicon.gif"))
                .setCallback(mUMShareListener);
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(AppSocialActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else if (platform != SHARE_MEDIA.MORE
                    && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL) {
                Toast.makeText(AppSocialActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(AppSocialActivity.this, platform + " 收藏失败啦", Toast.LENGTH_SHORT).show();
            } else if (platform != SHARE_MEDIA.MORE
                    && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL) {
                Toast.makeText(AppSocialActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AppSocialActivity.this, platform + " 操作取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        //
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, dataIntent);

    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_btn_bottom_1) {
            //mShareAction.open();//default
            ShareBoardConfig config = new ShareBoardConfig();
            config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
            config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE);
            config.setTitleVisibility(false); // 隐藏title
            config.setCancelButtonVisibility(false); // 隐藏取消按钮
            mShareAction.open(config);
        } else if (v.getId() == R.id.id_btn_center_1) {
            ShareBoardConfig config = new ShareBoardConfig();
            config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
            config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
            mShareAction.open(config);
        } else if (v.getId() == R.id.id_btn_bottom_2) {
            ShareBoardConfig config = new ShareBoardConfig();
            config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
            config.setTitleVisibility(false); // 隐藏title
            config.setCancelButtonVisibility(false); // 隐藏取消按钮
            mShareAction.open(config);
        } else if (v.getId() == R.id.id_btn_center_2) {
            ShareBoardConfig config = new ShareBoardConfig();
            config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
            config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
            mShareAction.open(config);
        }
    }
}
