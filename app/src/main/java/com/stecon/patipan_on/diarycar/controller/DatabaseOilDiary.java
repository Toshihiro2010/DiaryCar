package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by patipan_on on 10/25/2017.
 */

public class DatabaseOilDiary {



    private static final String DB_NAME = "CAR";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "OIL";

    public static final String COL_DATE = "date";
    public static final String COL_LIT = "liter_amount";
    public static final String COL_MONEY = "money";

    public static String strSqlInsert = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + COL_LIT + " TEXT, "
            + COL_MONEY + " TEXT);";

}
