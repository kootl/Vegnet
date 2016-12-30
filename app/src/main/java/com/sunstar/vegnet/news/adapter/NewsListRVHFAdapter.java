package com.sunstar.vegnet.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterViewHolder;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.helper.NewsTagHelper;

import java.util.List;

/**
 * Created by louisgeek on 2016/12/7.
 */

public class NewsListRVHFAdapter extends RVHeaderFooterAdapter<NewsListBean.PagebeanBean.ContentlistBean> {
    // private int TYPE_NROMAL=21;
    private int TYPE_MULTI = 22;

    public NewsListRVHFAdapter(List<NewsListBean.PagebeanBean.ContentlistBean> dataList, RecyclerView recyclerView) {
        super(dataList, recyclerView);
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, NewsListBean.PagebeanBean.ContentlistBean data) {

        if (viewHolder instanceof NewsNormalRVHeaderFooterViewHolder) {
            //KLog.d("qq NewsNormalRVHeaderFooterViewHolder ");
            NewsNormalRVHeaderFooterViewHolder rvHFViewHolder = (NewsNormalRVHeaderFooterViewHolder) viewHolder;

            rvHFViewHolder.id_tv_item_title.setText(data.getTitle());
            rvHFViewHolder.id_tv_item_title_more.setText(data.getDesc());
            if (data.getImageurls() != null && data.getImageurls().size() > 0) {
                //  GlideHelper.displayImage(rvHFViewHolder.id_tv_item_image, data.getImageurls().get(0).getUrl().toString());
                ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image, data.getImageurls().get(0).getUrl().toString());
            } else {
                //防止错乱
                // GlideHelper.displayImageRes(rvHFViewHolder.id_tv_item_image, R.mipmap.ic_image_no);
                ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image, R.mipmap.ic_image_no);
            }
            NewsTagHelper.addTagViews(rvHFViewHolder.id_ll_item_tag_group);
        } else if (viewHolder instanceof NewsMultiImageRVHeaderFooterViewHolder) {
            //KLog.d("qq NewsMultiImageRVHeaderFooterViewHolder ");
            NewsMultiImageRVHeaderFooterViewHolder rvHFViewHolder = (NewsMultiImageRVHeaderFooterViewHolder) viewHolder;

            rvHFViewHolder.id_tv_item_title.setText(data.getTitle());

            NewsTagHelper.addTagViews(rvHFViewHolder.id_ll_item_tag_group);

            if (data.getImageurls() != null && data.getImageurls().size() > 0) {
                //GlideHelper.displayImage(rvHFViewHolder.id_tv_item_image, data.getImageurls().get(0).getUrl().toString());
                ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image, data.getImageurls().get(0).getUrl().toString());

                if (data.getImageurls().size() > 1) {
                    // GlideHelper.displayImage(rvHFViewHolder.id_tv_item_image2, data.getImageurls().get(1).getUrl().toString());
                    ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image2, data.getImageurls().get(1).getUrl().toString());

                    if (data.getImageurls().size() > 2) {
                        //GlideHelper.displayImage(rvHFViewHolder.id_tv_item_image3, data.getImageurls().get(2).getUrl().toString());
                        ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image3, data.getImageurls().get(2).getUrl().toString());
                    } else {
                        //GlideHelper.displayImageRes(rvHFViewHolder.id_tv_item_image3, R.mipmap.ic_image_no);
                        ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image3, R.mipmap.ic_image_no);
                    }
                } else {
                    //  GlideHelper.displayImageRes(rvHFViewHolder.id_tv_item_image2, R.mipmap.ic_image_no);
                    ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image2, R.mipmap.ic_image_no);
                }
            } else {
                //防止错乱
                // GlideHelper.displayImageRes(rvHFViewHolder.id_tv_item_image2, R.mipmap.ic_image_no);
                ImageLoaderFactory.getManager().displayImage(rvHFViewHolder.id_tv_item_image2, R.mipmap.ic_image_no);

            }

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType) {
        //KLog.d("viewType 11:" + viewType);

        RVHeaderFooterViewHolder rvHeaderFooterViewHolder;
        if (viewType == TYPE_MULTI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_multi_image, parent, false);
            rvHeaderFooterViewHolder = new NewsMultiImageRVHeaderFooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_normal, parent, false);
            rvHeaderFooterViewHolder = new NewsNormalRVHeaderFooterViewHolder(view);
        }

        return rvHeaderFooterViewHolder;
    }

    @Override
    public int getItemViewTypeInner(int realPosition, NewsListBean.PagebeanBean.ContentlistBean data) {
        if (data.getImageurls() != null && data.getImageurls().size() > 1) {
            return TYPE_MULTI;
        }
        return 0;
    }


    public class NewsMultiImageRVHeaderFooterViewHolder extends RVHeaderFooterViewHolder {
        TextView id_tv_item_title;
        ImageView id_tv_item_image;
        ImageView id_tv_item_image2;
        ImageView id_tv_item_image3;
        LinearLayout id_ll_item_tag_group;

        public NewsMultiImageRVHeaderFooterViewHolder(View itemView) {
            super(itemView);
            id_tv_item_title = (TextView) itemView.findViewById(R.id.id_tv_item_title);
            id_tv_item_image = (ImageView) itemView.findViewById(R.id.id_tv_item_image);
            id_tv_item_image2 = (ImageView) itemView.findViewById(R.id.id_tv_item_image2);
            id_tv_item_image3 = (ImageView) itemView.findViewById(R.id.id_tv_item_image3);
            id_ll_item_tag_group = (LinearLayout) itemView.findViewById(R.id.id_ll_item_tag_group);
        }
    }

    public class NewsNormalRVHeaderFooterViewHolder extends RVHeaderFooterViewHolder {
        TextView id_tv_item_title;
        TextView id_tv_item_title_more;
        ImageView id_tv_item_image;
        LinearLayout id_ll_item_tag_group;

        public NewsNormalRVHeaderFooterViewHolder(View itemView) {
            super(itemView);
            id_tv_item_title = (TextView) itemView.findViewById(R.id.id_tv_item_title);
            id_tv_item_title_more = (TextView) itemView.findViewById(R.id.id_tv_item_title_more);
            id_tv_item_image = (ImageView) itemView.findViewById(R.id.id_tv_item_image);
            id_ll_item_tag_group = (LinearLayout) itemView.findViewById(R.id.id_ll_item_tag_group);
        }
    }
}
