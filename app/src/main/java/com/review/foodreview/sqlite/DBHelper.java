package com.review.foodreview.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.review.foodreview.dto.DBLog;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private static final String SQL_CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s" +
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s VARCHAR(255)," +
            "%s VARCHAR(255))",
            DBLog.TABLE,
            DBLog.Column.ID,
            DBLog.Column.TAG,
            DBLog.Column.MESSAGE);

    private static final String SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", DBLog.TABLE);

    private static final String SQL_QUERY = String.format("SELECT * FROM %s", DBLog.TABLE);

    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;
    private List<DBLog> logList;
    private DBLog dbLog;

    public DBHelper(Context context) {
        super(context, DBLog.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.SQL_CREATE_TABLE);

        Log.d(TAG, String.format("CREATE TABLE %s", DBLog.TABLE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(this.SQL_DROP_TABLE);
        onCreate(db);

        Log.d(this.TAG, String.format("DROP TABLE %s", DBLog.TABLE));
    }

    public List<DBLog> getAllLogs() {
        this.sqLiteDatabase = this.getWritableDatabase();
        this.cursor = this.sqLiteDatabase.rawQuery(this.SQL_QUERY, null);

        logList = new ArrayList<>();

        while (this.cursor.moveToNext()) {
            dbLog.setId(this.cursor.getInt(0));
            dbLog.setTag(this.cursor.getString(1));
            dbLog.setMessage(this.cursor.getString(2));

            logList.add(dbLog);
        }

        cursor.close();
        sqLiteDatabase.close();
        dbLog.clear();

        return logList;
    }

    public void addlogs(DBLog log) {
        this.sqLiteDatabase = this.getWritableDatabase();
        this.contentValues = new ContentValues();

        contentValues.put(DBLog.Column.ID, log.getId());
        contentValues.put(DBLog.Column.TAG, log.getTag());
        contentValues.put(DBLog.Column.MESSAGE, log.getMessage());

        sqLiteDatabase.insert(DBLog.TABLE, null, this.contentValues);
        sqLiteDatabase.close();
    }
}
