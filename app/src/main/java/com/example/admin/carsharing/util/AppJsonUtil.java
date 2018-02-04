package com.example.admin.carsharing.util;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import com.example.admin.carsharing.domain.ResultInfo;

/**
 * Class Name:
 * Created by zlt .
 * 简介：
 * Data： 2018/2/2.
 */

public class AppJsonUtil {
    /**
     * 返回默认实体类
     * @param result
     * @return
     */
    public static ResultInfo getResultInfo(String result){
        ResultInfo info = JSON.parseObject(result, ResultInfo.class);
        return  info;
    }

    /**
     * 返回实体类
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> T getObject(String result, Class<T> clazz){
        T t = JSON.parseObject(getResultInfo(result).getData(),clazz);
        return  t;
    }

    /**
     * 返回数组实体类
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> List<T> getArrayList(String result, Class<T> clazz){
        List<T> list = JSON.parseArray(getResultInfo(result).getData(), clazz);
        return  list;
    }
    /**
     * 返回数组实体类
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> List<T> getArrayList(String result, String key, Class<T> clazz){
        try {
            JSONObject jsonObject=new JSONObject(getResultInfo(result).getData());

            List<T> list = JSON.parseArray(jsonObject.getString(key), clazz);
            return  list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回String
     * @param result
     * @param key
     * @return
     */
    public static  String getString(String result,String key){
        try {
            JSONObject jsonObject=new JSONObject(getResultInfo(result).getData());
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
