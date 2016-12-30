package com.sunstar.vegnet.kootl.changepd;

import android.support.design.widget.TextInputLayout;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.PasswordToggleViewTool;

public class ChangePasswordActivity extends BaseActivity<BasePresenter> {


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_change_password;
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

        setToolbarTitle(R.string.nav_title_changepd);

        /**
         * fix
         */
        fixPasswordToggleViewInCenter();

    }

    private void fixPasswordToggleViewInCenter() {
        TextInputLayout id_til_old_password = findById(R.id.id_til_old_password);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_old_password);
        TextInputLayout id_til_password = findById(R.id.id_til_password);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_password);
        TextInputLayout id_til_password_again = findById(R.id.id_til_password_again);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_password_again);
    }
}
