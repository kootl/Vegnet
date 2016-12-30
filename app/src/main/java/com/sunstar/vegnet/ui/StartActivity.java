package com.sunstar.vegnet.ui;


import android.os.Handler;
import android.widget.ImageView;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.SharedPreferencesTool;
import com.sunstar.vegnet.kootl.guide.ui.GuideActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class StartActivity extends BaseActivity<BasePresenter> {


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected int setupToolbarResId() {
        return 0;
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
        return true;
    }

    @Override
    protected boolean setupToolbarTitleCenter() {
        return false;
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
        return -1;
    }

    @Override
    protected void begin() {
        ImageView id_iv_start_image = findById(R.id.id_iv_start_image);
       // GlideHelper.displayImageRes(id_iv_start_image, R.mipmap.welcome);
        ImageLoaderFactory.getManager().displayImage(id_iv_start_image, R.mipmap.welcome);

        delayJump(1000, true);
    }


    /**
     * 延迟x毫秒进入
     */
    private void delayJump(long delayMilliseconds, boolean useRx) {
        if (useRx) {
            Observable.timer(delayMilliseconds, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    nextWhere();
                }
            });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextWhere();
                }
            }, delayMilliseconds);
        }
    }


    private void goToGuide() {
        //startAty(GuideActivity.class);
        GuideActivity.startMe(mContext, GuideActivity.class);
        //###finish();
        finishAtyAfterTransition();
    }

    private void goToMain() {
        MainActivity.startMe(mContext, MainActivity.class);
        //###finish();
        finishAtyAfterTransition();
    }

    private void nextWhere() {
        /**
         *
         */
        boolean hasOpenedGuide = (boolean) SharedPreferencesTool.get(mContext,
                CommFinalData.SP_HAS_OPENED_GUIDE, false);
        KLog.d("hasOpenedGuide DD:" + hasOpenedGuide);
        if (hasOpenedGuide) {
            goToMain();
        } else {
            goToGuide();
        }


    }


}
