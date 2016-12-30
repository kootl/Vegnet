package com.sunstar.vegnet.kootl.imageshow.bean;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/8.
 */

public class ImageShowDataWrapper {
    private List<ImageShowBean> imageShowBeanList;
    private int nowSeletedPos;

    public ImageShowDataWrapper(List<ImageShowBean> imageShowBeanList, int nowSeletedPos) {
        this.imageShowBeanList = imageShowBeanList;
        this.nowSeletedPos = nowSeletedPos;
    }

    public List<ImageShowBean> getImageShowBeanList() {
        return imageShowBeanList;
    }

    public int getNowSeletedPos() {
        return nowSeletedPos;
    }
}
