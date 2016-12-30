package com.sunstar.vegnet.kootl.imagepicker.contract;

import android.content.Context;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.base.BaseModel;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.base.BaseView;

/**
 * Created by louisgeek on 2016/12/12.
 */

public class ImagePickerContract {


    public interface View<T> extends BaseView<T> {
        Context getMyContext();
    }

    public interface Presenter<V> extends BasePresenter<V> {
    }

    public interface Model<T> extends BaseModel<T> {
        void scanImageData(Context context, BaseCallBack<T> baseCallBack);
    }


}