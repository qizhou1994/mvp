package com.zq.android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.zq.android.R;
import com.zq.android.app.App;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * @des: Activity基类
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 16:24
 */
public abstract class BaseActivity <P extends Presenter> extends NucleusAppCompatActivity<P> implements IBaseActivity {
    protected final String TAG = getClass().getSimpleName();

    /**当前Activity渲染的视图View**/
    private View mContextView = null;
    /**当前Activity的弱引用，防止内存泄露**/
    private WeakReference<Activity> context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 屏幕方向固定
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //将当前Activity压入栈
        context = new WeakReference<Activity>(this);

        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);

        injectModule(((App)getApplication()).getAppComponent());

        // 注解绑定
        ButterKnife.bind(this);

        setPresenterFactory(getPresenterFactory());

        //初始化参数
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

        }else{
            bundle = new Bundle();
        }
        getBundleExtras(bundle);

//        // 开启沉浸式状态栏
//        StatusBarCompat.compat(this);

        //初始化控件
        initViewsAndEvents(mContextView);

        //业务操作
        doBusiness(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher();
        refWatcher.watch(this);
    }


    /**********************************************************************************************/
    /*****************                   Activity跳转                   ***************************/
    /**********************************************************************************************/
    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    /**********************************************************************************************/
    /*****************                   Activity跳转                   ***************************/
    /**********************************************************************************************/



    /**********************************************************************************************/
    /*****************              对话框以及加载进度条                   ***************************/
    /**********************************************************************************************/
    public SweetAlertDialog mProgressDialog;
    public SweetAlertDialog mWarningDialog;
    public SweetAlertDialog mErrorDialog;

    public void showWarningDialog(String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        mWarningDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确定")
                .setCancelText("取消")
                .setConfirmClickListener(listener);

        mWarningDialog.show();
    }

    public void showErrorDialog(String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        mErrorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("确定")
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(listener);
        mErrorDialog.show();
    }

    /**
     * 加载进度条
     * @param message
     */
    public void showProgressDialog(String message) {
        mProgressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mProgressDialog.setTitleText(message);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();

    }

    /**
     * 加载进度
     * @param message
     * @param progress
     */
    public void showProgressDialog(String message, int progress) {
        mProgressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mProgressDialog.setTitleText(message);
        mProgressDialog.setCancelable(true);
        mProgressDialog.getProgressHelper().setProgress(progress);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    /**********************************************************************************************/
    /*****************              对话框以及加载进度条                   ***************************/
    /**********************************************************************************************/
}
