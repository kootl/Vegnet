package com.sunstar.vegnet.kootl.comm.factory.imageloader.impls;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.IImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.ILoadImageCallback;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class PicassoImageLoader implements IImageLoader {
    @Override
    public void displayImage(ImageView imageView, String url, int width, int height) {
        Picasso.with(imageView.getContext()).load(url).resize(width, height)
                .placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no)
                .into(imageView);
    }

    @Override
    public void displayImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url)
                .placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no)
                .into(imageView);
    }

    @Override
    public void displayImageNoPlaceholder(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url)
                //.placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no)
                .into(imageView);
    }

    @Override
    public void displayImage(ImageView imageView, int imageResId) {
        Picasso.with(imageView.getContext()).load(imageResId)
                .placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, final ILoadImageCallback loadImageCallback) {
        Picasso.with(KooApplication.getAppContext()).load(url)
                .placeholder(R.mipmap.ic_image_no)
                .error(R.mipmap.ic_image_no)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        loadImageCallback.onLoadImageBack(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
