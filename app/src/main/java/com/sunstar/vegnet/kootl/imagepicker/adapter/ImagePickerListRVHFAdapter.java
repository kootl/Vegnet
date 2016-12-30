package com.sunstar.vegnet.kootl.imagepicker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterViewHolder;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/12.
 */

public class ImagePickerListRVHFAdapter extends RVHeaderFooterAdapter<ImagePickerBean> {


    public List<String> getSelectedImageList() {
        return mSelectedImageList;
    }

    private List<String> mSelectedImageList = new ArrayList<>();

    public ImagePickerListRVHFAdapter(List<ImagePickerBean> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, ImagePickerBean data) {
        RVHeaderFooterViewHolder rvHFViewHolder = (RVHeaderFooterViewHolder) viewHolder;
        ImageView id_iv_item_image = rvHFViewHolder.findBindView(R.id.id_iv_item_image);

        //GlideHelper.displayImage(id_iv_item_image, data.getPath());
        ImageLoaderFactory.getManager().displayImage(id_iv_item_image, data.getPath());

        ImageView id_iv_selectbtn = rvHFViewHolder.findBindView(R.id.id_iv_selectbtn);
        if (mSelectedImageList.contains(data.getPath())) {
            id_iv_selectbtn.setSelected(true);
        } else {
            id_iv_selectbtn.setSelected(false);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image_picker, parent, false);
        return new RVHeaderFooterViewHolder(view);
    }

    @Override
    public int getItemViewTypeInner(int realPosition, ImagePickerBean data) {
        return 0;
    }



}
