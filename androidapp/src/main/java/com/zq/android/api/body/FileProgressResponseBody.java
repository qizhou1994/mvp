package com.zq.android.api.body;

import com.zq.android.listener.FileProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by hdly on 2017/12/20.
 */
public class FileProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final FileProgressListener listener;
    private BufferedSource bufferedSource;

    public FileProgressResponseBody(ResponseBody responseBody, FileProgressListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) bufferedSource = Okio.buffer(source(responseBody.source()));
        return bufferedSource;
    }

    // 下载
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (listener != null )
                    listener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }

    
}
