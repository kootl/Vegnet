package com.sunstar.vegnet.kootl.comm.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.comm.helper.VectorOrImageResHelper;

/**
 * Created by louisgeek on 2016/12/15.
 */

public class ToastTool {


    private static Toast mToast;
    private static Toast mToastImage;

    /**
     * @param message
     * @param duration
     * @return
     */
    private static Toast initToast(CharSequence message, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(KooApplication.getAppContext(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        return mToast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(String message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(String message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void showDuration(String message, int duration) {
        initToast(message, duration).show();
    }


    /**
     * 显示有image的toast
     *
     * @param message
     * @param drawable
     * @return
     */
    public static Toast showImage(final String message, final Drawable drawable) {
        Context context = KooApplication.getAppContext();
        if (mToastImage == null) {
            mToastImage = new Toast(context);
            mToastImage.setDuration(Toast.LENGTH_SHORT);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.id_tv_toast);
        tv.setText(TextUtils.isEmpty(message) ? "" : message);
        ImageView iv = (ImageView) view.findViewById(R.id.id_iv_toast);
        if (drawable != null) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageDrawable(drawable);
        } else {
            iv.setVisibility(View.GONE);
        }
        mToastImage.setView(view);
        mToastImage.setGravity(Gravity.CENTER, 0, 0);
        mToastImage.show();
        return mToastImage;

    }

    /**
     * VectorDrawableCompat
     *
     * @param message
     * @param imageResId
     * @return
     */
    public static Toast showImageBase(final String message, int imageResId) {
        Drawable drawable = VectorOrImageResHelper.getDrawable(KooApplication.getAppContext(), imageResId);
        return showImage(message, drawable);
    }

    public static Toast showImageOk(final String message) {
        return showImageBase(message, R.drawable.ic_check_circle_black_24dp);
    }

    public static Toast showImageInfo(final String message) {
        return showImageBase(message, R.drawable.ic_info_black_24dp);
    }

    public static Toast showImageWarn(final String message) {
        return showImageBase(message, R.drawable.ic_error_black_24dp);
    }
}