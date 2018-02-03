package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 1/6/2018.
 */

public class DatabaseStatusToServer {

    public static final String TABLE_NAME = "StatusToServer";

    public static final String COL_ID = "_id";

    public static final String COL_STATUS = "status";
    public static final String COL_MESSAGE = "message";
    public static final String COL_TRANSACTION_DATE = "transaction_date";
    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_STATUS + " INTEGER DEFAULT 0,"
            + COL_MESSAGE + " TEXT,"
            + COL_TRANSACTION_DATE+ " DATETIME,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT);";
}
