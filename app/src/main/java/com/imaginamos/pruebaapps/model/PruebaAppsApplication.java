package com.imaginamos.pruebaapps.model;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class PruebaAppsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        initImageLoader(getApplicationContext());
        Fresco.initialize(this);
    }

//    public static void initImageLoader(Context context) {
//// This configuration tuning is custom. You can tune every option, you may tune some of them,
//// or you can create default configuration by
////  ImageLoaderConfiguration.createDefault(this);
//// method.
//
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//
//        .cacheInMemory(true)
//                .cacheOnDisk(true)
//
//        .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//
//        .defaultDisplayImageOptions(defaultOptions)
//
//        .build();
//        ImageLoader.getInstance().init(config);
//
//
////        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
////        config.threadPriority(Thread.NORM_PRIORITY - 2);
////        config.denyCacheImageMultipleSizesInMemory();
////        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
////        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
////        config.tasksProcessingOrder(QueueProcessingType.LIFO);
////        config.writeDebugLogs(); // Remove for release app
////
////// Initialize ImageLoader with configuration.
////        ImageLoader.getInstance().init(config.build());
//    }
}