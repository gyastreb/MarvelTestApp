package com.gyastreb.testproject;

import android.app.Application;

import com.gyastreb.testproject.db.DBHelper;

/**
 * Created by gyastreb on 9/19/16.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        DBHelper.releaseHelper();
        super.onTerminate();
    }
}
