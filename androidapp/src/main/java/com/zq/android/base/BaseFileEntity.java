package com.zq.android.base;

/**
 * Created by hdly on 2017/12/19.
 */
public class BaseFileEntity {

    public static final int SUCCESS_CODE = 100;// 替换成自己的成功码

    private int code;
    private boolean compressed;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }
}
