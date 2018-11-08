package com.review.foodreview.dto;

import android.provider.BaseColumns;

public class DBLog {
    public static final String DATABASE_NAME = "log.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Log";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String TAG = "tag";
        public static final String MESSAGE = "message";
    }

    private Integer _id;
    private String tag;
    private String message;

    public DBLog() {
    }

    public DBLog(Integer id, String tag, String message) {
        this._id = id;
        this.tag = tag;
        this.message = message;
    }

    public void clear() {
        this._id = null;
        this.tag = null;
        this.message = null;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
