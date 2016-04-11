package com.lzl_rjkx.doctor.bean;

import android.graphics.Bitmap;


/**
 * Created by lzl_os on 16/2/22.
 */

public class CollectDao {

    private String id;
    private Bitmap icon;
    private String title;
    private String author;
    private String pubTime;

    public CollectDao() {
    }

    public CollectDao(String id, Bitmap icon, String title, String author, String pubTime) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.author = author;
        this.pubTime = pubTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap geticon() {
        return icon;
    }

    public void seticon(Bitmap icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }
}
