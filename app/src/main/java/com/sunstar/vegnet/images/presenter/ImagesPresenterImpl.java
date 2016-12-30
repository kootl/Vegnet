package com.sunstar.vegnet.images.presenter;

import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.images.bean.ImagesAndTextListBean;
import com.sunstar.vegnet.images.contract.ImagesContract;
import com.sunstar.vegnet.images.model.ImagesModelImpl;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;

/**
 * Created by louisgeek on 2016/12/26
 */

public class ImagesPresenterImpl implements ImagesContract.Presenter<ImagesContract.View> {

    @Override
    public void gainMoreData(int page) {
        mView.showMoreDataLoading();
        mModel.loadMoreData(page, new BaseCallBack<BaseBeanShowApi<ImagesAndTextListBean>>() {
            @Override
            public void onSuccess(BaseBeanShowApi<ImagesAndTextListBean> data) {
                //
                mView.hideMoreDataLoading();
                if (data != null && data.getShowapi_res_body() != null && data.getShowapi_res_body() != null
                        && data.getShowapi_res_body().getContentlist().size() > 0) {
                    mView.setupMoreData(data);
                } else {
                    mView.showMoreDataLoadComplete();
                }
            }

            @Override
            public void onError(String msg) {
                //
                mView.hideMoreDataLoading();
                mView.showMessage(msg);
            }
        });
    }

    private ImagesContract.Model mModel;
    private ImagesContract.View mView;

    public ImagesPresenterImpl(ImagesContract.View view) {
        this.attachView(view);
        mModel = new ImagesModelImpl();
    }

    @Override
    public void attachView(ImagesContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void gainData() {
        mView.showProgress();
        mModel.loadData(new BaseCallBack<BaseBeanShowApi<ImagesAndTextListBean>>() {
            @Override
            public void onSuccess(BaseBeanShowApi<ImagesAndTextListBean> data) {
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