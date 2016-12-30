package com.sunstar.vegnet.kootl.usercenter.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;

public class UserCenterActivity extends BaseActivity<BasePresenter> {

    public static final int SHOW_REGISTER = 100;
    public static final int SHOW_FIND_PASSWORD = 101;
    public static final int SHOW_FIND_ACCOUNT = 102;

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_user_center;
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int goToWhere = getBundleExtraInt1();
        switch (goToWhere) {
            case SHOW_REGISTER:
                setToolbarTitle(R.string.nav_title_userregister);
                fragmentTransaction.replace(R.id.id_frame_content, UserRegisterFragment.newInstance("", ""));
                break;
            case SHOW_FIND_PASSWORD:
                setToolbarTitle(R.string.nav_title_findpassword);
                fragmentTransaction.replace(R.id.id_frame_content, FindPasswordFragment.newInstance("", ""));
                break;
            case SHOW_FIND_ACCOUNT:
                setToolbarTitle(R.string.nav_title_findaccount);
                fragmentTransaction.replace(R.id.id_frame_content, FindAccountFragment.newInstance("", ""));
                break;
        }
        fragmentTransaction.commit();

    }




}
