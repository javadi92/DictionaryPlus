package database;

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

public class DBHelper extends SQLiteOpenHelper {

    private static String dbName="dic.db";
    private static int dbVersion=1;
    private Context mContext;
    private static String dbPath="";
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, dbName, null, dbVersion);
        this.mContext=context;
        dbPath="/data/data/"+mContext.getPackageName()+"/databases/";
    }

    public void copyDb() throws IOException {
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

    public void createDb() throws IOException{
        try {
            copyDb();
        }catch (SQLException e){
            e.printStackTrace();
            Log.e("Error createDb",e.getMessage());
        }
    }

    public void openDb(){
        try{
            db=SQLiteDatabase.openDatabase(dbPath+dbName,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e){
            e.printStackTrace();
            Log.e("Error openDb",e.getMessage());
        }
    }

    public String translate(String word){
        openDb();
        Cursor cursor =db.rawQuery("SELECT * FROM "+DBC.TABLE_NAME+" WHERE " +DBC.ENGLISH_WORD
                +"="+word,null);
        String mean=null;
        if(cursor.moveToFirst()){
            do{
                mean=cursor.getString(1);
            }while (cursor.moveToNext());
        }
        return mean;
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
