package com.heima.takeout31.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.heima.takeout31.model.net.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by lidongzhi on 2016/12/8.
 */

public class TakeoutOpenHelper extends OrmLiteSqliteOpenHelper {
    //1.1----- 1
    //1.2------1 (没有涉及到数据库的功能）
    //1.3------2(当新版功能与数据库有关，就需要升级数据库版本号）
    public TakeoutOpenHelper(Context context) {
        super(context, "takeout31.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, RecepitAddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //升级数据库版本后回调此方法
        try {
            TableUtils.createTable(connectionSource, RecepitAddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
