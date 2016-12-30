package com.sunstar.vegnet.kootl.usercenter.contract;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.base.BaseModel;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.base.BaseView;

/**
 * Created by louisgeek on 2016/12/9.
 */

public class UserLoginContract {

    public interface View<T> extends BaseView<T> {
        String getUserName();

        String getPassword();

        void loginSuccess();

    }

    public interface Presenter<V> extends BasePresenter<V> {
    }

    public interface Model<T> extends BaseModel<T> {
        void userLoginWeb(String userName,String userPass,BaseCallBack<T> baseCallBack);
    }


}