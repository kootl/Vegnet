package com.sunstar.vegnet.kootl.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterViewHolder;
import com.sunstar.vegnet.kootl.search.bean.SearchListBean;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/7.
 */

public class SearchListRVHFAdapter extends RVHeaderFooterAdapter<SearchListBean.PagebeanBean.ContentlistBean> {
    public SearchListRVHFAdapter(List<SearchListBean.PagebeanBean.ContentlistBean> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, SearchListBean.PagebeanBean.ContentlistBean data) {
        RVHeaderFooterViewHolder rvHFViewHolder = (RVHeaderFooterViewHolder) viewHolder;
        TextView id_tv_item_title = rvHFViewHolder.findBindView(R.id.id_tv_item_title);
        id_tv_item_title.setText(data.getTitle());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_normal, parent, false);
        return new RVHeaderFooterViewHolder(view);
    }

    @Override
    public int getItemViewTypeInner(int realPosition, SearchListBean.PagebeanBean.ContentlistBean data) {
        return 0;
    }

}
