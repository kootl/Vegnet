package com.sunstar.vegnet.kootl.ssqselect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterViewHolder;
import com.sunstar.vegnet.kootl.ssqselect.bean.AreaDataWrapper;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/7.
 */

public class AreaSelectRVHFAdapter extends RVHeaderFooterAdapter<AreaDataWrapper> {
    public AreaSelectRVHFAdapter(List<AreaDataWrapper> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_select_has_more, parent, false);
        return new RVHeaderFooterViewHolder(view);
    }

    @Override
    public int getItemViewTypeInner(int realPosition, AreaDataWrapper data) {
        return 0;
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, AreaDataWrapper data) {
        RVHeaderFooterViewHolder rvHFViewHolder = (RVHeaderFooterViewHolder) viewHolder;

        TextView id_tv_item_title = rvHFViewHolder.findBindView(R.id.id_tv_item_title);
      /*  id_tv_item_title.setText(data.getName().replace("省", "") + "province:" + data.getHoldSelectedProvincePos()
                + "city:" + data.getHoldSelectedCityPos() + "area:" + data.getHoldSelectedAreaPos()
                + "nowShowWhere:" + data.getNowShowWhere()
        );*/
        id_tv_item_title.setText(data.getName());
        //id_tv_item_title.setText(data.getName().replace("省", ""));
        ImageView id_tv_item_image = rvHFViewHolder.findBindView(R.id.id_tv_item_image);
        if (data.isShowMoreIcon()) {
            id_tv_item_image.setVisibility(View.VISIBLE);
        } else {
            id_tv_item_image.setVisibility(View.INVISIBLE);
        }

    }

}
