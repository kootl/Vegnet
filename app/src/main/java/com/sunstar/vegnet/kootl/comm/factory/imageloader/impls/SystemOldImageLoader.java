package com.sunstar.vegnet.kootl.comm.factory.imageloader.impls;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.louisgeek.checkappupdatelib.tool.ThreadUtil;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.IImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.ILoadImageCallback;
import com.sunstar.vegnet.kootl.comm.helper.VectorOrImageResHelper;
import com.sunstar.vegnet.kootl.comm.tool.ImageTool;

/**
 * Created by louisgeek on 2016/12/28.
 */
@Deprecated //不使用
public class SystemOldImageLoader implements IImageLoader {
    @Override
    public void displayImage(final ImageView imageView, final String url, final int width, final int height) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = ImageTool.downloadImageToBitmapWishImageSize(url, width, height);
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    @Override
    public void displayImage(final ImageView imageView, final String url) {
        // TODO: 2016/12/28
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = ImageTool.downloadImageToBitmapWishImageSize(url, 200, 200);
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    @Override
    public void displayImageNoPlaceholder(ImageView imageView, String url) {
        // TODO: 2016/12/28
        displayImage(imageView,url);
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
