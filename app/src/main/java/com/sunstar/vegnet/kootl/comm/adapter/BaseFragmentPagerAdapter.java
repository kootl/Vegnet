package com.sunstar.vegnet.kootl.comm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.sunstar.vegnet.kootl.comm.tool.CollectionTool;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment   FragmentPagerAdapter适配器
 *
 * BaseFragmentPagerAdapter每一个生成的 Fragment 都将保存在内存之中，适用于数量比较少的/相对静态的页面
 * 如果处理有很多页面，数据动态性较大、占用内存较多的情况，应该使用BaseFragmentStatePagerAdapter
 *
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private FragmentManager mFragmentManager;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentManager = fm;
        mFragmentList = fragmentList;
        mTitleList = titleList;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        this(fm, fragmentList, null);
    }
    public void refreshFragment(List<Fragment> fragmentList, List<String> titleList) {
        if (mFragmentList != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : mFragmentList) {
                fragmentTransaction.remove(fragment);
            }
            //fragmentTransaction.commit();
            fragmentTransaction.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
        mTitleList = titleList;
        mFragmentList = fragmentList;
        notifyDataSetChanged();
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
