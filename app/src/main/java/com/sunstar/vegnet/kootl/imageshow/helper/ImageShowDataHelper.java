package com.sunstar.vegnet.kootl.imageshow.helper;

import android.content.Context;
import android.content.Intent;

import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowBean;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowDataWrapper;
import com.sunstar.vegnet.kootl.imageshow.ui.ImageShowActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/11/14.
 */

public class ImageShowDataHelper {

    public static void setDataAndToImageShow(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        //
        List<ImageShowBean> imageShowBeanList = new ArrayList<>();
        ImageShowBean imageShowBean = new ImageShowBean();
        imageShowBean.setImageUrl(imageUrl);
        imageShowBean.setTitle(imageUrl);
        imageShowBean.setContent(imageUrl);
        imageShowBeanList.add(imageShowBean);

        ImageShowDataWrapper imageShowDataWrapper=new ImageShowDataWrapper(imageShowBeanList,0);
        DataHolderSingleton.getInstance().putData("imageShowDataWrapper", imageShowDataWrapper);
        //
        context.startActivity(intent);
    }

    public static void setDataAndToImageShow(Context context, ImageShowBean imageShowBean) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        //
        List<ImageShowBean> imageShowBeanList = new ArrayList<>();
        imageShowBeanList.add(imageShowBean);

        ImageShowDataWrapper imageShowDataWrapper=new ImageShowDataWrapper(imageShowBeanList,0);
        DataHolderSingleton.getInstance().putData("imageShowDataWrapper", imageShowDataWrapper);
        //
        context.startActivity(intent);
    }

    public static void setDataAndToImageShow(Context context, List<ImageShowBean> imageShowBeanList, int nowSeletedPos) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        //
        List<ImageShowBean> imageShowBeanArrayList = new ArrayList<>();
        imageShowBeanArrayList.addAll(imageShowBeanList);
        /*DataHolderSingleton.getInstance().putData("imageShowBeanList", imageShowBeanArrayList);
        DataHolderSingleton.getInstance().putData("nowSeletedPos", nowSeletedPos);*/
        ImageShowDataWrapper imageShowDataWrapper=new ImageShowDataWrapper(imageShowBeanArrayList,nowSeletedPos);
        DataHolderSingleton.getInstance().putData("imageShowDataWrapper", imageShowDataWrapper);
        //
        context.startActivity(intent);
    }
}
