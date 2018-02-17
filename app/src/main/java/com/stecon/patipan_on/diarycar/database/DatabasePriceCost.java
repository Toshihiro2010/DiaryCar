package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 11/30/2017.
 */

public class DatabasePriceCost {

    public static final String TABLE_NAME = "TRIP_COST";

    public static final String COL_ID = "_id";
    public static final String COL_LICENSE_PLATE = "license_plate";
    public static final String COL_PRICE_TYPE = "price_type";
    public static final String COL_PRICE_TITLE = "title";
    public static final String COL_PRICE_MONEY = "money";
    public static final String COL_NOTE = "note";
    public static final String COL_TRANSACTION_DATE = "transaction_date";
    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_LICENSE_PLATE + " TEXT,"
            + COL_PRICE_TYPE + " TEXT,"
            + COL_PRICE_TITLE + " TEXT,"
            + COL_PRICE_MONEY + " REAL,"
            + COL_NOTE + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";

}
