package com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces;

import android.widget.ImageView;

/**
 * Created by louisgeek on 2016/12/28.
 */

public interface IImageLoader {
    void displayImage(ImageView imageView, String url, int width, int height);

    void displayImage(ImageView imageView, String url);

    void displayImageNoPlaceholder(ImageView imageView, String url);

    void displayImage(ImageView imageView, int imageResId);


    void loadImage(String url, ILoadImageCallback loadImageCallback);


}