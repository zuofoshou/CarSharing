package com.example.admin.carsharing.dialog;

import android.app.Dialog;
import android.content.Context;

import com.example.admin.carsharing.R;

/**
 * Class Name:
 * Created by zlt .
 * 简介：对话框的基类
 * Data： 2018/1/31.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        this(context, R.style.dialog_untran);
        setCanceledOnTouchOutside(false);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, R.style.dialog_untran);
        setCanceledOnTouchOutside(false);
    }

}
