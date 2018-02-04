package com.example.admin.carsharing.domain;

/**
 * Class Name:
 * Created by zlt .
 * 简介：返回的标准数据类型实体类
 * Data： 2018/2/2.
 */

public class ResultInfo {
    private String flag;
    private String message;
    private String data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResultInfo(String flag, String message, String data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public ResultInfo() {
    }
}
