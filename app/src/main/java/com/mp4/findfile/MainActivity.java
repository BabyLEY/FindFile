package com.mp4.findfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView myListView;
    List<String> fname;
    private ArrayAdapter myArrayAdaptor;
    private String interPath;
    private String exterPath;
    private FilesDao filesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filesDao = new FilesDao(this);

        initView();
        initData();


    }

    public void get(View view) {
        Intent intent = new Intent(this, FilesDocumentActivity.class);
        startActivity(intent);
    }

    private void initView() {
        setContentView(R.layout.activity_find_document);
    }

    private void initData() {
        fname = new ArrayList<String>();
        getDocument();
        System.out.println("interPath========" + interPath);//内存储
        System.out.println("exterPath========" + exterPath);//外存储
        System.out.println("List  Length======" + fname.size());
        myListView = (ListView) findViewById(R.id.fileView);
        myArrayAdaptor = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, fname);
        myListView.setAdapter(myArrayAdaptor);
    }

    @UiThread
    void getDocument() {
        interPath = getSdPath(getApplicationContext(), false);
        exterPath = getSdPath(getApplicationContext(), true);
        if (interPath != null) {
            searchFile(interPath);
        } else if (exterPath != null) {
            searchFile(exterPath);
        }
    }

    //文件查找
    public void searchFile(String path) {
        File[] files = new File(path).listFiles();
        try {
            if (files != null) {
                for (File temp : files) {
                    if (temp.isDirectory()) {
                        searchFile(temp.getAbsolutePath());
                    } else {
                        String filename = temp.getName();
                        //String suffix = filename.substring(filename.lastIndexOf(".") + 1) ;
                        if (filename.equals("")) {
                            Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                        } else {
                            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
                            suffix = suffix.toLowerCase();
                            if (suffix.equals("doc") | suffix.equals("docx")) {
                                String file = temp.getAbsoluteFile().toString();
                                String s = temp.toString();
                               // fname.add(s);
                                if (!filesDao.checkPackageName(s)) {//判断该文件是否存在 存在则不增加
                                    filesDao.addPackageName(s);
                                }

                            }
                        }


                    }
                }
            }

        } catch (Exception e) {
            System.out.println("为空" + e);
        }


    }

    //获取内外存储设备路径
    public static String getSdPath(Context mContext, boolean is_removable) {
        StorageManager myStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClass = null;
        try {
            storageVolumeClass = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = myStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClass.getMethod("getPath");
            Method isRemovable = storageVolumeClass.getMethod("isRemovable");
            Object result = getVolumeList.invoke(myStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement) + "/";
                boolean removable = (boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removable == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
