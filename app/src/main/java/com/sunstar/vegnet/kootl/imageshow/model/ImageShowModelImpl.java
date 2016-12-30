package com.sunstar.vegnet.kootl.imageshow.model;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.kootl.imageshow.contract.ImageShowContract;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowDataWrapper;

/**
 * Created by louisgeek on 2016/12/08
 */

public class ImageShowModelImpl implements ImageShowContract.Model<ImageShowDataWrapper> {

    @Override
    public void loadData(BaseCallBack<ImageShowDataWrapper> baseCallBack) {
        ImageShowDataWrapper imageShowDataWrapper = (ImageShowDataWrapper) DataHolderSingleton.getInstance().getData("imageShowDataWrapper");
        if (imageShowDataWrapper != null) {
            baseCallBack.onSuccess(imageShowDataWrapper);
        } else {
            baseCallBack.onError("imageShowDataWrapper is null");
        }
    }
}