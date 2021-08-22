package com.example.shareit.Model;

import android.graphics.drawable.Drawable;

public class Model_app {
    private String app_name;
    private Drawable icon;
    private String path;
    private long size;

    public Model_app(String app_name, Drawable icon, String path, long size) {
        this.app_name = app_name;
        this.icon = icon;
        this.path = path;
        this.size = size;
    }
    public String getApp_name() {
        return app_name;
    }
    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }
    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
}
