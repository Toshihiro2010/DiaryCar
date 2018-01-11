package com.stecon.patipan_on.diarycar.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.stecon.patipan_on.diarycar.database.DatabaseLog;


public class MyLogSave {

    private SQLiteDatabase sqLiteDatabase;

    public MyLogSave(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void SaveLog(String message) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseLog.COL_MESSAGE, message);
        sqLiteDatabase.insert(DatabaseLog.TABLE_NAME, null, contentValues);
    }


}
