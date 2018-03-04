package com.zq.android.base;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by shenzx on 2015/12/29.
 */
public class BaseObj implements Serializable {

    private static final long serialVersionUID = 6337104618534280060L;

    /**
     * 主键ID
     */
    @DatabaseField(generatedId = true)
    protected long num;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
