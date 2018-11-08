package com.review.foodreview.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Log " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tag VARCHAR(255)," +
            "message VARCHAR(255))";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS Log";

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, "my.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);

        Log.d(TAG, "CREATE table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);

        Log.d(TAG, "DROP table");
    }
}
