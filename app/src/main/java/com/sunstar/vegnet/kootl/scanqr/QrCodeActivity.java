package com.sunstar.vegnet.kootl.scanqr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.ImageView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.SizeTool;
import com.sunstar.vegnet.kootl.comm.tool.ThreadTool;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class QrCodeActivity extends BaseActivity<BasePresenter> {


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected BasePresenter setupPresenter() {
        return null;
    }

    @Override
    protected TabHostShowDataInfo setupTabHostDataInfo() {
        return null;
    }

    @Override
    protected boolean notNeedSwipeBack() {
        return false;
    }

    @Override
    protected boolean setupToolbarTitleCenter() {
        return true;
    }

    @Override
    protected boolean setupHideHomeAsUp() {
        return false;
    }

    @Override
    protected int setupCollapsingToolbarLayoutResId() {
        return 0;
    }

    @Override
    protected int setupSwipeRefreshLayoutResId() {
        return 0;
    }

    @Override
    protected int setupRecyclerViewResId() {
        return 0;
    }

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        return null;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected int setupMenuResId() {
        return 0;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    @Override
    protected void begin() {
        setToolbarTitle(R.string.nav_title_about);

        final ImageView id_iv_qr_code = (ImageView) findViewById(R.id.id_iv_qr_code);


        final Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap QRBitmap = QRCodeEncoder.syncEncodeQRCode("朱方佺louisgeek",
                        SizeTool.dp2px(mContext, 200), Color.BLACK, Color.WHITE, logoBitmap);

                ThreadTool.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        id_iv_qr_code.setImageBitmap(QRBitmap);
                    }
                });
            }
        }).start();

    }
}
