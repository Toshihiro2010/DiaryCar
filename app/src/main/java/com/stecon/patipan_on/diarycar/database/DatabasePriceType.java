package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 1/5/2018.
 */

public class DatabasePriceType {

    public static final String TABLE_NAME = "PriceType";

    public static String COL_ID = "_id";

    public static final String COL_TYPE_ID = "type_id";
    public static final String COL_TYPE_NAME_TH = "type_name_th";
    public static final String COL_TYPE_NAME_EN = "type_name_en";

    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TYPE_NAME_TH + " TEXT,"
            + COL_TYPE_NAME_EN + " TEXT,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";


}
