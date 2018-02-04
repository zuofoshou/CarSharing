package com.example.admin.carsharing.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.admin.carsharing.R;

/**
 * Class Name:
 * Created by zlt .
 * 简介：
 * Data： 2018/1/31.
 */

public class LoadingDialog extends BaseDialog {

    TextView tv_message;

    public LoadingDialog(Context context) {
        super(context, 0);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        View loadingView = LayoutInflater.from(context).inflate(R.layout.frame_loading_dialog, null, false);
        tv_message = (TextView) loadingView.findViewById(R.id.frame_tv_message);
        setContentView(R.layout.frame_loading_dialog);
    }

    public void showLoadingDialog(String message){
        if (!TextUtils.isEmpty(message)){
            tv_message.setText(message);
        }
        show();
    }
}
