package com.sunstar.vegnet.kootl.comm.factory.imageloader.impls;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.IImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.ILoadImageCallback;
import com.sunstar.vegnet.kootl.comm.helper.GlideHelper;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class GlideImageLoader implements IImageLoader {
    @Override
    public void displayImage(ImageView imageView, String url, int width, int height) {
        GlideHelper.displayImage(imageView, url, width, height);
    }

    @Override
    public void displayImage(ImageView imageView, String url) {
        GlideHelper.displayImage(imageView, url);
    }

    @Override
    public void displayImageNoPlaceholder(ImageView imageView, String url) {
        GlideHelper.displayImageNoPlaceholder(imageView, url);
    }

    @Override
    public void displayImage(ImageView imageView, int imageResId) {
        GlideHelper.displayImageRes(imageView, imageResId);
    }

    @Override
    public void loadImage(String url, final ILoadImageCallback loadImageCallback) {
        GlideHelper.loadImage(url, new GlideHelper.OnLoadImageBackListener() {
            @Override
            public void onLoadImageBack(Bitmap bitmap) {
                loadImageCallback.onLoadImageBack(bitmap);
            }
        });
    }
}
