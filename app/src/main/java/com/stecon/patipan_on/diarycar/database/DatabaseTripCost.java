package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 11/30/2017.
 */

public class DatabaseTripCost {

    public static final String TABLE_NAME = "TRIP_COST";

    public static final String COL_TRIP_ID = "trip_id";
    public static final String COL_PRICE_TYPE = "price_type";
    public static final String COL_PRICE_TITLE = "title";
    public static final String COL_PRICE_MONEY = "money";
    public static final String COL_NOTE = "note";
    public static final String COL_TRANSACTION_DATE = "transaction_date";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TRIP_ID + " INTEGER,"
            + COL_PRICE_TYPE + " TEXT,"
            + COL_PRICE_TITLE + " TEXT,"
            + COL_PRICE_MONEY + " REAL,"
            + COL_NOTE + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

}