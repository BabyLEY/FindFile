package com.mp4.findfile;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * @author Administrator
 * @desc 程序锁数据库的业务dao
 * @date 2015-9-25
 * @Author $Author: goudan $ Id Rev URL
 * @Date $Date: 2015-09-25 15:09:07 +0800 (Fri, 25 Sep 2015) $
 * @Id $Id: FilesDao.java 107 2015-09-25 07:09:07Z goudan $
 * @Rev $Rev: 107 $
 * @URL $URL:
 * https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com
 * /itheima/mobilesafe13/dao/FilesDao.java $
 */
public class FilesDao {
    private FilesDB mFilesDB;
    private Context mContext;

    public FilesDao(Context context) {
        mFilesDB = new FilesDB(context);
        mContext = context;
    }

    /**
     * 添加文件名
     */
    public void addPackageName(String packagename) {
        SQLiteDatabase database = mFilesDB.getWritableDatabase();
        ContentValues values = new ContentValues();//?干嘛的?
        values.put(FilesDB.PACKAGE_NAME, packagename);
        database.insert(FilesDB.FILESTB, null, values);
        database.close();
    }

    /**
     *查找该名称是否存在
     * @param packagename
     */
    public boolean checkPackageName(String packagename) {
        boolean res = false;
        SQLiteDatabase database = mFilesDB.getReadableDatabase();
        Cursor cursor = database.rawQuery("select 1 from " + FilesDB.FILESTB
                        + " where " + FilesDB.PACKAGE_NAME + " = ? ",
                new String[] { packagename });
        if (cursor.moveToNext()) {
            res = true;
        }

        return res;
    }

    /**
     * @return 获取搜索的所有文件的文件名
     */
    public List<String> getPackageName() {
        List<String> lockedPacknames = new ArrayList<String>();
        SQLiteDatabase database = mFilesDB.getReadableDatabase();
        Cursor cursor = database.rawQuery("select " + FilesDB.PACKAGE_NAME
                + "  from " + FilesDB.FILESTB, null);
        while (cursor.moveToNext()) {
            lockedPacknames.add(cursor.getString(0));
        }

        return lockedPacknames;
    }

    /**
     * 删除
     * @param package_name
     * @return
     */
    public boolean delete(String package_name) {
        // 1.获取数据库
        SQLiteDatabase database = mFilesDB.getWritableDatabase();
        // 2. 删除
        int count = database.delete(mFilesDB.FILESTB, mFilesDB.PACKAGE_NAME + "=?",
                new String[] { package_name });
        // 3. 数据的关闭
        database.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
