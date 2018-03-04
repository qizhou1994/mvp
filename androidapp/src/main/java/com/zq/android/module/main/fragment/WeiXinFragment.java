package com.zq.android.module.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.zq.android.base.BaseFragment;
import com.zq.android.module.main.presenter.WeiXinPresenter;
import com.zq.android.module.main.view.WeiXinView;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-20 10:53
 */
public class WeiXinFragment extends BaseFragment<WeiXinPresenter> implements WeiXinView {
    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
