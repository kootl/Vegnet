package com.sunstar.vegnet.kootl.imagepicker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterViewHolder;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDirBean;

import java.util.List;

/**
 * Created by louisgeek on 2016/11/3.
 */

public class ImagePickerDirListRecyclerAdapter extends RVHeaderFooterAdapter<ImagePickerDirBean> {

    private View mLastClickDirItemChildView = null;

    public ImagePickerDirListRecyclerAdapter(List<ImagePickerDirBean> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image_picker_dir, parent, false);
        return new RVHeaderFooterViewHolder(view);
    }

    @Override
    public int getItemViewTypeInner(int realPosition, ImagePickerDirBean data) {
        return 0;
    }


    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, ImagePickerDirBean data) {
        RVHeaderFooterViewHolder rvHFViewHolder = (RVHeaderFooterViewHolder) viewHolder;
        TextView id_tv_item_title = rvHFViewHolder.findBindView(R.id.id_tv_item_title);
        ImageView id_tv_item_image = rvHFViewHolder.findBindView(R.id.id_tv_item_image);
        ImageView id_tv_item_image_more = rvHFViewHolder.findBindView(R.id.id_tv_item_image_more);

        if (realPosition == 0) {
            id_tv_item_image_more.setVisibility(View.VISIBLE);
            mLastClickDirItemChildView = id_tv_item_image_more;
        } else {
            id_tv_item_image_more.setVisibility(View.GONE);
        }

        id_tv_item_title.setText(data.getDirName().replace("/", "") + "(" + data.getImageCount() + "张)");

        KLog.d("getDirImageIconPath" + data.getDirImageIconPath());
        //GlideHelper.displayImage(id_tv_item_image, data.getDirImageIconPath());
        ImageLoaderFactory.getManager().displayImage(id_tv_item_image, data.getDirImageIconPath());

    }


    public View getLastClickDirItemChildView() {
        return mLastClickDirItemChildView;
    }

    public void setLastClickDirItemChildView(View lastClickDirItemChildView) {
        mLastClickDirItemChildView = lastClickDirItemChildView;
    }
}
