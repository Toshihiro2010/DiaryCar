package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 11/30/2017.
 */

public class DatabaseTripDetail {

    public static final String TABLE_NAME = "TRIP_DETAIL";

    public static final String COL_ID = "_id";
    public static final String COL_LICENSEPLATE = "license_plate";
    public static final String COL_RESERVATION_NUMBER = "reservation_number";
    public static final String COL_PURPOSE = "purpose";

    public static final String COL_DEPARTURE_DATETIME = "departure_date";
    public static final String COL_DEPARTURE_ODOMETER = "departure_odometer";
    public static final String COL_DEPARTURE_LATITUDE = "departure_latitude";
    public static final String COL_DEPARTURE_LONGITUDE = "departure_longitude";
    public static final String COL_DEPARTURE_LOCATION_NAME = "departure_location_name";

    public static final String COL_ARRIVAL_DATETIME = "arrival_date";
    public static final String COL_ARRIVAL_ODOMETER = "arrival_odometer";
    public static final String COL_ARRIVAL_LATITUDE= "arrival_latitude";
    public static final String COL_ARRIVAL_LONGITUDE = "arrival_longitude";
    public static final String COL_ARRIVAL_LOCATION_NAME = "arrival_location_name";
    public static final String COL_ARRIVAL_PARKING_LOCATION = "arrival_parking_location";
    public static final String COL_FUEL_LEVEL = "fuel_level";

    public static final String COL_NOTE = "note";
    public static final String COL_TRANSACTION_DATE = "transaction_date";
    public static final String COL_DATE_CREATE = "date_create";
    public static final String COL_DATE_UPDATE = "date_update";
    public static final String COL_BY_CREATE = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_LICENSEPLATE + " TEXT,"
            + COL_RESERVATION_NUMBER + " TEXT,"
            + COL_PURPOSE + " TEXT,"
            + COL_DEPARTURE_DATETIME + " DATETIME,"
            + COL_DEPARTURE_ODOMETER + " REAL,"
            + COL_DEPARTURE_LATITUDE + " REAL,"
            + COL_DEPARTURE_LONGITUDE + " REAL,"
            + COL_DEPARTURE_LOCATION_NAME + " TEXT,"
            + COL_ARRIVAL_DATETIME + " DATETIME,"
            + COL_ARRIVAL_ODOMETER+ " REAL,"
            + COL_ARRIVAL_LATITUDE + " REAL,"
            + COL_ARRIVAL_LONGITUDE + " REAL,"
            + COL_ARRIVAL_LOCATION_NAME + " TEXT,"
            + COL_ARRIVAL_PARKING_LOCATION + " TEXT,"
            + COL_FUEL_LEVEL + " REAL,"
            + COL_NOTE + " TEXT,"
            + COL_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_BY_CREATE + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";

}
