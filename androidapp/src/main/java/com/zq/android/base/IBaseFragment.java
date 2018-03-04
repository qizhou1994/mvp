package com.zq.android.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-20 10:44
 */
public interface IBaseFragment {
    /**
     * 绑定渲染视图的布局文件
     * @return 布局文件资源id
     */
    public int bindLayout();
    /**
     * 初始化控件
     */
    public void initViews(final View view, Bundle savedInstanceState);

    /**
     * 业务处理操作（onCreate方法中调用）
     * @param mContext  当前Activity对象
     */
    public void doBusiness(Context mContext);

    /**
     * 获取当前上下文对象
     * @return
     */
    Context getContext();
}
