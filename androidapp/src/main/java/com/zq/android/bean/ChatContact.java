package com.zq.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wc on 2017/9/21.
 */
public class ChatContact implements Parcelable {

    private String userId;
    private String userName;
    private String content;
    private long sendTime;

    protected ChatContact(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        content = in.readString();
    }

    public static final Creator<ChatContact> CREATOR = new Creator<ChatContact>() {
        @Override
        public ChatContact createFromParcel(Parcel in) {
            return new ChatContact(in);
        }

        @Override
        public ChatContact[] newArray(int size) {
            return new ChatContact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(content);
    }

    public ChatContact() {

    }

    public ChatContact(String userId, String userName, String content, long sendTime) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.sendTime = sendTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "ChatContact{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
