package com.sunstar.vegnet.kootl.imageshow.presenter;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowDataWrapper;
import com.sunstar.vegnet.kootl.imageshow.contract.ImageShowContract;
import com.sunstar.vegnet.kootl.imageshow.model.ImageShowModelImpl;

/**
 * Created by louisgeek on 2016/12/08
 */

public class ImageShowPresenterImpl implements ImageShowContract.Presenter<ImageShowContract.View> {

    private ImageShowContract.Model mModel;
    private ImageShowContract.View mView;

    public ImageShowPresenterImpl(ImageShowContract.View view) {
        this.attachView(view);
        mModel = new ImageShowModelImpl();
    }

    @Override
    public void attachView(ImageShowContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


    @Override
    public void gainData() {
        mView.showProgress();
        mModel.loadData(new BaseCallBack<ImageShowDataWrapper>() {
            @Override
            public void onSuccess(ImageShowDataWrapper data) {
                mView.hideProgress();
                mView.setupData(data);
            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }
}