package com.sunstar.vegnet.images.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.images.bean.ImagesAndTextListBean;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterViewHolder;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/26.
 */

public class ImagesRVHFAdapter extends RVHeaderFooterAdapter<ImagesAndTextListBean.ContentlistBean> {

    public ImagesRVHFAdapter(List<ImagesAndTextListBean.ContentlistBean> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, ImagesAndTextListBean.ContentlistBean data) {
        if (viewHolder instanceof ImagesRVHFViewHolder) {
            ImagesRVHFViewHolder rvhfViewHolder = (ImagesRVHFViewHolder) viewHolder;
            rvhfViewHolder.id_tv_item_title.setText(data.getTitle());
            //
            String imageUrl = data.getImg();
           // KLog.d(imageUrl);
            if (imageUrl != null && !imageUrl.equals("")) {
              // GlideHelper.displayImage(rvhfViewHolder.id_tv_item_image, imageUrl);
                ImageLoaderFactory.getManager().displayImage(rvhfViewHolder.id_tv_item_image, imageUrl);
                //### LruCacheSimpleHelper.loadImage(imageUrl,rvhfViewHolder.id_tv_item_image);
            } else {
                //###GlideHelper.displayImageRes(rvhfViewHolder.id_tv_item_image, R.mipmap.ic_image_no);
                ImageLoaderFactory.getManager().displayImage(rvhfViewHolder.id_tv_item_image, R.mipmap.ic_image_no);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_big_image, parent, false);
        return new ImagesRVHFViewHolder(view);
    }

    @Override
    public int getItemViewTypeInner(int realPosition, ImagesAndTextListBean.ContentlistBean data) {
        return 0;
    }

    class ImagesRVHFViewHolder extends RVHeaderFooterViewHolder {
        TextView id_tv_item_title;
        ImageView id_tv_item_image;

        public ImagesRVHFViewHolder(View itemView) {
            super(itemView);
            id_tv_item_title = (TextView) itemView.findViewById(R.id.id_tv_item_title);
            id_tv_item_image = (ImageView) itemView.findViewById(R.id.id_tv_item_image);
        }
    }
}
