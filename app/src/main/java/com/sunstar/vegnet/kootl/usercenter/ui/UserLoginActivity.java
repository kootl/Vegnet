package com.sunstar.vegnet.kootl.usercenter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.listener.OnNotFastClickListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.social.AuthHelper;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.PasswordToggleViewTool;
import com.sunstar.vegnet.kootl.usercenter.bean.BaseListBean;
import com.sunstar.vegnet.kootl.usercenter.contract.UserLoginContract;
import com.sunstar.vegnet.kootl.usercenter.presenter.UserLoginPresenterImpl;
import com.sunstar.vegnet.ui.StartActivity;


public class UserLoginActivity extends BaseActivity<UserLoginContract.Presenter> implements UserLoginContract.View<BaseListBean> {


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_user_login;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected UserLoginContract.Presenter setupPresenter() {
        return mPresenter = new UserLoginPresenterImpl(this);
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
        setToolbarTitle(R.string.nav_title_userlogin);

        /**
         * fix
         */
        fixPasswordToggleViewInCenter();

        setClickListener(findById(R.id.id_btn_login), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.gainData();
            }
        });


        TextView id_tv_find_pd = findById(R.id.id_tv_find_pd);
        TextView id_tv_find_account = findById(R.id.id_tv_find_account);
        Button id_btn_reg = findById(R.id.id_btn_reg);
        ImageView id_iv_weixin = findById(R.id.id_iv_weixin);
        ImageView id_iv_qq = findById(R.id.id_iv_qq);
        ImageView id_iv_weibo = findById(R.id.id_iv_weibo);
        id_tv_find_pd.setOnClickListener(mOnNotFastClickListener);
        id_btn_reg.setOnClickListener(mOnNotFastClickListener);
        id_tv_find_account.setOnClickListener(mOnNotFastClickListener);
        id_iv_weixin.setOnClickListener(mOnNotFastClickListener);
        id_iv_qq.setOnClickListener(mOnNotFastClickListener);
        id_iv_weibo.setOnClickListener(mOnNotFastClickListener);

    }

    private OnNotFastClickListener mOnNotFastClickListener = new OnNotFastClickListener() {
        @Override
        protected void onNotFastClick(View v) {
            switch (v.getId()) {
                case R.id.id_tv_find_pd:
                    //showToastShort("id_tv_find_pd");
                    Bundle bundlePd = createBundleExtraInt1(UserCenterActivity.SHOW_FIND_PASSWORD);
                    UserCenterActivity.startMe(mContext, UserCenterActivity.class, bundlePd);
                    break;
                case R.id.id_tv_find_account:
                    //showToastShort("id_tv_find_account");
                    Bundle bundleFa = createBundleExtraInt1(UserCenterActivity.SHOW_FIND_ACCOUNT);
                    UserCenterActivity.startMe(mContext, UserCenterActivity.class, bundleFa);
                    break;
                case R.id.id_btn_reg:
                    // showToastShort("id_btn_reg");
                    Bundle bundleReg = createBundleExtraInt1(UserCenterActivity.SHOW_REGISTER);
                    UserCenterActivity.startMe(mContext, UserCenterActivity.class, bundleReg);
                    //finishAtyAfterTransition();
                    break;
                case R.id.id_iv_weixin:
                    // showToastShort("id_ib_weixin");
                    AuthHelper.weixinLogin(UserLoginActivity.this);
                    break;
                case R.id.id_iv_qq:
                    // showToastShort("id_ib_qq");
                    AuthHelper.qqLogin(UserLoginActivity.this);
                    break;
                case R.id.id_iv_weibo:
                    // showToastShort("id_ib_weibo");
                    AuthHelper.weiboLogin(UserLoginActivity.this);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        AuthHelper.callAtOnActivityResult(this, requestCode, resultCode, data);
    }

    private void fixPasswordToggleViewInCenter() {
        TextInputLayout id_til_password = findById(R.id.id_til_password);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_password);
    }

    @Override
    public String getUserName() {
        EditText et = findById(R.id.id_et_username);
        return et.getText().toString();
    }

    @Override
    public String getPassword() {
        EditText et = findById(R.id.id_et_password);
        return et.getText().toString();
    }

    @Override
    public void loginSuccess() {
        /**
         * 成功
         */
        // showMessageDialog(data.getMessage());
        // startAty(StartActivity.class);
        StartActivity.startMe(mContext, StartActivity.class);
    }


    @Override
    public void showProgress() {
        showSmileLoadingDialog("登录中...");
    }

    @Override
    public void hideProgress() {
        hideLoadingDialog();
    }

    @Override
    public void showNoDataNotice() {

    }

    @Override
    public void hideNoDataNotice() {

    }

    @Override
    public void showMessage(String msg) {
        showMessageDialog(msg);
    }

    @Override
    public void setupData(BaseListBean data) {

    }
}
