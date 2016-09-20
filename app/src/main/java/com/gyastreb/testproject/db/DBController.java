package com.gyastreb.testproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gyastreb.testproject.models.Data;
import com.gyastreb.testproject.models.Main;
import com.gyastreb.testproject.models.Result;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by gyastreb on 9/17/16.
 */
public class DBController extends OrmLiteSqliteOpenHelper{

    private static final String TAG = DBController.class.getSimpleName();

    private static final String DATABASE_NAME ="testapp.db";

    private static final int DATABASE_VERSION = 1;

    private ResultDAO resultsDao = null;

    public DBController(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try {
            TableUtils.createTable(connectionSource, Result.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try {
            TableUtils.dropTable(connectionSource, Main.class, true);
            TableUtils.dropTable(connectionSource, Data.class, true);
            TableUtils.dropTable(connectionSource, Result.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(db, connectionSource);

    }



    @Override
    public void close(){
        super.close();
        resultsDao = null;
    }
}
