package com.example.admin.carsharing.application;

import android.app.Application;
import android.content.Context;
import com.example.admin.carsharing.config.SavePath;
import com.example.admin.carsharing.util.CrashHandler;
import com.example.admin.carsharing.util.RetrofitUtils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * Class Name:
 * Created by zlt .
 * 简介：
 * Data： 2018/2/3.
 */

public class BaseApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        mContext = this;
        CrashHandler.getInstance().init(this);
        RetrofitUtils.init();
        initPresco();

    }

    private void initPresco() {
        final File file = new File(SavePath.savePath + "cache/");
        if (!file.exists()){
            file.mkdirs();
        }
        DiskCacheConfig.Builder diskCache = DiskCacheConfig.newBuilder(mContext)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return file;
                    }
                })
                .setBaseDirectoryName("image_cache")
                .setMaxCacheSize(40 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB);
        ImagePipelineConfig.Builder config = ImagePipelineConfig.newBuilder(mContext);
        config.setMainDiskCacheConfig(diskCache.build());
        Fresco.initialize(this, config.build());
    }

    public static Context getApplicationCotext(){
        return mContext.getApplicationContext();
    }
}
