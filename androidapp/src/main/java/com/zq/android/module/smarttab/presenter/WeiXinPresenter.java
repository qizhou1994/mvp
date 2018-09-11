package com.zq.android.module.smarttab.presenter;

import android.os.Bundle;


import com.zq.android.api.MainApi;
import com.zq.android.base.BasePresenter;
import com.zq.android.data.MainEntity;
import com.zq.android.model.MainModel;
import com.zq.android.model.SchedulerTransformer;
import com.zq.android.module.smarttab.fragment.RecommendedFragment;
import com.zq.android.utils.L;

import javax.inject.Inject;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * Created by zq on 2018/9/11.
 */

public class WeiXinPresenter extends BasePresenter<RecommendedFragment> {


    @Inject
    MainModel mainModel;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        if (mainModel == null) {
            mainModel = new MainModel();
        }

        restartableFirst(1, new Func0<Observable<MainEntity.WeiXinNews>>() {
            @Override
            public Observable<MainEntity.WeiXinNews> call() {
                return mainModel.getWeiXinNews(1).
                        compose(new SchedulerTransformer<MainEntity.WeiXinNews>());
            }
        }, new Action2<RecommendedFragment, MainEntity.WeiXinNews>() {
            @Override
            public void call(RecommendedFragment baseFragment, MainEntity.WeiXinNews t) {
                baseFragment.successLoad(t);
            }
        }, new Action2<RecommendedFragment, Throwable>() {
            @Override
            public void call(RecommendedFragment baseFragment, Throwable throwable) {
                L.e("fail = " + throwable);
                throwable.printStackTrace();
            }
        });
    }


    public void loadData() {
        start(1);
    }

}
