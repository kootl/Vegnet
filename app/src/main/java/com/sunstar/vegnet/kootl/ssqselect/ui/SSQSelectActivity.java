package com.sunstar.vegnet.kootl.ssqselect.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.ssqselect.helper.SSQAtyHelper;


public class SSQSelectActivity extends BaseActivity<BasePresenter> {

    public static final int SHOW_PROVINCE = 10;
    public static final int SHOW_CITY = 11;
    public static final int SHOW_AREA = 21;

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_ssq_select;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        SSQAtyHelper.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        SSQAtyHelper.removeActivity(this);
    }

    @Override
    protected void begin() {
        //
        int needShowWhere = getBundleExtraInt1();
        if (needShowWhere == 0) {
            needShowWhere = SHOW_PROVINCE;
        }


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_frame_content, SSQSelectFragment.newInstance("", "", needShowWhere));
        fragmentTransaction.commit();
    }

    public void resetToolbarTitle(String titleStr) {
        setToolbarTitle(titleStr);
    }
}
