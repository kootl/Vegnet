package com.sunstar.vegnet.kootl.ssqselect.contract;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.base.BaseModel;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.base.BaseView;
import com.sunstar.vegnet.kootl.ssqselect.bean.Province;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/26.
 */

public class SSQSelectContract {

    public interface View<T> extends BaseView<T> {
        int readyDataNeedShowWhere();

        void backQueryResultData(T data);

        List<Province> readyDataNeedQueryData();

        String readyDataQueryText();
    }

    public interface Presenter<V> extends BasePresenter<V> {
        void querySSQData();
    }

    public interface Model<T> extends BaseModel<T> {
        void loadSSQData(int nowShowWhere, BaseCallBack<T> baseCallBack);

        void querySSQData(List<Province> forQueryProvinceList, String newText, BaseCallBack<T> baseCallBack);

    }


}