package com.sunstar.vegnet.kootl.comm.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;

/**
 * Created by louisgeek on 2016/12/27.
 */

public class VectorOrImageResHelper {
    public static Drawable getDrawable(Context context, int imageResId) {
        /**
         * 自定处理VectorDrawable  or  Image  否则5.0以下使用Vector会报错
         */
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, imageResId);
        return drawable;
    }
}
