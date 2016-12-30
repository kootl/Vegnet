package com.sunstar.vegnet.kootl.ssqselect.presenter;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.ssqselect.bean.AreaDataWrapper;
import com.sunstar.vegnet.kootl.ssqselect.contract.SSQSelectContract;
import com.sunstar.vegnet.kootl.ssqselect.model.SSQSelectModelImpl;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/26
 */

public class SSQSelectPresenterImpl implements SSQSelectContract.Presenter<SSQSelectContract.View> {

    private SSQSelectContract.Model mModel;
    private SSQSelectContract.View mView;

    public SSQSelectPresenterImpl(SSQSelectContract.View view) {
        this.attachView(view);
        mModel = new SSQSelectModelImpl();
    }

    @Override
    public void attachView(SSQSelectContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {
        mView.showProgress();
        mModel.loadSSQData(mView.readyDataNeedShowWhere(), new BaseCallBack<List<AreaDataWrapper>>() {
            @Override
            public void onSuccess(List<AreaDataWrapper> data) {
                mView.hideProgress();
                if (data.size() > 0) {
                    mView.setupData(data);
                } else {
                    mView.showNoDataNotice();
                }

            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }

    @Override
    public void querySSQData() {
        mModel.querySSQData(mView.readyDataNeedQueryData(), mView.readyDataQueryText(), new BaseCallBack<List<AreaDataWrapper>>() {
            @Override
            public void onSuccess(List<AreaDataWrapper> data) {
                mView.backQueryResultData(data);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}