package com.sunstar.vegnet.kootl.imagepicker.presenter;

import android.content.Context;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDirDataWrapper;
import com.sunstar.vegnet.kootl.imagepicker.contract.ImagePickerContract;
import com.sunstar.vegnet.kootl.imagepicker.model.ImagePickerModelImpl;

/**
 * Created by louisgeek on 2016/12/12
 */

public class ImagePickerPresenterImpl implements ImagePickerContract.Presenter<ImagePickerContract.View> {

    private ImagePickerContract.Model mModel;
    private ImagePickerContract.View mView;

    public ImagePickerPresenterImpl(ImagePickerContract.View view) {
        this.attachView(view);
        mModel = new ImagePickerModelImpl();
    }

    @Override
    public void attachView(ImagePickerContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


    @Override
    public void gainData() {
        mView.showProgress();
        Context context = mView.getMyContext();
        mModel.scanImageData(context, new BaseCallBack<ImagePickerDirDataWrapper>() {
            @Override
            public void onSuccess(ImagePickerDirDataWrapper data) {
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