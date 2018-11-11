package com.review.foodreview.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.review.foodreview.dto.LogDTO;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private final String LOGTAG = getClass().getSimpleName();

    private static final String DATABASE_NANE = "my.db";
    private static final String TABLE_NAME = "Log";
    private static final String ID = "_id";
    private static final String TAG = "tag";
    private static final String MESSAGE = "message";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS Log";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Log " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tag VARCHAR(255)," +
            "message VARCHAR(255))";

    /**
     * Intent: init database connector
     */
    private SQLiteDatabase sqLiteDatabase;

    /**
     * Intent: for create rows in database
     */
    private ContentValues contentValues;

    /**
     *
     * Intent: for read rows in database
     */
    private Cursor cursor;
    private LogDTO logDTO;
    private List<LogDTO> logDTOS;

    public DBHelper(Context context) {
        super(context, DATABASE_NANE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        Log.d(LOGTAG, "table was created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
        Log.d(LOGTAG, "table was drop");
    }

    public void createLog(LogDTO logDTO) {
        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put("tag", logDTO.getTag());
        contentValues.put("message", logDTO.getMessage());

        sqLiteDatabase.insert("Log", null, contentValues);

        sqLiteDatabase.close();
        Log.d(LOGTAG, "create row completes");
    }

    public List<LogDTO> readLog() {
        sqLiteDatabase = this.getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM Log", null);
        logDTO = new LogDTO();
        logDTOS = new ArrayList<>();

        while (cursor.moveToNext()) {
            logDTO.setId(cursor.getInt(0));
            logDTO.setTag(cursor.getString(1));
            logDTO.setMessage(cursor.getString(2));

            logDTOS.add(logDTO);
        }
        cursor.close();
        sqLiteDatabase.close();

        Log.d(LOGTAG, "read row complete");
        return logDTOS;
    }
}
