package com.zq.android.bean.file;

/**
 * Created by hdly on 2017/12/19.
 */
public class ImageFileObj {

    // 文件创建时间（暂无用）
    private long createTime;
    // 原文件名称
    private String name;
    // 文件url
    private String path;
    // 文件大小
    private long size;

    private int status;
    // 文件后缀名
    private String suffixName;
    // 文件类型，暂未定
    private int type;
    // 以后可用此标识来删除图片，目前没用
    private String uuid;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ImageFileObj{" +
                "createTime=" + createTime +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", status=" + status +
                ", suffixName='" + suffixName + '\'' +
                ", type=" + type +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
