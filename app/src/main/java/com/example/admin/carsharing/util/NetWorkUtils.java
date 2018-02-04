package com.example.admin.carsharing.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class Name:
 * Created by zlt .
 * 简介：主要是判断是否有网络连接
 * Data： 2018/1/31.
 */

public class NetWorkUtils {
    public static boolean isNetWorkConnected(Context context){
        if (context != null){
            // 这个类主要用于查询网络状态、执行相关操作
            ConnectivityManager mConnectityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            // 这是一个描述网络状态的接口
            NetworkInfo mNetWorkInfo = mConnectityManager.getActiveNetworkInfo();
            if (mNetWorkInfo != null){
                return mNetWorkInfo.isAvailable();
            }
        }
        return false;
    }
}
