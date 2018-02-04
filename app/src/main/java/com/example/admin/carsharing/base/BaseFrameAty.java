package com.example.admin.carsharing.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.example.admin.carsharing.R;
import com.example.admin.carsharing.config.SavePath;
import com.example.admin.carsharing.dialog.LoadingDialog;
import com.example.admin.carsharing.http.HttpCallBack;
import com.example.admin.carsharing.util.AppManger;
import com.example.admin.carsharing.util.CrashHandler;
import com.example.admin.carsharing.util.DensityUtils;
import com.example.admin.carsharing.util.NetWorkUtils;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.logger.Logger;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class Name:
 * Created by zlt .
 * 简介：所有Activity的基类
 * Data： 2018/1/29.
 */

public abstract class BaseFrameAty extends AppCompatActivity implements HttpCallBack {


    /**
     * 系统容器布局
     */
    private FrameLayout content;
    /**
     * 请求网络全屏弹框
     */
    private LinearLayout mLoadingContent;
    /**
     * 无网络全屏弹框
     */
    private View errorView;
    private LoadingDialog mLoadingDialog;
    protected boolean isInitRequestData = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManger.getInstance().addActivity(this);
        isInitRequestData = setIsInitRequestData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.frame_base_layout);
        content = (FrameLayout) findViewById(R.id.content);
        if (getLayoutId() != -1) {
            View childView = getLayoutInflater().inflate(getLayoutId(), null, false);
            if (childView.getParent() != content) {
                content.addView(childView);
            }
        }
        ButterKnife.bind(this);
        initData();
        initRequestMethod();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //-----------------------------------------网络相关----------------------------------

    /**
     * 初始化网络
     */
    private void initRequestMethod() {
        if (isInitRequestData) {
            // 判断网络是否可用
            if (NetWorkUtils.isNetWorkConnected(this)) {
                requestData();
            } else {
                showNetWorkErrorPage();
            }
        }
    }

    /**
     * 网络连接错误时显示的界面
     */
    private void showNetWorkErrorPage() {
        dismissLoadingContentDialog();
        if (errorView != null && errorView.getParent() == content) {
            content.removeView(errorView);
            content.addView(errorView);
        } else {
            errorView = getLayoutInflater().inflate(R.layout.frame_error_layout, null, false);
            errorView.setClickable(true);
            content.addView(errorView);
            Button btn_refresh = (Button) errorView.findViewById(R.id.fre_btn_refresh);
            btn_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetWorkUtils.isNetWorkConnected(BaseFrameAty.this)) {
                        requestData();
                    } else {
                        showToast("请检查网络连接");
                    }
                }
            });
        }
    }

    /**
     * 隐藏网络错误界面
     */
    private void hideNetWorkErrorPage() {
        if (errorView != null && errorView.getParent() == content) {
            content.removeView(errorView);
        }
    }

    //-----------------------------------------方法相关----------------------------------

    /**
     * 点击事件母方法
     *
     * @param view
     */
    public abstract void btnClick(View view);

    protected abstract void requestData();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected abstract boolean setIsInitRequestData();

    /**
     * 绑定布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 弹吐司
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //-----------------------------------------Dialog相关----------------------------------

    /**
     * 隐藏该进度条
     */
    public void dismissLoadingContentDialog() {
        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
        }
    }

    /**
     * 可以显示信息的Dialog
     *
     * @param message
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingDialog.showLoadingDialog(message);
    }

    /**
     * 全屏遮蔽的进度条
     *
     * @param spacingTop    到顶部的距离（单位dp)
     * @param spacingBottom 到底部的距离（单位dp）
     */
    public void showLoadingContentDialog(int spacingTop, int spacingBottom) {
        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);
        } else {
            mLoadingContent = (LinearLayout) getLayoutInflater().inflate(R.layout.frame_loading_content_dialog, null, false);
            if (spacingTop >= 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(this, spacingTop);
                params.bottomMargin = DensityUtils.dp2px(this, spacingBottom);
                spacingView.setLayoutParams(params);
            } else if (spacingTop >= 0 && spacingBottom < 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(this, spacingTop);
                spacingView.setLayoutParams(params);
            } else if (spacingTop < 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.bottomMargin = DensityUtils.dp2px(this, spacingBottom);
                spacingView.setLayoutParams(params);
            }
            content.addView(mLoadingContent);
        }
    }

    /**
     * 显示全屏遮盖的进度条(toolbar可以显示出来默认56dp)
     */
    public void showLoadingContentDialog() {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);
        } else {
            mLoadingContent = (LinearLayout) getLayoutInflater().inflate(R.layout.frame_loading_content_dialog, null, false);
            content.addView(mLoadingContent);
        }
    }

    /**
     * 隐藏进度条
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            //结束掉网络请求
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        ButterKnife.unbind(this);
        AppManger.getInstance().killActivity(this);
    }

    //-----------------------------------------Activity跳转----------------------------------

    public boolean isHasAnimiation() {
        return hasAnimiation;
    }

    public void setHasAnimiation(boolean hasAnimiation) {
        this.hasAnimiation = hasAnimiation;
    }

    private boolean hasAnimiation = true;

    /**
     * 跳转activity
     *
     * @param className
     * @param options
     */
    public void startActivity(Class<?> className, Bundle options) {
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    }

    /**
     * 待返回值的跳转
     *
     * @param className
     * @param options
     * @param requestCode
     */
    public void startActivityForResult(Class<?> className, Bundle options, int requestCode) {
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivityForResult(intent, requestCode);
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_right_in,
                    R.anim.slide_left_out);
        }
    }

    /**
     * 关闭activity
     */
    public void finish() {
        super.finish();
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_right_out);
        }
    }

    //-----------------------------------------网络请求方法----------------------------------


    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        Logger.json(result);
        dismissLoadingDialog();
        dismissLoadingContentDialog();
        hideNetWorkErrorPage();
    }

    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        Logger.json(result);
        dismissLoadingDialog();
        dismissLoadingContentDialog();
        hideNetWorkErrorPage();
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        dismissLoadingDialog();
        dismissLoadingContentDialog();
        CrashHandler.getInstance().saveCrashInfo2File(t, SavePath.savePath + "zltHttpError/crash");
    }

    public void doHttp(Call<ResponseBody> bodyCall, final int what){
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    Logger.json(result);
                    JSONObject object = JSONObject.parseObject(result);
                    if (object.getString("flag").equals("error")){
                        BaseFrameAty.this.onFailure(result, call, response, what);
                    } else {
                        BaseFrameAty.this.onSuccess(result, call, response, what);
                    }
                } catch (Exception e) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    e.printStackTrace(new PrintStream(baos));
                    String exception = baos.toString();
                    Logger.w(exception);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.w(t.getMessage() + call.request().url().toString());
                BaseFrameAty.this.onError(call, t, what);
            }
        });
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}

