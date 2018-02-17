package com.stecon.patipan_on.diarycar.database;

/**
 * Created by patipan_on on 2/12/2018.
 */

public class DatabaseFuelType {

    public static final String TABLE_NAME = "FuelType";

    public static String COL_ID = "_id";

    public static final String COL_TYPE_ID = "type_id";
    public static final String COL_FUEL_NAME_TH = "type_name_th";
    public static final String COL_FUEL_NAME_EN = "type_name_en";

    public static final String COL_DATE_CREATE= "create_date";
    public static final String COL_DATE_UPDATE = "update_date";
    public static final String COL_CREATE_BY = "create_by";
    public static final String COL_UPDATE_BY = "update_by";
    public static final String COL_STATUS = "status";

    public static String strSqlCreate = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TYPE_ID + " INTEGER,"
            + COL_FUEL_NAME_TH + " TEXT,"
            + COL_FUEL_NAME_EN + " TEXT,"
            + COL_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COL_CREATE_BY + " TEXT,"
            + COL_UPDATE_BY + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";

    public static String[] myFuelDefault() {

        String bensin91 = "INSERT INTO " + TABLE_NAME + " ("
                + COL_TYPE_ID + ","
                + COL_FUEL_NAME_TH + ","
                + COL_FUEL_NAME_EN + ","
                + COL_CREATE_BY + ","
                + COL_UPDATE_BY
                + ") VALUES (1,'เบนซิน 91', 'Bensin 91', 'System' , 'System');";

        String bensin95 = "INSERT INTO " + TABLE_NAME + " ("
                + COL_TYPE_ID + ","
                + COL_FUEL_NAME_TH + ","
                + COL_FUEL_NAME_EN + ","
                + COL_CREATE_BY + ","
                + COL_UPDATE_BY
                + ") VALUES (2,'เบนซิน 95', 'Bensin 95', 'System' , 'System');";

        String gasohol91 = "INSERT INTO " + TABLE_NAME + " ("
                + COL_TYPE_ID + ","
                + COL_FUEL_NAME_TH + ","
                + COL_FUEL_NAME_EN + ","
                + COL_CREATE_BY + ","
                + COL_UPDATE_BY
                + ") VALUES (3,'แก๊ซโซฮอลล์ 91', 'gasohol 91', 'System' , 'System');";

        String gasohol95 = "INSERT INTO " + TABLE_NAME + " ("
                + COL_TYPE_ID + ","
                + COL_FUEL_NAME_TH + ","
                + COL_FUEL_NAME_EN + ","
                + COL_CREATE_BY + ","
                + COL_UPDATE_BY
                + ") VALUES (4,'แก๊ซโซฮอลล์ 95', 'gasohol 95', 'System' , 'System');";

        String decel = "INSERT INTO " + TABLE_NAME + " ("
                + COL_TYPE_ID + ","
                + COL_FUEL_NAME_TH + ","
                + COL_FUEL_NAME_EN + ","
                + COL_CREATE_BY + ","
                + COL_UPDATE_BY
                + ") VALUES (5,'ดีเซล', 'decel', 'System' , 'System');";

        String fuel[] = {bensin91, bensin95, gasohol91, gasohol95, decel};

        return fuel;
    }
}
