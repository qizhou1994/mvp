package com.zq.android.model;

import com.zq.android.api.MainApi;
import com.zq.android.api.body.FileProgressRequestBody;
import com.zq.android.base.BaseFileModel;
import com.zq.android.bean.ChatMessage;
import com.zq.android.bean.file.FileObjAckBase;
import com.zq.android.bean.file.ImageFileObj;
import com.zq.android.listener.FileProgressListener;
import com.zq.android.utils.L;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by hdly on 2017/12/21.
 */
public class FileModel extends BaseFileModel<MainApi> {

    public FileModel() {
    }

    public FileModel(ChatMessage chatMessage, FileProgressListener listener) {
        super(chatMessage, listener);
    }

    public FileModel(ChatMessage chatMessage, FileProgressListener listener, boolean isNeedResolve) {
        super(chatMessage, listener, isNeedResolve);
    }

    @Override
    protected Class getApiClass() {
        return MainApi.class;
    }

    // 上传文件（fastdfs）
    public Observable<FileObjAckBase<ImageFileObj>> uploadFileFastDFS(File fileName, FileProgressListener progressListener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileName);
        FileProgressRequestBody fileProgressRequestBody = new FileProgressRequestBody(fileName, requestBody, progressListener);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", fileName.getName(), fileProgressRequestBody);
        try {
            L.i("uploadFileFastDFS", requestBody.contentLength() + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getService().uploadFileFastDFS("自己的文件url", body);
    }

    // 下载文件(fastdfs)
    public Observable<ResponseBody> downloadFileFastDFS(String fileUrl) {
        return getService().downloadFileFastDFS(fileUrl);
    }
}
