package com.stecon.patipan_on.diarycar;

/**
 * Created by patipan_on on 11/10/2017.
 */

public class DatabaseUser {

    private static final String DB_NAME = "CAR";

    public static final String TABLE_NAME = "๊User";

    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_STATUS = "status";

    public static String strCreate = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USERNAME + " TEXT , "
            + COL_PASSWORD + " TEXT, "
            + COL_STATUS + " , INTEGER);";
}
