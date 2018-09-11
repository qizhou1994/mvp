package com.zq.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zq.android.app.App;
import com.zq.android.injector.component.ApiComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 18:31
 */
public abstract class BaseFragment<P extends Presenter> extends NucleusSupportFragment<P> implements IBaseFragment, IBaseView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenterFactory(getPresenterFactory());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(bindLayout(), container, false);
		//绑定
		  ButterKnife.bind(this, rootView);
        // 初始化控件
        initViews(rootView,savedInstanceState);
        // 业务操作
        doBusiness(getActivity());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public Context getContext() {
        return getActivity();
    }



    /**
     * 注入p层
     */
    protected void injectorPresenter() {
    }

    //
    protected ApiComponent getApiComponent() {
        return ((App) getActivity().getApplication()).getApiComponent();
    }


}
