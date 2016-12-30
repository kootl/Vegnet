package com.sunstar.sociallib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated /** demo */
public class AppAuthActivity extends AppCompatActivity {

    public List<SnsPlatform> mSnsPlatformList = new ArrayList<>();
    private SHARE_MEDIA[] mShareMediaArray = {
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA
           /* ,SHARE_MEDIA.FACEBOOK,SHARE_MEDIA.TWITTER,SHARE_MEDIA.LINKEDIN,
            SHARE_MEDIA.DOUBAN,SHARE_MEDIA.RENREN,SHARE_MEDIA.KAKAO*/
    };
    private int[] mButtonResIDArray = {R.id.id_btn_weixin, R.id.id_btn_qq, R.id.id_btn_weibo};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        initPlatforms();
    }

    private void initPlatforms() {
        mSnsPlatformList.clear();
        for (SHARE_MEDIA share_media : mShareMediaArray) {
            if (!share_media.toString().equals(SHARE_MEDIA.GENERIC.toString())) {
                mSnsPlatformList.add(share_media.toSnsPlatform());
            }
        }
        /**
         *
         */
        for (int i = 0; i < mSnsPlatformList.size(); i++) {
            final boolean isAuth = UMShareAPI.get(this).isAuthorize(this, mSnsPlatformList.get(i).mPlatform);
            int iconResId = ResContainer.getResourceId(this, "drawable", mSnsPlatformList.get(i).mIcon);
            int strResId = ResContainer.getResourceId(this, "string", mSnsPlatformList.get(i).mShowWord);

            Button idBtn = (Button) findViewById(mButtonResIDArray[i]);

            final int finalI = i;
            idBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAuth) {
                        UMShareAPI.get(AppAuthActivity.this).deleteOauth(AppAuthActivity.this, mSnsPlatformList.get(finalI).mPlatform, mUMAuthListener);
                    } else {
                        UMShareAPI.get(AppAuthActivity.this).doOauthVerify(AppAuthActivity.this, mSnsPlatformList.get(finalI).mPlatform, mUMAuthListener);
                    }
                }
            });


        }
    }


    private UMAuthListener mUMAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(AppAuthActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(AppAuthActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(AppAuthActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
