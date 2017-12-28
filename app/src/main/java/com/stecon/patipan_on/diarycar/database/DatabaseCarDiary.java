package com.stecon.patipan_on.diarycar.database;


/**
 * Created by patipan_on on 10/25/2017.
 */

public class DatabaseCarDiary {
    public static final String TABLE_NAME = "Diary";

    public static final String COL_DATE = "date";
    public static final String COL_TITLE = "title";
    public static final String COL_DETAIL = "detail";
    public static final String COL_KILOMETER = "kilometer";
    public static final String COL_TRANSACTION_DATE= "transaction_date";
    public static final String COL_DATE_CREATE = "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";


    public static String strInsert = "CREATE TABLE " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_TITLE + " TEXT,"
            + COL_DETAIL + " TEXT,"
            + COL_KILOMETER + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT);"
            + COL_KILOMETER + " TEXT);";

}