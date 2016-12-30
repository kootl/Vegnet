package com.sunstar.vegnet.kootl.itemselect.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/10.
 */

public class ItemSelectDataWrapper implements Serializable{
    public List<ItemSelectBean> getItemSelectBeanList() {
        return mItemSelectBeanList;
    }

    public void setItemSelectBeanList(List<ItemSelectBean> itemSelectBeanList) {
        mItemSelectBeanList = itemSelectBeanList;
    }

    private List<ItemSelectBean> mItemSelectBeanList;
}
