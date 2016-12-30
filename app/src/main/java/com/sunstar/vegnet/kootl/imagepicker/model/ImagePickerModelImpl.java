package com.sunstar.vegnet.kootl.imagepicker.model;

import android.content.Context;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.imagepicker.helper.ImagePickerHelper;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDirDataWrapper;
import com.sunstar.vegnet.kootl.imagepicker.contract.ImagePickerContract;

import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/12/12
 */

public class ImagePickerModelImpl implements ImagePickerContract.Model<ImagePickerDirDataWrapper> {

    @Override
    public void loadData(BaseCallBack<ImagePickerDirDataWrapper> baseCallBack) {

    }

    @Override
    public void scanImageData(Context context, final BaseCallBack<ImagePickerDirDataWrapper> baseCallBack) {
        ImagePickerHelper.scanLocalImage(context, new ImagePickerHelper.ScanLocalImagesCallBack() {
            @Override
            public void onSuccess(Map<String, List<String>> groupMap) {
                ImagePickerDirDataWrapper imagePickerDataWrapper = new ImagePickerDirDataWrapper();
                imagePickerDataWrapper.setGroupMap(groupMap);
                baseCallBack.onSuccess(imagePickerDataWrapper);
            }

            @Override
            public void onError(String message) {
                baseCallBack.onError(message);
            }
        });
    }
}