package com.sunstar.vegnet.kootl.comm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.news.bean.NewsChannelBean;

import java.util.List;

/**
 * Created by louisgeek on 2016/11/3.
 */
@Deprecated
public class BaseRVHeaderFooterAdapter extends RVHeaderFooterAdapter<NewsChannelBean.ChannelListBean> {


    public BaseRVHeaderFooterAdapter(List<NewsChannelBean.ChannelListBean> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }


    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, NewsChannelBean.ChannelListBean data) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemViewTypeInner(int realPosition, NewsChannelBean.ChannelListBean data) {
        return 0;
    }


}
