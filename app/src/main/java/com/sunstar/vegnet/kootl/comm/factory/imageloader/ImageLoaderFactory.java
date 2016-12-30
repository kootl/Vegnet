package com.sunstar.vegnet.kootl.comm.factory.imageloader;

import com.sunstar.vegnet.kootl.comm.factory.imageloader.impls.GlideImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.impls.PicassoImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.impls.SystemImageLoader;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.IImageLoader;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class ImageLoaderFactory {

    /**
     * 2016年12月28日13:52:01
     * 静态内部类实现单例模式方案的改写
     */
    private static class ImageLoaderFactoryInner4Glide {
        private static final GlideImageLoader INSTANCE = new GlideImageLoader();
    }

    /**
     * @return
     */
    public static IImageLoader getManager() {
        return ImageLoaderFactoryInner4Glide.INSTANCE;
        //  return ImageLoaderFactoryInner4Picasso.INSTANCE;
    }


    private static class ImageLoaderFactoryInner4System {
        private static final SystemImageLoader INSTANCE = new SystemImageLoader();
    }

    /***
     * 不支持gif加载，只显示第一帧
     */
    private static class ImageLoaderFactoryInner4Picasso {
        private static final PicassoImageLoader INSTANCE = new PicassoImageLoader();
    }
}
