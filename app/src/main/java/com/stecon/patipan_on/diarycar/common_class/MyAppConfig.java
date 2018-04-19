package com.stecon.patipan_on.diarycar.common_class;

/**
 * Created by patipan_on on 12/19/2017.
 */

public class MyAppConfig {

    public static final String P_NAME = "my_custom_app_data";
    public static final String licensePlate = "licensePlate";
    public static final String trip_id = "trip_id";
    public static final String activity_code = "ActivityCode";
    public static final String language_app = "Language_app";
    public static final String pin_code = "pin_code";
    public static final String employee_id = "employee_id";
    public static final String employee_name = "employee_name";


    public static long num_trip_id = 0;

    public static long getNum_trip_id() {
        return num_trip_id;
    }

    public static void setNum_trip_id(long num_trip_id) {
        MyAppConfig.num_trip_id = num_trip_id;
    }
}
