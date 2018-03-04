package com.zq.android.utils.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.zq.android.bean.ChatMessage;
import com.zq.android.db.DatabaseHelper;
import com.zq.android.db.dao.UnlimitDaoManager;
import com.zq.android.utils.L;
import com.zq.android.utils.ListUtils;
import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wc on 2017/9/28.
 */
public class ChatMessageUtil {
    private static final String TAG = "ChatMessageUtil";
    public static final int MAX_MESSAGE_SAVE_NUM = 15;
    public static final int REFRESH_MESSAGE_NUM = 15;
    private static ChatMessageUtil instance;
    private DatabaseHelper mHelper;

    public static final String PREFIX = "table_prefix_";

    public static ChatMessageUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ChatMessageUtil(context);
        }
        return instance;
    }

    public ChatMessageUtil(Context context) {
        mHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    private Map<String, Dao<ChatMessage, Integer>> mDaoMap = new HashMap<String, Dao<ChatMessage, Integer>>();

    private Dao<ChatMessage, Integer> getDao(long groupId) {
        String tableName = PREFIX + groupId;
//        L.i(TAG, "tableName = " + tableName);
        if (mDaoMap.containsKey(tableName)) {
            return mDaoMap.get(tableName);
        }
        Dao<ChatMessage, Integer> dao = null;
        try {
            DatabaseTableConfig<ChatMessage> config = DatabaseTableConfigUtil.fromClass(mHelper.getConnectionSource(), ChatMessage.class);
//            L.i(TAG, "mHelper.getConnectionSource()" + mHelper.getConnectionSource());
//            L.i(TAG, "config = " + config.toString());
            config.setTableName(tableName);
            createTableIfNotExist(tableName);
            dao = UnlimitDaoManager.createDao(mHelper.getConnectionSource(), config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dao != null) {
            mDaoMap.put(tableName, dao);
        }
        return dao;
    }

    public void createTableIfNotExist(String tableName) {
        if (isTableExist(tableName)) {
            return;
        }
        String sql = "CREATE TABLE " + tableName + " (content VARCHAR , num INTEGER PRIMARY KEY AUTOINCREMENT , type VARCHAR , " +
                "direct VARCHAR , status VARCHAR , chatType VARCHAR , groupId INTEGER , contentType INTEGER , userName VARCHAR , " +
                "userId VARCHAR , unknownContent VARCHAR , ts INTEGER , fp VARCHAR)";
        try {
            mHelper.getWritableDatabase().execSQL(sql);
            L.d("roamer", "isTableExist(tableName):" + isTableExist(tableName));
        } catch (SQLiteException e) {
            L.i(TAG, "tableName = " + tableName + "的表已经存在，先删后建表");

            String deleteSql = "DROP TABLE " + tableName;
            mHelper.getWritableDatabase().execSQL(deleteSql);

            mHelper.getWritableDatabase().execSQL(sql);
        }
    }

    private boolean isTableExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = mHelper.getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public int addMessage(long groupId, ChatMessage message) {
        Dao<ChatMessage, Integer> dao = getDao(groupId);
        try {
            List<ChatMessage> oldDatas = dao.queryForAll();
            // 超过15条就删除一条
            if (!ListUtils.isEmpty(oldDatas) && oldDatas.size() >= MAX_MESSAGE_SAVE_NUM) {
                dao.delete(oldDatas.get(0));
            }
            return dao.create(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        L.i(TAG, "addMessage(long groupId, ChatMessage message),由于异常而返回0");
        return 0;
    }

    public void addMessage(List<ChatMessage> messageList) {
        if (!ListUtils.isEmpty(messageList)) {
            long groupId = messageList.get(0).getGroupId();
            for (ChatMessage chatMessage : messageList) {
                addMessage(groupId, chatMessage);
            }
        } else {
            L.i(TAG, "需要保存的消息list为空");
        }
    }

    /**
     * 根据fp拿本地缓存消息，用来获取真实时间（服务器时间）
     *
     * @param groupId
     * @param fp
     * @return
     */
    public ChatMessage queryMessageForFp(long groupId, String fp) {
        Dao<ChatMessage, Integer> dao = getDao(groupId);
        try {
            List<ChatMessage> messageList = dao.queryForEq("fp", fp);
            if (!ListUtils.isEmpty(messageList)) {
                // 肯定只有一个
                return messageList.get(0);
            } else {
                L.i(TAG, "根据fp：" + fp + "，取不到消息");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        L.i(TAG, "因为异常，故没有能够根据fp找到本地存的消息");
        return null;
    }

    public boolean updateChatMessage(ChatMessage chatMessage) {
        Dao<ChatMessage, Integer> dao = getDao(chatMessage.getGroupId());
        try {
            return 1 == dao.update(chatMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        L.i(TAG, "更新消息失败");
        return false;
    }

    // 根据groupId获取所有信息
    public List<ChatMessage> queryAllMessageForGroupId(long groupId) {
        Dao<ChatMessage, Integer> dao = getDao(groupId);
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 按照时间进行排序(对消息)
    public void sortMsgFromTs(List<ChatMessage> datas) {
        Collections.sort(datas, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage lhs, ChatMessage rhs) {
                return lhs.getTs() >= rhs.getTs() ? 1 : -1;
            }
        });
    }

    public void deleteTable(long groupId) {
        String tableName = PREFIX + groupId;
        if (!isTableExist(tableName))
            return;

        String sql = "DROP TABLE " + tableName;

        try {
            mHelper.getWritableDatabase().execSQL(sql);
        } catch (SQLiteException e) {
            L.i(TAG, "groupId = " + groupId + "的讨论组已经被删除了");
        }
    }
}
