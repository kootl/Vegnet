package com.sunstar.vegnet.news.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunstar.vegnet.R;

/**
 * Created by louisgeek on 2016/12/18.
 */

public class NewsTagHelper {
    public static void addTagViews(LinearLayout ll_item_tag_group) {
        ll_item_tag_group.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View tagView = LayoutInflater.from(ll_item_tag_group.getContext()).inflate(R.layout.layout_news_tag, null, false);
            TextView id_tv_tag = (TextView) tagView.findViewById(R.id.id_tv_tag);
            id_tv_tag.setText("独家" + i);
            ll_item_tag_group.addView(id_tv_tag);
        }
    }
}
