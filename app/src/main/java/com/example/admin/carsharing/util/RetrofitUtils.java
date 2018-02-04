package com.example.admin.carsharing.util;

import com.example.admin.carsharing.config.HttpConfig;

import retrofit2.Retrofit;

/**
 * Class Name:
 * Created by zlt .
 * 简介：接口工具
 * Data： 2018/2/4.
 */

public class RetrofitUtils {
    private static Retrofit singleton;

    public static <T> T createApi(Class<T> tClass){
        return singleton.create(tClass);
    }

    public static Retrofit getInstance(){
        return singleton;
    }

    public static void init(){
        if (singleton == null){
            synchronized (RetrofitUtils.class){
                if (singleton == null){
                    singleton = new Retrofit.Builder().baseUrl(HttpConfig.BASE_URL)
                            .client(OkHttpUtils.getInstance())
                            .build();
                }
            }
        }
    }
}
