package com.zq.android.injector.component;

import com.zq.android.injector.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:03
 */
@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
}
