package com.zq.android.base;

import com.zq.android.BuildConfig;
import com.zq.android.api.Api;
import com.zq.android.api.interceptor.FileProgressInterceptor;
import com.zq.android.bean.ChatMessage;
import com.zq.android.listener.FileProgressListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hdly on 2017/12/20.
 */
public abstract class BaseFileModel<API> {

    private static final String TAG = "BaseFileModel";

    public static final String SECRET_KEY = "B0D1A38457131A835A1B21B2D945C767785EA61AA4D0ECA1EB0BA4ADAF248375";
    public static final String IDENTITY_ID = "8EDC084895550355A05E3CAAD05C484D";

    private API mService;
    private FileProgressListener listener;
    private ChatMessage chatMessage;
    private boolean isNeedResolve;

    public BaseFileModel() {
    }

    public BaseFileModel(ChatMessage chatMessage, FileProgressListener listener) {
        this(chatMessage, listener, true);
    }

    public BaseFileModel(ChatMessage chatMessage, FileProgressListener listener,  boolean isNeedResolve) {
        this.listener = listener;
        this.chatMessage = chatMessage;
        this.isNeedResolve = isNeedResolve;
        initRetrofit();
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(loggingInterceptor);

        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("secret-key", SECRET_KEY)
                        .addHeader("identity-id", IDENTITY_ID)
                        .build();
                return chain.proceed(request);
            }
        });

        // 添加监听,获取进度
        if (listener != null)
            okHttpBuilder.addInterceptor(new FileProgressInterceptor(listener));

        OkHttpClient okHttpClient = okHttpBuilder.build();
        okHttpClient.newBuilder().connectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    private void initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)// 替换成自己的
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getClient());
        // 是否需要解析
        if (isNeedResolve) {
            builder = builder.addConverterFactory(GsonConverterFactory.create());
        }

        Retrofit retrofit = builder.build();

        mService = retrofit.create(getApiClass());
    }

    public API getService() {
        return mService;
    }

    protected abstract Class<API> getApiClass();

}
