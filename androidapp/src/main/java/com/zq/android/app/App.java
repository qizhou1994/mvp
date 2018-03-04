package com.zq.android.app;

import android.app.Application;

import com.zq.android.BuildConfig;
import com.zq.android.injector.component.ApiComponent;
import com.zq.android.injector.component.AppComponent;
import com.zq.android.injector.component.DaggerApiComponent;
import com.zq.android.injector.component.DaggerAppComponent;
import com.zq.android.injector.module.ApiModule;
import com.zq.android.injector.module.AppModule;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;

/**
 * @des: Application
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 16:22
 */
public class App extends Application {
    private static RefWatcher refWatcher;
    private AppComponent appComponent;
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // 图片加载
        Fresco.initialize(this);
        // 内存泄漏检测工具
        refWatcher = LeakCanary.install(this);
        //
        ButterKnife.setDebug(BuildConfig.DEBUG);
        // DI
        initializeComponent();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }

    private void initializeComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

}
