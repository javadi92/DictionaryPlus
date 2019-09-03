package com.javadi.dictionary.utils;

import android.app.Application;

import com.javadi.dictionary.database.DBHelper;

public class App extends Application {

    public static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper=DBHelper.getInstance(this);
    }
}
