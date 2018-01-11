package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 1/4/2018.
 */

public class DatabaseVehicleMaster {

    public static final String TABLE_NAME = "VehicleMaster";

    public static String COL_ID = "_id";

    public static final String COL_VEHICLE_ID = "vehicle_id";
    public static final String COL_SERVICE_ID = "service_id";
    public static final String COL_LICENSE_PLATE = "license_plate";
    public static final String COL_VEHICLE_DESCRIPTION = "vehicle_description";
    public static final String COL_TANK_CAPACITY = "tank_capacity"; //ความจุน้ำมัน
    public static final String COL_NUMBER_OF_SEATS = "number_of_seats"; //จำนวนที่นั่ง
    public static final String COL_LICENSE_EXPIRED_DATE = "license_expired_date"; //วันที่เลขทะเบียนหมดอายุ
    public static final String COL_INSURANCE_COMPANY = "insurance_company"; //
    public static final String COL_INSURANCE_NUMBER = "insurance_number";
    public static final String COL_INSURANCE_EXPIRED_DATE = "insurance_expired_date";
    public static final String COL_EFFECTIVE_DATE = "effective_date";
    public static final String COL_EXPIRED_DATE = "expired_date";

    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_VEHICLE_ID + " INTEGER,"
            + COL_SERVICE_ID + " INTEGER,"
            + COL_LICENSE_PLATE + " TEXT,"
            + COL_VEHICLE_DESCRIPTION + " TEXT,"
            + COL_TANK_CAPACITY + " REAL,"
            + COL_NUMBER_OF_SEATS + " INTEGER,"
            + COL_LICENSE_EXPIRED_DATE + " DATETIME,"
            + COL_INSURANCE_COMPANY + " TEXT,"
            + COL_INSURANCE_NUMBER + " TEXT,"
            + COL_INSURANCE_EXPIRED_DATE + " DATETIME,"
            + COL_EFFECTIVE_DATE + " DATETIME,"
            + COL_EXPIRED_DATE+ " DATETIME,"

            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";


}
