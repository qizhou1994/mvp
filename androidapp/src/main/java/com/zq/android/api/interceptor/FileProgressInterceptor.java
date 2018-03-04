package com.zq.android.api.interceptor;

import com.zq.android.api.body.FileProgressResponseBody;
import com.zq.android.listener.FileProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by hdly on 2017/12/20.
 */
public class FileProgressInterceptor implements Interceptor {

    private FileProgressListener listener;

    public FileProgressInterceptor(FileProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new FileProgressResponseBody(originalResponse.body(), listener))
                .build();
    }
}
