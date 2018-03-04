package com.zq.library.app;

import android.app.Application;

import com.zq.library.BuildConfig;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;

/**
 * @version V1.0
 * @des
 * @author: zq
 * @email qizhou1994@126.com
 * @date: 2017-05-27 16:22
 */
public class BaseApp extends Application {
    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        // 图片加载
        // Fresco.initialize(this);
        // 内存泄漏检测工具
        refWatcher = LeakCanary.install(this);
        //
        ButterKnife.setDebug(BuildConfig.DEBUG);

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

}