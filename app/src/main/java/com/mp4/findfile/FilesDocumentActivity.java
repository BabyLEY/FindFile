package com.mp4.findfile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by m1315 on 2016/4/28.
 */
public class FilesDocumentActivity extends Activity {

    private FilesDao filesDao;
    private TextView viewById;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filesDao = new FilesDao(this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {
        List<String> packageNames = filesDao.getPackageName();
        File file = null;
        if (packageNames.size() > 0) {
            String path = packageNames.get(packageNames.size() - 1);
            file = new File(path);
            String filename = file.getName();
            viewById.setText("文件路径:" + path + "文件名:"+filename);
            myListView = (ListView) findViewById(R.id.listView);
            ArrayAdapter myArrayAdaptor = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, packageNames);
            myListView.setAdapter(myArrayAdaptor);
        }
    }
    private void initView() {
        setContentView(R.layout.activity_document);
        viewById = (TextView) findViewById(R.id.textView);
    }
}
