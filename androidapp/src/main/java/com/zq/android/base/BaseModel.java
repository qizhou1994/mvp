package com.zq.android.base;

import android.support.annotation.NonNull;

import com.zq.android.BuildConfig;
import com.zq.android.api.Api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:30
 */
public abstract class BaseModel<API> {
    private API mService;

    public BaseModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getClient())
                .build();

        mService = retrofit.create(getApiClass());
    }

    @NonNull
    private OkHttpClient getClient() {
        // 日志拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(loggingInterceptor);

        // 请求拦截（添加header）
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("header1", "content1")
                        .addHeader("header2", "content2")
                        .addHeader("header3", "content1")
                        .build();
                return chain.proceed(request);
            }
        });

        return okHttpBuilder.build();
    }
    public API getService() {
        return mService;
    }
    protected abstract Class<API> getApiClass();

}

