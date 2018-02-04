package com.example.admin.carsharing.application;

import android.app.Application;
import android.content.Context;

import com.example.admin.carsharing.util.CrashHandler;
import com.example.admin.carsharing.util.RetrofitUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

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
        Fresco.initialize(this);

    }

    public static Context getApplicationCotext(){
        return mContext.getApplicationContext();
    }
}
