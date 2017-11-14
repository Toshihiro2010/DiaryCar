package com.stecon.patipan_on.diarycar.controller;


/**
 * Created by patipan_on on 10/25/2017.
 */

public class DatabaseCarDiary{
    private static final String DB_NAME = "CAR";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Diary";

    public static final String COL_DATE = "date";
    public static final String COL_TITLE = "title";
    public static final String COL_DETAIL = "detail";
    public static final String COL_KILOMETER = "kilometer";

    public static String strInsert = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + COL_TITLE + " TEXT, "
            + COL_DETAIL + " TEXT, "
            + COL_KILOMETER + " TEXT);";

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColDate() {
        return COL_DATE;
    }

    public static String getColTitle() {
        return COL_TITLE;
    }

    public static String getColDetail() {
        return COL_DETAIL;
    }

    public static String getColKilometer() {
        return COL_KILOMETER;
    }

    public static String getStrInsert() {
        return strInsert;
    }

}
