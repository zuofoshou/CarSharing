package com.example.admin.carsharing.ui;

import android.view.View;
import com.example.admin.carsharing.base.BaseFrameAty;
import com.example.admin.carsharing.domain.ResultInfo;
import com.example.admin.carsharing.util.AppJsonUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class Name:
 * Created by zlt .
 * 简介：
 * Data： 2018/1/29.
 */

public abstract class BaseAty extends BaseFrameAty {

    public boolean isShowToast = true;

    @Override
    public void btnClick(View view) {

    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        if (isShowToast) {
            ResultInfo resultInfo = AppJsonUtil.getResultInfo(result);
            showToast(resultInfo.getMessage());
        }
        super.onFailure(result, call, response, what);
    }

    @Override
    protected boolean setIsInitRequestData() {
        return false;
    }

}
