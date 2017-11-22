package com.stecon.patipan_on.diarycar.controller;

/**
 * Created by patipan_on on 11/10/2017.
 */

public class DatabaseUser {


    public static final String TABLE_NAME = "๊User";

    public static final String COL_EMPLOYEEID= "employeeId";
    public static final String COL_IDCARDNUMBER = "idCardNumber";
    public static final String COL_DATEBIRTH = "dateBirth";

    public static String strCreate = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EMPLOYEEID + " TEXT , "
            + COL_IDCARDNUMBER + " TEXT, "
            + COL_DATEBIRTH + " , TEXT);";
}
