package com.example.admin.carsharing.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Class Name:
 * Created by zlt .
 * 简介：常用单位转换的辅助类
 * Data： 2018/2/1.
 */

public class DensityUtils {

    private DensityUtils(){
        // 不能被实例化
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wM.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
