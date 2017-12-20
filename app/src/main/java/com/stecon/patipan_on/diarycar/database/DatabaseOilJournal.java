package com.stecon.patipan_on.diarycar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by patipan_on on 10/25/2017.
 */

public class DatabaseOilJournal {

    public static final String TABLE_NAME = "OIL";

    public static final String COL_LICENSE_PLATE = "license_plate";
    public static final String COL_ODOMETER = "odometer";

    public static final String COL_UNIT_PRICE = "unit_price";
    public static final String COL_FUEL_TYPE = "fuel_type";
    public static final String COL_VOLUME = "volume";
    public static final String COL_TOTAL_PRICE = "total_price";
    public static final String COL_PARTIAL_FILL_UP = "partial_fillup";
    public static final String COL_PAYMENT_TYPE = "payment_type";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_NOTE = "note";
    public static final String COL_TRANSACTION_DATE = "transaction_date";

//    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
//            + COL_ODOMETER + " TEXT, "
//            + COL_TOTAL_PRICE + " TEXT);";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_LICENSE_PLATE + " TEXT,"
            + COL_ODOMETER + " REAL,"
            + COL_UNIT_PRICE + " REAL,"
            + COL_FUEL_TYPE + " TEXT,"
            + COL_VOLUME + " REAL,"
            + COL_TOTAL_PRICE + " REAL,"
            + COL_PARTIAL_FILL_UP + " INTEGER,"
            + COL_PAYMENT_TYPE + " TEXT,"
            + COL_LATITUDE+ " REAL,"
            + COL_LONGITUDE + " REAL,"
            + COL_NOTE + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


}
