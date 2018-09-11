package com.zq.android.injector.module;

import com.zq.android.model.MainModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 16:59
 */
@Module
public class ApiModule {
    @Provides
    @Singleton
    MainModel provideMainModel() {
        return new MainModel();
    }
}
