package com.sunstar.vegnet.kootl.guide.presenter;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.guide.bean.GuideBean;
import com.sunstar.vegnet.kootl.guide.contract.GuideContract;
import com.sunstar.vegnet.kootl.guide.model.GuideModelImpl;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/15
 */

public class GuidePresenterImpl implements GuideContract.Presenter<GuideContract.View> {

    private GuideContract.Model mModel;
    private GuideContract.View mView;

    public GuidePresenterImpl(GuideContract.View view) {
        this.attachView(view);
        mModel = new GuideModelImpl();
    }

    @Override
    public void attachView(GuideContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


    @Override
    public void gainData() {
        mModel.loadData(new BaseCallBack<List<GuideBean>>() {
            @Override
            public void onSuccess(List<GuideBean> data) {
                mView.setupData(data);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}