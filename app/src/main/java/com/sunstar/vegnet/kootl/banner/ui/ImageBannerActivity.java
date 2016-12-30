package com.sunstar.vegnet.kootl.banner.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.data.ImagesDatas;
import com.sunstar.vegnet.kootl.banner.adapter.ImageBannerPageAdapter;
import com.sunstar.vegnet.kootl.banner.bean.ImageBannerBean;
import com.sunstar.vegnet.kootl.banner.widget.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * banner demo
 */
public class ImageBannerActivity extends AppCompatActivity {
    List<ImageBannerBean> mImageBannerBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_banner);


        for (int i = 0; i < ImagesDatas.imageThumbUrls.length; i++) {
            if (i > 4) {
                break;
            }
            ImageBannerBean imageBannerBean = new ImageBannerBean();
            imageBannerBean.setImageUrl(ImagesDatas.imageThumbUrls[i]);
            imageBannerBean.setImageText("图片xx" + i);
            mImageBannerBeanList.add(imageBannerBean);
        }


        initBannerViewPager();
    }

    BannerViewPager mBannerViewPager;

    private void initBannerViewPager() {

        mBannerViewPager = (BannerViewPager) findViewById(R.id.id_banner_view_pager);
        View bannerBottomView = findViewById(R.id.id_rl_banner_bottom);
        ImageBannerPageAdapter pagerAdapter = new ImageBannerPageAdapter(mImageBannerBeanList,
                mBannerViewPager, bannerBottomView,true);
        mBannerViewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerViewPager.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerViewPager.stopAutoPlay();
    }
}
