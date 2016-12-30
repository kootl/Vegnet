package com.sunstar.vegnet.kootl.itemselect.presenter;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectDataWrapper;
import com.sunstar.vegnet.kootl.itemselect.contract.ItemSelectContract;
import com.sunstar.vegnet.kootl.itemselect.model.ItemSelectModelImpl;

/**
 * Created by louisgeek on 2016/12/10
 */

public class ItemSelectPresenterImpl implements ItemSelectContract.Presenter<ItemSelectContract.View> {

    private ItemSelectContract.Model mModel;
    private ItemSelectContract.View mView;

    public ItemSelectPresenterImpl(ItemSelectContract.View view) {
        this.attachView(view);
        mModel = new ItemSelectModelImpl();
    }

    @Override
    public void attachView(ItemSelectContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {
        mView.showProgress();
        mModel.loadData(new BaseCallBack<ItemSelectDataWrapper>() {
            @Override
            public void onSuccess(ItemSelectDataWrapper data) {
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