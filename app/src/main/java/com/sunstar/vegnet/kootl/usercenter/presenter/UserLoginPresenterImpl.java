package com.sunstar.vegnet.kootl.usercenter.presenter;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.usercenter.bean.BaseListBean;
import com.sunstar.vegnet.kootl.usercenter.contract.UserLoginContract;
import com.sunstar.vegnet.kootl.usercenter.model.UserLoginModelImpl;

/**
 * Created by louisgeek on 2016/12/09
 */

public class UserLoginPresenterImpl implements UserLoginContract.Presenter<UserLoginContract.View> {


    private UserLoginContract.Model mModel;
    private UserLoginContract.View mView;

    public UserLoginPresenterImpl(UserLoginContract.View view) {
        this.attachView(view);
        mModel = new UserLoginModelImpl();
    }

    @Override
    public void attachView(UserLoginContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {
        String username = mView.getUserName();
        String password = mView.getPassword();
        if (username == null || ("").equals(username)) {
            mView.showMessage("用户有误");
            return;
        }
        if (password == null || ("").equals(password)) {
            mView.showMessage("密码有误");
            return;
        }
        mView.showProgress();
        mModel.userLoginWeb(username, password, new BaseCallBack<BaseListBean>() {
            @Override
            public void onSuccess(BaseListBean data) {
                mView.hideProgress();
                if (data.getCode() == 0) {
                    mView.loginSuccess();
                } else {
                    mView.showMessage(data.getMessage());
                }
            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }
}