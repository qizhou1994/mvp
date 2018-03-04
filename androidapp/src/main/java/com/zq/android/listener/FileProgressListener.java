package com.zq.android.listener;

/**
 * Created by hdly on 2017/12/20.
 */
public interface FileProgressListener {

    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);

}
