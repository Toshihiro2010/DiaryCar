package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 11/10/2017.
 */

public class DatabaseUser {


    public static final String TABLE_NAME = "User";


    public static final String COL_ID = "_id";
    public static final String COL_EMPLOYEE_ID = "employee_id";
    public static final String COL_EMPLOYEE_NAME = "employee_name";
    public static final String COL_PIN_CODE = "pin_code";
    public static final String COL_DATE_CREATE = "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strCreate = "CREATE TABLE " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EMPLOYEE_ID + " TEXT,"
            + COL_EMPLOYEE_NAME + " TEXT,"
            + COL_PIN_CODE + " TEXT,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";

}
