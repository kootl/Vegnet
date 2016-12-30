package com.sunstar.vegnet.kootl.comm.helper;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.comm.tool.NetWorkTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by louisgeek on 2016/11/5.
 */

public class GlideHelper {

    public static void displayImage(ImageView imageView, String url, int width, int height) {
        boolean isNoPic = PreferenceManagerHelper.isNoPic(imageView.getContext());
        if (!NetWorkTool.isNetWorkConnected_Wifi()) {//不是wifi
            if (isNoPic) {//设置移动网络下无图
                ToastTool.showImageInfo("移动网络下不加载图片！");
                return;
            }
        }
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().override(width, height).into(imageView);
    }

    public static void displayImage(ImageView imageView, String url) {
        boolean isNoPic = PreferenceManagerHelper.isNoPic(imageView.getContext());
        if (!NetWorkTool.isNetWorkConnected_Wifi()) {//不是wifi
            if (isNoPic) {//设置移动网络下无图
                ToastTool.showImageInfo("移动网络下不加载图片！");
                return;
            }
        }
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(imageView);
    }

    public static void displayImageNoPlaceholder(ImageView imageView, String url) {
        boolean isNoPic = PreferenceManagerHelper.isNoPic(imageView.getContext());
        if (!NetWorkTool.isNetWorkConnected_Wifi()) {//不是wifi
            if (isNoPic) {//设置移动网络下无图
                ToastTool.showImageInfo("移动网络下不加载图片！");
                return;
            }
        }
        Glide.with(imageView.getContext()).load(url)
                //.placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(imageView);
    }

    @Deprecated //不推荐
    public static void displayImage(ImageView imageView, Bitmap bitmap) {
        //
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        //
        Glide.with(imageView.getContext()).load(bytes).asBitmap().placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no).into(imageView);
    }

    public static void displayImageRes(ImageView imageView, int resId) {
        Glide.with(imageView.getContext()).load(resId)
                //.placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no).crossFade().into(imageView);
    }

    public static void displayCropCircleImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //  .error(R.drawable.ic_image_no)
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }


    public static void loadImage(final ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_image_no)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.ic_image_no)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static void loadImage(String url, final OnLoadImageBackListener onLoadImageBackListener) {
        Glide.with(KooApplication.getAppContext()).load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_image_no)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.ic_image_no)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (onLoadImageBackListener != null) {
                            onLoadImageBackListener.onLoadImageBack(resource);
                        }

                    }
                });
    }

    public interface OnLoadImageBackListener {
        void onLoadImageBack(Bitmap bitmap);
    }
}
