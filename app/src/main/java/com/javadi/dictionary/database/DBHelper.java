package com.javadi.dictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static String dbName="dic.db";
    private static int dbVersion=1;
    private static Context mContext;
    private static String dbPath="";
    private SQLiteDatabase db;
    private static DBHelper instance=null;

    public static synchronized DBHelper getInstance(Context context){
        if(instance==null){
            instance=new DBHelper(context);
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, dbName, null, dbVersion);
        this.mContext=context;
        dbPath="/data/data/"+context.getPackageName()+"/databases/";
        try {
            createDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDb() throws IOException {
        File dir=new File(dbPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File dbFile=new File(dbPath+dbName);
        if(!dbFile.exists()){
            try {
                InputStream is=mContext.getAssets().open(dbName);
                OutputStream os=new FileOutputStream(dbPath+dbName);
                byte[] buffer=new byte[1024];
                int length;
                while ((length=is.read(buffer))>0){
                    os.write(buffer,0,length);
                }
                os.flush();
                os.close();
                is.close();
            }catch (IOException e){
                e.printStackTrace();
                Log.e("Error copyDb",e.getMessage());
            }
        }
    }

    private void createDb() throws IOException{
        try {
            copyDb();

        }catch (SQLException e){
            e.printStackTrace();
            Log.e("Error createDb",e.getMessage());
        }
    }

    private void openDb(){
        try{
            db=SQLiteDatabase.openDatabase(dbPath+dbName,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e){
            e.printStackTrace();
            Log.e("Error openDb",e.getMessage());
        }
    }

    public String translate(String word){
        openDb();
        Cursor cursor =db.rawQuery("SELECT * FROM "+DBC.mainDB.TABLE_NAME+" WHERE " +DBC.mainDB.ENGLISH_WORD
                +"="+"'"+word+"'",null);
        String mean=null;
        if(cursor.moveToFirst()){
            do{
                mean=cursor.getString(1);
            }while (cursor.moveToNext());
        }
        return mean;
    }

    public List<String> wordList(){
        List<String> list=new ArrayList<>();
        openDb();
        Cursor cursor=db.rawQuery("SELECT "+DBC.mainDB.ENGLISH_WORD+" FROM "+DBC.mainDB.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex(DBC.mainDB.ENGLISH_WORD)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public synchronized void close() {
        if(db!=null){
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS "+ DBC.searchedWords.TABLE_NAME
                    +" ("+ DBC.searchedWords.id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DBC.searchedWords.ENGLISH_WORD+" TEXT,"
                    + DBC.searchedWords.PERSIAN_WORD+" TEXT,"
                    +DBC.searchedWords.checkFavorite+" INTEGER)");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getHistoryList(){
        List<String> list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase .rawQuery("SELECT "+DBC.searchedWords.ENGLISH_WORD+" FROM "+DBC.searchedWords.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex(DBC.searchedWords.ENGLISH_WORD)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public long insertHistoryWord(String word){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBC.searchedWords.ENGLISH_WORD,word);
        contentValues.put(DBC.searchedWords.checkFavorite,0);
        return sqLiteDatabase.insert(DBC.searchedWords.TABLE_NAME,null,contentValues);
    }

}
