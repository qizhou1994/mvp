package com.zq.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zq.android.base.BaseObj;
import com.zq.android.bean.ChatMessage;
import com.zq.android.utils.L;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据库访问帮助类
 *
 * @author 曾繁添
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private final static String DEFAULT_DATABASE = "reverse_distribute_order_db";
    private final static int DATABASE_VERSION = 1;
    private static DatabaseHelper instance = null;
    private final static String TAG = DatabaseHelper.class.getSimpleName();
    private Map<String, Dao> daoMap = new HashMap<String, Dao>();
    private static List<Class<? extends BaseObj>> table = new ArrayList<Class<? extends BaseObj>>();

    /**
     * 对外实例化对象不采用该构造方法
     *
     * @param context
     */
    @Deprecated
    public DatabaseHelper(Context context) {
        super(context, DEFAULT_DATABASE, null, DATABASE_VERSION);
    }


    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    //会隐式调用public DatabaseHelper(Context context){}
                    instance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
            }
        }
        return instance;
    }

    /**
     * 双重加锁检查
     *
     * @param context 上下文
     * @return 单例
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    //会隐式调用public DatabaseHelper(Context context){}
                    instance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
                }
            }
        }
        return instance;
    }

    /**
     * 注册表实体
     *
     * @param entity 表实体
     */
    public static void registerTables(Class<? extends BaseObj> entity) {
        table.add(entity);
    }

    /**
     * 创建表
     *
     * @param entity 实体
     */
    public void createTable(Class<? extends BaseObj> entity) {
        try {
            TableUtils.createTableIfNotExists(getConnectionSource(), entity);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create table-->" + entity.getSimpleName(), e);
        }
    }

    /**
     * 清除表数据
     *
     * @param entity 实体
     */
    public void clearTableData(Class<? extends BaseObj> entity) {
        try {
            TableUtils.clearTable(getConnectionSource(), entity);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to clear table data for -->" + entity.getSimpleName(), e);
        }
    }

    /**
     * 清除所有表数据
     */
    public void clearAllTableData() {
        L.d(TAG, "------START-----");
        try {
            if(table == null || table.size() == 0){
                initTables();
            }
            for (Class<? extends BaseObj> entity : table) {
                int res = TableUtils.clearTable(getConnectionSource(), entity);
                L.d(TAG, entity.getSimpleName() + " DELETE NUMBER:" + res);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Unable to clear table", e);
        }
        L.d(TAG, "------end-------");
    }

    /**
     * 删除表
     *
     * @param entity 实体
     */
    public void dropTable(Class<? extends BaseObj> entity) {
        try {
            TableUtils.dropTable(getConnectionSource(), entity, true);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to drop tables-->" + entity.getSimpleName(), e);
        }
    }


    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws SQLException
     */
    public DatabaseConnection getConnection() throws SQLException {
        DatabaseConnection connection = null;
        if (isOpen()) {
            connection = getConnectionSource().getReadWriteConnection();
        } else {
            connection = new AndroidDatabaseConnection(getWritableDatabase(), true);
        }
        return connection;
    }


    /**
     * 关闭数据库连接
     */
    public void closeConnection(DatabaseConnection connection) {
        try {
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "关闭数据库失败，原因：" + e.getMessage());
        }
    }

    /**
     * 提交事务
     *
     * @param connection 数据库连接
     * @param savePoint  事务点
     */
    public void commit(DatabaseConnection connection, Savepoint savePoint) {
        try {
            connection.commit(savePoint);
        } catch (SQLException e) {
            Log.e(TAG, "提交事务失败，原因：" + e.getMessage());
        }
    }

    /**
     * 回滚事务
     *
     * @param connection 数据库连接
     * @param savePoint  事务点
     */
    public void rollback(DatabaseConnection connection, Savepoint savePoint) {
        try {
            connection.rollback(savePoint);
        } catch (SQLException e) {
            Log.e(TAG, "回滚事务失败，原因：" + e.getMessage());
        }
    }

    /**
     * 获取Dao
     *
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public synchronized <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        Dao dao = null;
        try {
            String keyClassName = clazz.getSimpleName();
            if (daoMap.containsKey(keyClassName)) {
                dao = daoMap.get(keyClassName);
            }
            if (dao == null) {
                dao = super.getDao(clazz);
                daoMap.put(keyClassName, dao);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Unable to getDao", e);
            throw e;
        }
        return (D) dao;
    }

    /**
     * 释放数据库连接
     */
    public void releaseAll() {
        if (instance != null) {
            OpenHelperManager.releaseHelper();
            instance = null;
        }
    }

    /**
     * 释放资源
     */
    @SuppressWarnings("unused")
    @Override
    public void close() {
        super.close();
        for (String key : daoMap.keySet()) {
            Dao dao = daoMap.get(key);
            dao = null;
        }
    }


    /**
     * 创建数据库时执行，初始化表
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        initTables();
        try {
            for (Class<? extends BaseObj> entity : table) {
                TableUtils.createTableIfNotExists(connectionSource, entity);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create datbases", e);
        }
    }

    /**
     * 更新数据库时执行，需要手动生成或删除表，并且数据库版本DATABASE_VERSION记得+1
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

        } catch (Exception e) {
            Log.e(TAG, "Unable to upgrade database from version " + oldVersion + " to new " + newVersion, e);
        }
    }


    /**
     * 添加表
     */
    public void initTables() {
        // 放置所有需要生成的表,例如
        table.add(ChatMessage.class);
    }
}
