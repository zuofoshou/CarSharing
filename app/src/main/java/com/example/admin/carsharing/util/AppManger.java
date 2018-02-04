package com.example.admin.carsharing.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * Class Name:Activity管理类
 * Created by zlt .
 * 简介：管理Activity管理和应用程序退出
 * Data： 2018/1/30.
 */

public class AppManger {

    private static Stack<Activity> mActivityStack;
    private static AppManger mAppManager;
    private AppManger(){

    }

    /**
     * 单一实例
     * @return
     */
    public static AppManger getInstance(){
        if (mAppManager == null){
            synchronized (AppManger.class){
                if (mAppManager == null){
                    mAppManager = new AppManger();
                }
            }
        }
        return mAppManager;
    }

    /**
     * 查看某个activity是否被添加过
     * @param cls
     * @return
     */
    public boolean isAddActivity(Class<?> cls){
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()){
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)){
                return true;
            }
        }
        return false;
    }

    /**
     * 添加activity到堆栈
     * @param activity
     */
    public void addActivity(Activity activity){
        if (mActivityStack == null){
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 关闭所选activity之外的所有activity
     * @param activity
     */
    public void KillexceptActivity(Activity activity){
        int size = mActivityStack.size();
        for (int i = size-1; i >=0 ; i--) {
            if (mActivityStack.get(i) != activity){
                mActivityStack.get(i).finish();
                mActivityStack.remove(i);
            }
        }
    }

    /**
     * 获取栈顶Activity（即最后压入的）
     * @return
     */
    public Activity getTopActivity(){
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity
     */
    public void KillTopActivity(){
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     * @param activity
     */
    public void killActivity(Activity activity){
        if (activity != null){
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void killActivity(Class<?> cls){
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()){
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)){
                iterator.remove();
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void killAllActivity(){
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)){
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 是否打开APP，用于点击通知的情况下。
     * @return
     */
    public boolean isOpenActivity(){
        if (mActivityStack == null){
            return false;
        } else if (mActivityStack.size() == 0){
            return false;
        }
        return true;
    }

    /**
     * 退出应用程序
     * @param context
     */
    public void AppExit(Context context){
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
