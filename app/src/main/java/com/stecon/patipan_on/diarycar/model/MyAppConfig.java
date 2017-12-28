package com.stecon.patipan_on.diarycar.model;

/**
 * Created by patipan_on on 12/19/2017.
 */

public class MyAppConfig {

    public static final String P_NAME = "App_Config";
    public static final String licenPlate = "licensePlate";
    public static final String trip_id = "trip_id";
    public static final String activity_code = "ActivityCode";
    public static final String language_app = "Language_app";

    public static long num_trip_id = 0;

    public static long getNum_trip_id() {
        return num_trip_id;
    }

    public static void setNum_trip_id(long num_trip_id) {
        MyAppConfig.num_trip_id = num_trip_id;
    }
}
