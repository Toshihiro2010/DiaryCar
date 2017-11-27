package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 11/23/2017.
 */

public class DatabaseVehicleApply {

    public static final String TABLE_NAME = "VEHICLE_APPLY";

    public static final String COL_LICENSE_PLATE = "license_plate";
    public static final String COL_STATUS = "status";
    public static final String COL_DATE = "date";

    public static final String strCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_LICENSE_PLATE + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0,"
            + COL_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";



}
