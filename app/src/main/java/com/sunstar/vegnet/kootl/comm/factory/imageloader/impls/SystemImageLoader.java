package com.sunstar.vegnet.kootl.comm.factory.imageloader.impls;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.louisgeek.checkappupdatelib.tool.ThreadUtil;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.IImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.ILoadImageCallback;
import com.sunstar.vegnet.kootl.comm.app.KooImagesLoader;
import com.sunstar.vegnet.kootl.comm.helper.VectorOrImageResHelper;
import com.sunstar.vegnet.kootl.comm.tool.ImageTool;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class SystemImageLoader implements IImageLoader {
    @Override
    public void displayImage(ImageView imageView, String url, int width, int height) {
        KooImagesLoader.getInstance().loadImage(url, imageView, width, height);
    }

    @Override
    public void displayImage(ImageView imageView, String url) {
        KooImagesLoader.getInstance().loadImage(url, imageView);
    }

    @Override
    public void displayImageNoPlaceholder(ImageView imageView, String url) {
        // TODO: 2016/12/28
        displayImage(imageView, url);
    }

    @Override
    public void displayImage(ImageView imageView, int imageResId) {
        //### imageView.setImageResource(imageResId);
        Drawable drawable = VectorOrImageResHelper.getDrawable(imageView.getContext(), imageResId);
        imageView.setImageDrawable(drawable);
    }

    @Override
    public void loadImage(final String url, final ILoadImageCallback loadImageCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO: 2016/12/28
                final Bitmap bitmap = ImageTool.downloadImageToBitmapWishImageSize(url, 200, 200);
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadImageCallback.onLoadImageBack(bitmap);
                    }
                });
            }
        }).start();
    }
}
