package com.sunstar.vegnet.kootl.guide.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.guide.bean.GuideBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/15.
 */

public class GuidePagerAdapter extends PagerAdapter {
    public GuidePagerAdapter(List<GuideBean> guideBeanList) {
        mGuideBeanList = guideBeanList;
    }

    private List<GuideBean> mGuideBeanList = new ArrayList<>();

    @Override
    public int getCount() {
        return mGuideBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_normal, null, false);
        ImageView id_tv_image = (ImageView) view.findViewById(R.id.id_tv_image);
        Button id_btn_jump_in = (Button) view.findViewById(R.id.id_btn_jump_in);


        id_btn_jump_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGuideClickListener != null) {
                    onGuideClickListener.onJumpInClick(v);
                }
            }
        });

        if (position == mGuideBeanList.size() - 1) {
            //最后一页

            //
            id_btn_jump_in.setVisibility(View.VISIBLE);

        } else {
            //

            //
            id_btn_jump_in.setVisibility(View.GONE);


        }
       // GlideHelper.displayImageNoPlaceholder(id_tv_image, mGuideBeanList.get(position).getImagepath());
        ImageLoaderFactory.getManager().displayImageNoPlaceholder(id_tv_image, mGuideBeanList.get(position).getImagepath());
        //
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    public interface OnGuideClickListener {

        void onJumpInClick(View v);
    }


    public void setOnGuideClickListener(OnGuideClickListener onGuideClickListener) {
        this.onGuideClickListener = onGuideClickListener;
    }

    public OnGuideClickListener onGuideClickListener;


    /**
     * 传入的dataList  因为引用传递和值传递的    所以引用地址不能和mDataList一致
     *
     * @param guideBeanList
     */
    public void refreshDataList(List<GuideBean> guideBeanList) {
        mGuideBeanList.clear();
        mGuideBeanList.addAll(guideBeanList);
        notifyDataSetChanged();
    }


}
