package com.sunstar.vegnet.kootl.comm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sunstar.vegnet.kootl.comm.tool.CollectionTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 将只保留当前页面，当页面离开视线后，就会被消除，释放其资源
 */

public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentList = fragmentList;
        mTitleList = titleList;
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        this(fm, fragmentList, null);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return CollectionTool.isNullOrEmpty(mTitleList) ? "" : mTitleList.get(position);
    }
}
