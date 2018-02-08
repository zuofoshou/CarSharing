package com.example.admin.carsharing.ui.firstpage;

import android.view.View;
import android.widget.Button;
import com.example.admin.carsharing.R;
import com.example.admin.carsharing.ui.BaseFgt;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Class Name:首页
 * Created by zlt .
 * 简介：
 * Data： 2018/2/3.
 */

public class FirstPageFgt extends BaseFgt{

    @Bind(R.id.btn_firdtp_confirm)
    Button btnConfirm;

    @Override
    public int getLayoutId() {
        return R.layout.main_first_page;
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_firdtp_confirm)
    @Override
    public void btnClick(View view) {
        super.btnClick(view);
        switch (view.getId()){
            case R.id.btn_firdtp_confirm:
                startActivity(CarsList.class, null);
                break;
        }
    }

    @Override
    public void requestData() {

    }
}
