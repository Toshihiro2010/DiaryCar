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

    public static String strInsert = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + COL_TITLE + " TEXT, "
            + COL_DETAIL + " TEXT, "
            + COL_KILOMETER + " TEXT);";

}