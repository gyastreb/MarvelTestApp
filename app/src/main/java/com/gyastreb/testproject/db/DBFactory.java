package com.gyastreb.testproject.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by gyastreb on 9/19/16.
 */
public class DBFactory {
    private static DBController databaseManager;

    public static DBController getManager() {
        return databaseManager;
    }

    public static void initManager(Context context) {
        if (databaseManager == null || !databaseManager.isOpen()) {
            databaseManager = OpenHelperManager.getHelper(context, DBController.class);
        }
    }
}
