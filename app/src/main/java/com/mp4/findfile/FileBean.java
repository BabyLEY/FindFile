package com.mp4.findfile;

/**
 * 类说明：
 * Created by zhugeheng on 2016/1/27.
 * Copyright (c) 2015年 桂林远创信息科技有限公司. All rights reserved.
 */
public class FileBean {
    private String id;
    private String name;
    private float size;
    private long commitTime;
    private String timeIdentification;
    private int fileType;

    public FileBean(String name, float size, String timeIdentification, long commitTime) {
        this.name = name;
        this.size = size;

        this.timeIdentification = timeIdentification;
        this.commitTime = commitTime;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }


    public long getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(long commitTime) {
        this.commitTime = commitTime;
    }

    public String getTimeIdentification() {
        return timeIdentification;
    }

    public void setTimeIdentification(String timeIdentification) {
        this.timeIdentification = timeIdentification;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
}
