package com.sunstar.vegnet.kootl.usercenter.model;

import com.socks.library.KLog;
import com.sunstar.vegnet.data.ApiUrls;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.okhttp.GsonOkHttpCallback;
import com.sunstar.vegnet.kootl.comm.okhttp.OkHttpHelper;
import com.sunstar.vegnet.kootl.comm.okhttp.OkHttpSingleton;
import com.sunstar.vegnet.kootl.comm.tool.MD5Tool;
import com.sunstar.vegnet.kootl.usercenter.bean.BaseListBean;
import com.sunstar.vegnet.kootl.usercenter.contract.UserLoginContract;

/**
 * Created by louisgeek on 2016/12/09
 */

public class UserLoginModelImpl implements UserLoginContract.Model<BaseListBean> {

    @Override
    public void loadData(final BaseCallBack<BaseListBean> baseCallBack) {


    }


    @Override
    public void userLoginWeb(String userName, String userPass, final BaseCallBack<BaseListBean> baseCallBack) {
        new OkHttpHelper.Builder()
               // .params("Version=2&LoginID=gxb1&Password=96E79218965EB72C92A549DD5A330112&RoleType=1")
                .params("Version=2&LoginID="+userName+"&Password="+ MD5Tool.encode(userPass)+"&RoleType=1")
                .callback(new GsonOkHttpCallback<BaseListBean>() {
                    @Override
                    public BaseListBean OnSuccess(String result, int statusCode) {
                        return BaseListBean.fromJsonSix(result, BaseListBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(BaseListBean baseBean, int statusCode) {
                        baseCallBack.onSuccess(baseBean);
                    }

                    @Override
                    public void OnError(String errorMsg, int statusCode) {
                        KLog.e("statusCode:"+statusCode+errorMsg);
                        switch (statusCode){
                            case  OkHttpSingleton.OKHTTP_CALL_ERROR:
                                baseCallBack.onError("服务器异常");
                                break;
                            case  OkHttpSingleton.OKHTTP_CALL_CANCEL:
                                baseCallBack.onError("请求取消");
                                break;
                            default:
                            baseCallBack.onError("服务器错误");
                        }
                    }
                }).build().doPostUrl(ApiUrls.userLoginUrl);
    }
}