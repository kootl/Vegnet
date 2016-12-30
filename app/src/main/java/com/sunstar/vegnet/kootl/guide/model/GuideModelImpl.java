package com.sunstar.vegnet.kootl.guide.model;

import com.sunstar.vegnet.data.ImagesDatas;
import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.guide.bean.GuideBean;
import com.sunstar.vegnet.kootl.guide.contract.GuideContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/15
 */

public class GuideModelImpl implements GuideContract.Model<List<GuideBean>> {

    @Override
    public void loadData(BaseCallBack<List<GuideBean>> baseCallBack) {
        List<GuideBean> guideBeanList = new ArrayList<>();
        for (int i = 0; i < ImagesDatas.imageThumbUrls.length; i++) {
            if (i > 3) {
                break;
            }
            GuideBean guideBean = new GuideBean();
            guideBean.setImagepath(ImagesDatas.imageThumbUrls[i]);
            guideBeanList.add(guideBean);
        }
        /**
         *
         */
        baseCallBack.onSuccess(guideBeanList);

    }
}