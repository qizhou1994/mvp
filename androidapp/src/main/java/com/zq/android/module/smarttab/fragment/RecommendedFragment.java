package com.zq.android.module.smarttab.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zq.android.R;
import com.zq.android.base.BaseFragment;
import com.zq.android.data.MainEntity;
import com.zq.android.module.smarttab.presenter.WeiXinPresenter;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

/**
 * @version V1.0
 * @des
 * @author: zq
 * @email qizhou1994@126.com
 * @date: 2017-05-27 17:20
 */
@RequiresPresenter(WeiXinPresenter.class)
public class RecommendedFragment extends BaseFragment<WeiXinPresenter> {


    private TextView recommended_tv;

    @Override
    public int bindLayout() {
        return R.layout.fragment_recommended;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        recommended_tv = (TextView) view.findViewById(R.id.recommended_tv);
    }

    @Override
    public void doBusiness(Context mContext) {
//        getPresenter().loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().loadData();
    }

    @Override
    protected void injectorPresenter() {
       getApiComponent().inject(getPresenter());
    }

    public void successLoad(MainEntity.WeiXinNews weiXinNews){
        recommended_tv.setText("success\n" + weiXinNews);
    }


}
