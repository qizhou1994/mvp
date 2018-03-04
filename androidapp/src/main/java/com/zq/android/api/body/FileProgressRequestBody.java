package com.zq.android.api.body;

import com.zq.android.listener.FileProgressListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Sink;

/**
 * Created by hdly on 2017/12/21.
 */
public class FileProgressRequestBody extends RequestBody {

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    private RequestBody mRequestBody;
    private FileProgressListener mProgressListener;
    private File mFile;

    private BufferedSink bufferedSink;

    public FileProgressRequestBody() {
    }

    public FileProgressRequestBody(File mFile, RequestBody mRequestBody, FileProgressListener mProgressListener) {
        this.mFile = mFile;
        this.mRequestBody = mRequestBody;
        this.mProgressListener = mProgressListener;
    }

    //返回了requestBody的类型，想什么form-data/MP3/MP4/png等等等格式
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    //返回了本RequestBody的长度，也就是上传的totalLength
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                // update progress on UI thread
                mProgressListener.onProgress(uploaded, fileLength, uploaded == uploaded);
            }
            // update progress on UI thread,这里不写就只有99
            mProgressListener.onProgress(uploaded, fileLength, uploaded == uploaded);
        } finally {
            in.close();
        }

    }

    // 上传
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调上传接口
                mProgressListener.onProgress(bytesWritten, contentLength, bytesWritten == contentLength);
            }
        };
    }
}
