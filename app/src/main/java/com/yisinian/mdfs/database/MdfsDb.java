package com.yisinian.mdfs.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by mac on 16/1/11.
 */
public class MdfsDb extends SQLiteOpenHelper{


    public MdfsDb(Context context) {
        super(context, "mdfsdb", null  , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE file ( file_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "file_name varchar default 0" +
                    " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void insertIntoFile(SQLiteDatabase db,String fileName){
        db.execSQL("insert"+
                    " into "+
                    " file(file_name)values("+fileName+");");
    }




}
