package com.zq.android.model;

import com.zq.android.api.Api;
import com.zq.android.api.MainApi;
import com.zq.android.base.BaseModel;
import com.zq.android.data.MainEntity;

import rx.Observable;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:29
 */
public class MainModel extends BaseModel<MainApi> {

    @Override
    protected Class<MainApi> getApiClass() {
        return MainApi.class;
    }

    // 获取微信精选数据
    public Observable<MainEntity.WeiXinNews> getWeiXinNews(int pageIndex) {
        return getService().getWeiXinNews(Api.KEY_WEIXIN, pageIndex,
                Api.PAGE_SIZE, Api.DATA_TYPE);
    }
}
