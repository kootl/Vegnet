package com.sunstar.vegnet.kootl.userinfo.presenter;

import com.sunstar.vegnet.kootl.userinfo.contract.UserInfoContract;
import com.sunstar.vegnet.kootl.userinfo.model.UserInfoModelImpl;

/**
 * Created by louisgeek on 2016/12/22
 */

public class UserInfoPresenterImpl implements UserInfoContract.Presenter<UserInfoContract.View> {


    private UserInfoContract.Model mModel;
    private UserInfoContract.View mView;

    public UserInfoPresenterImpl(UserInfoContract.View view) {
        this.attachView(view);
        mModel = new UserInfoModelImpl();
    }

    @Override
    public void attachView(UserInfoContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {

    }
}