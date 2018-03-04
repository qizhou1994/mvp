package com.zq.android.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @des: 实体类
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:10
 */
public class BaseEntity<T> implements Serializable {

    //    error_code	int	返回码
    //    reason	string	返回说明
    //    result	string	返回结果集
    @SerializedName("error_code")
    private int errorCode;
    private String reason;
    private T result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
