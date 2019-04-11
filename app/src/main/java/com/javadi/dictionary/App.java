package com.javadi.dictionary;

import android.app.Application;

import database.DBHelper;

public class App extends Application {

    public static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper=DBHelper.getInstance(this);
    }
}
