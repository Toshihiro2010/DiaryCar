package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 12/25/2017.
 */

public class DatabaseLog {

    public static final String TABLE_NAME = "LOG";

    public static final String COL_MESSAGE = "message";
    public static final String COL_TRANSACTION_DATE = "transaction_datetime";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_MESSAGE + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_STATUS + " TEXT );";

}
