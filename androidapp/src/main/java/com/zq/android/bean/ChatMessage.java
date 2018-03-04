package com.zq.android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zq.android.base.BaseObj;
import com.zq.android.db.dao.ChatMessageDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * 此类是针对一对多关系时的例子。ormlite可以一个类对应一张表，
 * 但需要一个类对应多张表时，可以用这个例子进行改动，需要重写
 * 的重要类是ChatMessageDaoImpl
 */
@DatabaseTable(daoClass = ChatMessageDaoImpl.class)
public class ChatMessage extends BaseObj implements Parcelable, Cloneable {

    private static final String TAG = "chatMsg";

    @DatabaseField
    ChatMessage.Type type;// 对话内容的类型

    @DatabaseField
    public ChatMessage.Direct direct;// 属于接收还是发送

    @DatabaseField
    public ChatMessage.Status status;// 若为发送，则是发送的状态；若为接收，则是接收的状态

    @DatabaseField
    ChatMessage.ChatType chatType;// 对话的类型（目前只有讨论组）

    public ChatContact chatContact;// 对话的bean（包括id，username，具体信息，发送时间）

    @DatabaseField
    public String content;

    @DatabaseField
    public long groupId;

    @DatabaseField
    public int contentType;

    @DatabaseField
    private String userId;

    @DatabaseField
    private String userName;

    @DatabaseField
    private String unknownContent;

    @DatabaseField
    private long ts;

    @DatabaseField
    private String fp;

    private int schedule;

    protected ChatMessage(Parcel in) {
    }

    public ChatMessage() {
        setChatContact(new ChatContact());
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public ChatContact getChatContact() {
        return chatContact;
    }

    public void setChatContact(ChatContact chatContact) {
        this.chatContact = chatContact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUnknownContent() {
        return unknownContent;
    }

    public void setUnknownContent(String unknownContent) {
        this.unknownContent = unknownContent;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", direct=" + direct +
                ", status=" + status +
                ", chatType=" + chatType +
                ", chatContact=" + chatContact +
                ", content='" + content + '\'' +
                ", groupId=" + groupId +
                ", contentType=" + contentType +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", unknownContent='" + unknownContent + '\'' +
                ", ts=" + ts +
                ", fp='" + fp + '\'' +
                ", schedule=" + schedule +
                '}';
    }

    // 初始化一些数据
    public static ChatMessage initChatMessage(ChatMessage chatMessage, ChatMessage.Type type, ChatMessage.Direct direct,
                         ChatMessage.ChatType chatType, long groupId, int contentType, int userId, String username, long time) {

        chatMessage.setUserId(String.valueOf(userId));
        chatMessage.setUserName(username);
        if (chatMessage.getTs() == 0) {
            chatMessage.setTs(time);
        }
        chatMessage.setChatType(chatType);
        chatMessage.setContentType(contentType);
        chatMessage.setDirect(direct);
        chatMessage.setType(type);
        chatMessage.setGroupId(groupId);

        return chatMessage;
    }

    public static String genFingerPrint() {
        return UUID.randomUUID().toString();
    }

    public static enum ChatType {
        ChatRoom;

        private ChatType() {
        }
    }

    public static enum Direct {
        SEND,
        RECEIVE;

        private Direct() {
        }
    }

    public static enum Status {
        SUCCESS,
        FAIL,
        INPROGRESS,
        CREATE,
        UNREAD;

        private Status() {
        }
    }

    public static enum Type {
        TXT,
        IMAGE,
        VIDEO,
        LOCATION,
        VOICE,
        FILE,
        SYSTEM_MSG,
        CMD;

        private Type() {}
    }
}
