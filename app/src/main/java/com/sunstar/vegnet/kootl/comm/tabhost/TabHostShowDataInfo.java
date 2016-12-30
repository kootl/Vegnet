package com.sunstar.vegnet.kootl.comm.tabhost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/11/4.
 * 构建 TabHost
 */

public class TabHostShowDataInfo {
    private  List<Class<?>> mFragmentClassesList=new ArrayList<>();
    private  List<Integer> mImageResIDList=new ArrayList<>();
    private  List<Integer> mTabLabelResIDList=new ArrayList<>();

    /*public static String mTabTag[] = { "tab1", "tab2","tab3","tab4"};*/
    public   void addFragmentClass(Class<?> clazz,int imageResId,int tabLabelResId){
        mFragmentClassesList.add(clazz);
        mImageResIDList.add(imageResId);
        mTabLabelResIDList.add(tabLabelResId);
    }

    public List<Class<?>> getFragmentClassesList() {
        return mFragmentClassesList;
    }

    public List<Integer> getImageResIDList() {
        return mImageResIDList;
    }

    public List<Integer> getTabLabelResIDList() {
        return mTabLabelResIDList;
    }
}
