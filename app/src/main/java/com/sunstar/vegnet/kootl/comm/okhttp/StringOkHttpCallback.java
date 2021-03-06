package com.sunstar.vegnet.kootl.comm.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by louisgeek on 2016/7/11.
 */
public abstract class StringOkHttpCallback  implements okhttp3.Callback {

    private static final String TAG = "StringOkHttpCallback";
    @Override
    public void onFailure(Call call, final IOException e) {
        if (call.isCanceled()){
            this.OnError("StringOkHttpCallback call is canceled,call:"+call.toString()+";message:"+e.getMessage(), OkHttpSingleton.OKHTTP_CALL_CANCEL);
        }else{
            this.OnError(e.getMessage(), OkHttpSingleton.OKHTTP_CALL_ERROR);
        }
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()){
            final String response_body_result = response.body().string();
            Log.d(TAG, "onResponse: response_body_result:"+response_body_result);
             this.OnSuccess(response_body_result,response.code());
        }else{
            this.OnError(response.message(),response.code());
        }
    }

    public  abstract void OnSuccess(String result,int statusCode);

    public  abstract void OnError(String errorMsg,int statusCode);
}
