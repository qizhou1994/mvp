package com.zq.android.bean.file;

import com.zq.android.base.BaseFileEntity;

import java.io.Serializable;

/**
 * Created by hdly on 2017/12/19.
 */
public class FileObjAckBase<T> extends BaseFileEntity implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
