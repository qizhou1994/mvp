package com.zq.android.injector.module;

import android.app.Application;
import android.content.Context;

import com.zq.android.app.App;
import com.zq.android.injector.qualifier.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 16:58
 */
@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApplication
    App provideApp() {
        return app;
    }


    @ForApplication
    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @ForApplication
    @Provides
    @Singleton
    public Context provideContext() {
        return app.getApplicationContext();
    }

}
