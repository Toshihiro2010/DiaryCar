package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 1/4/2018.
 */

public class DatabaseServiceMaster {

    public static final String TABLE_NAME = "ServiceMaster";

    public static String COL_ID = "_id";

    public static final String COL_SERVICE_ID = "service_id";
    public static final String COL_SERVICE_DESCRIPTION = "service_description";
    public static final String COL_SERVICE_PERIOD_MILEAGE = "service_period_mileage";
    public static final String COL_SERVICE_PERIOD_MONTH = "service_period_month";
    public static final String COL_EFFECTIVE_DATE = "effective_date";
    public static final String COL_EXPIRED_DATE = "expired_date";

    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";


    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_SERVICE_ID + " INTEGER,"
            + COL_SERVICE_DESCRIPTION + " TEXT,"
            + COL_SERVICE_PERIOD_MILEAGE + " REAL,"
            + COL_SERVICE_PERIOD_MONTH + " INTEGER,"
            + COL_EFFECTIVE_DATE + " DATETIME,"
            + COL_EXPIRED_DATE+ " DATETIME,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT);";





}
