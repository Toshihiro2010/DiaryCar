package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 1/3/2018.
 */

public class DatabaseServiceRecords {

    public static final String TABLE_NAME = "ServiceRecord";

    public static String COL_ID = "_id";

    public static final String COL_LICENSE_PLATE = "license_plate";

    public static final String COL_SERVICE_ID = "service_id";
    public static final String COL_ODOMETER = "odometer";
    public static final String COL_FUEL_LEVEL = "fuel_level";
    public static final String COL_SERVICE_COST = "service_cost";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_LOCATION_NAME = "location_name";
    public static final String COL_NOTE = "note";
    public static final String COL_TRANSACTION_DATE = "transaction_date";
    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_SERVICE_ID + " INTEGER,"
            + COL_LICENSE_PLATE + " TEXT,"
            + COL_ODOMETER + " REAL,"
            + COL_FUEL_LEVEL + " REAL,"
            + COL_SERVICE_COST + " REAL,"
            + COL_LATITUDE+ " REAL,"
            + COL_LONGITUDE + " REAL,"
            + COL_NOTE + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";


}
