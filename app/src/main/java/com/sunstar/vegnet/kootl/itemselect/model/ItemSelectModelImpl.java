package com.sunstar.vegnet.kootl.itemselect.model;

import com.sunstar.vegnet.kootl.comm.base.BaseCallBack;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectBean;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectDataWrapper;
import com.sunstar.vegnet.kootl.itemselect.contract.ItemSelectContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/10
 */

public class ItemSelectModelImpl implements ItemSelectContract.Model<ItemSelectDataWrapper> {

    @Override
    public void loadData(BaseCallBack<ItemSelectDataWrapper> baseCallBack) {
        ItemSelectDataWrapper itemSelectDataWrapper = new ItemSelectDataWrapper();
        List<ItemSelectBean> itemSelectBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemSelectBean itemSelectBean = new ItemSelectBean();
            itemSelectBean.setItemid(i);
            itemSelectBean.setItemTitle("标题" + i);
            if (i % 2 == 0) {
                itemSelectBean.setSelected(true);
            }
            itemSelectBeanList.add(itemSelectBean);
        }
        itemSelectDataWrapper.setItemSelectBeanList(itemSelectBeanList);

        baseCallBack.onSuccess(itemSelectDataWrapper);
    }
}