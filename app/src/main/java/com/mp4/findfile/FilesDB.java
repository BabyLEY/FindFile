package com.mp4.findfile;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Administrator
 */
public class FilesDB extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    public static final String PACKAGE_NAME = "packagename";
    public static final String FILESTB = "files_tb";

    public FilesDB(Context context) {
        super(context, "mydocument.db", null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 第一个创建执行
        //创建表的操作
        //	db.execSQL("create table filestb(_id integer primary key autoincrement,package_name text)");
        db.execSQL("create table files_tb(_id integer primary key autoincrement,packagename text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //版本号发生变化，调用该方法
        db.execSQL("drop table files_tb");
        onCreate(db);
    }

}
