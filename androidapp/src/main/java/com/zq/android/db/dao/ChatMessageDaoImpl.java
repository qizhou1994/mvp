package com.zq.android.db.dao;

import com.zq.android.bean.ChatMessage;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by wc on 2017/9/28.
 */
public class ChatMessageDaoImpl extends BaseDaoImpl<ChatMessage, Integer>{

    public ChatMessageDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<ChatMessage> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
