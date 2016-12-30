package com.sunstar.vegnet.images.model;

import com.socks.library.KLog;
import com.sunstar.vegnet.data.ApiUrls;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.images.bean.ImagesAndTextListBean;
import com.sunstar.vegnet.images.contract.ImagesContract;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.comm.lrucache.LruCacheNormalLogicHelper;
import com.sunstar.vegnet.kootl.comm.okhttp.GsonOkHttpCallback;
import com.sunstar.vegnet.kootl.comm.okhttp.OkHttpHelper;

import java.util.HashMap;
import java.util.Map;

import static com.sunstar.vegnet.data.BaseGsonBean.fromJson;

/**
 * Created by louisgeek on 2016/12/26
 */

public class ImagesModelImpl implements ImagesContract.Model<BaseBeanShowApi<ImagesAndTextListBean>> {

    @Override
    public void loadMoreData(int page, BaseCallBack<BaseBeanShowApi<ImagesAndTextListBean>> baseCallBack) {
        loadDataBase(ApiUrls.jokeGifImagesListUrl, page, baseCallBack);
    }

    @Override
    public void loadData(BaseCallBack<BaseBeanShowApi<ImagesAndTextListBean>> baseCallBack) {
        loadDataBase(ApiUrls.jokeGifImagesListUrl, 1, baseCallBack);
    }

    private void loadDataBase(final String apiUrl, int page, final BaseCallBack<BaseBeanShowApi<ImagesAndTextListBean>> baseCallBack) {
        final Map<String, String> paramMap = new HashMap<>();
       /* paramMap.put("showapi_appid", ApiUrls.SHOWAPI_APPID);
        paramMap.put("showapi_sign", ApiUrls.SHOWAPI_SIGN_SIMPLE);*/
        //
        paramMap.put("page", String.valueOf(page));
        //
        final String withoutTimeKey = apiUrl + "?page=" + page;
        LruCacheNormalLogicHelper.findCacheLogic(withoutTimeKey, new LruCacheNormalLogicHelper.OnCacheLogicCallback() {
            @Override
            public void onCacheLogic(final String keyRawLazy) {
                //
             /*   final String result = LruCacheStringTool.getStingCacheLevels(keyRawLazy);
                if (result != null) {
                    KLog.json("缓存  result:" + result);
                    BaseBeanShowApi<ImagesAndTextListBean> baseBean = BaseBeanShowApi.fromJson(result, ImagesAndTextListBean.class);

                    if (baseBean.getShowapi_res_code() == 0) {
                        baseCallBack.onSuccess(baseBean);
                    } else {
                        baseCallBack.onError(baseBean.getShowapi_res_error());
                        LruCacheNormalLogicHelper.removeCacheMaybeIn_Http_back_OnError(keyRawLazy, withoutTimeKey);
                    }
                } else*/ {
                    //
                    KLog.json("网络  result:");
                    new OkHttpHelper.Builder().params(paramMap).callback(new GsonOkHttpCallback<BaseBeanShowApi<ImagesAndTextListBean>>() {


                        @Override
                        public BaseBeanShowApi<ImagesAndTextListBean> OnSuccess(String result, int statusCode) {
                            LruCacheNormalLogicHelper.saveCacheMaybeIn_Http_back_OnSuccess(keyRawLazy, result, withoutTimeKey);
                            return fromJson(result, ImagesAndTextListBean.class);
                        }

                        @Override
                        public void OnSuccessOnUI(BaseBeanShowApi<ImagesAndTextListBean> baseBean, int statusCode) {
                            if (baseBean.getShowapi_res_code()==0){
                                baseCallBack.onSuccess(baseBean);
                            } else {
                                baseCallBack.onError(baseBean.getShowapi_res_error());
                                LruCacheNormalLogicHelper.removeCacheMaybeIn_Http_back_OnError(keyRawLazy, withoutTimeKey);
                            }
                        }

                        @Override
                        public void OnError(String errorMsg, int statusCode) {
                            baseCallBack.onError(errorMsg);
                        }
                    }).build().doPostUrl(apiUrl);
                    //

                }
            }
        });
    }
}