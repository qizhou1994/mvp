package com.zq.android.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.zq.android.injector.component.AppComponent;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 16:24
 */
public interface IBaseActivity {
    /**
     * 绑定渲染视图的布局文件
     * @return 布局文件资源id
     */
    public int bindLayout();

    /**
     * 初始化界面参数
     * @param parms
     */
    public void getBundleExtras(Bundle parms);

    /**
     * 初始化控件
     */
    public void initViewsAndEvents(final View view);

    /**
     * 业务处理操作（onCreate方法中调用）
     * @param mContext  当前Activity对象
     */
    public void doBusiness(Context mContext);

    /**
     * 注入
     */
    public void injectModule(AppComponent appComponent);

    /**
     * 获取当前上下文对象
     * @return
     */
    Context getContext();

}
