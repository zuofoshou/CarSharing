package com.example.admin.carsharing.util;

import com.example.admin.carsharing.application.BaseApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Class Name:
 * Created by zlt .
 * 简介：
 * Data： 2018/2/4.
 */

public class OkHttpUtils {
    private static OkHttpClient.Builder singleton;
    public static OkHttpClient getInstance(){
        if (singleton == null){
            synchronized (OkHttpUtils.class){
                if (singleton == null){
                    singleton = new OkHttpClient().newBuilder();
                    File cacheDir = new File(BaseApplication.getApplicationCotext().getCacheDir(), "okhttp/cache");
                    try {
                        singleton.cache(new Cache(cacheDir, 1024 * 1024 * 10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    singleton.connectTimeout(10, TimeUnit.SECONDS);
                    singleton.readTimeout(10, TimeUnit.SECONDS);
                    singleton.writeTimeout(20, TimeUnit.SECONDS);
                }
            }
        }
        return singleton.build();
    }
}
