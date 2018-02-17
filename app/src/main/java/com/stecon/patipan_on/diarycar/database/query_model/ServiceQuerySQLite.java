package com.stecon.patipan_on.diarycar.database.query_model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabasePriceCost;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceRecords;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;
import com.stecon.patipan_on.diarycar.model.ServiceRecordModel;

import java.util.ArrayList;
/**
 * Created by patipan_on on 2/16/2018.
 */

public class ServiceQuerySQLite {

    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static ArrayList<ServiceRecordModel> serviceRecordModelArrayList;

    public ServiceQuerySQLite(Context context) {
        this.context = context;
        this.myDbHelper = new MyDbHelper(context);
        this.sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    public ArrayList<ServiceRecordModel> getDataServiceFromSqlite(String licensePlate) {
        Log.d("license plate => ", licensePlate);

        serviceRecordModelArrayList = new ArrayList<>();
        String strSql = "SELECT *"
                + " FROM "
                + DatabaseServiceRecords.TABLE_NAME
                + " WHERE " + DatabaseServiceRecords.COL_LICENSE_PLATE + " = '" + licensePlate + "'"
                + " ORDER BY " + DatabaseServiceRecords.COL_ID + " DESC";

        Log.d("STR SQL=> ", strSql);

        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int id = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_ID));
                int service_id = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_ID));
                String license_plate = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LICENSE_PLATE));
                Double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_ODOMETER));
                Double fule_level = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_FUEL_LEVEL));
                Double service_cost = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_COST));
                Double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LATITUDE));
                Double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LONGITUDE));
                String location_name = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LOCATION_NAME));
                String note = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_NOTE));

                String temp_date = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_TRANSACTION_DATE));
                String[] date = MyDateTimeModify.getStrsDateTimeFromSqlite(temp_date);

                String create_date = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_DATE_CREATE));
                String update_date = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_DATE_UPDATE));
                String create_by = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_CREATE_BY));
                String update_by = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_UPDATE_BY));
                int status = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_STATUS));

                ServiceRecordModel serviceRecordModel = new ServiceRecordModel(id, service_id, license_plate, odometer, fule_level, service_cost, latitude, longitude, location_name, note, date[0], create_date, update_date, create_by, update_by, status);
                serviceRecordModelArrayList.add(serviceRecordModel);
                cursor.moveToNext();

            }
            return serviceRecordModelArrayList;
        } else {
            return null;
        }
    }
//        if (serviceRecordModelArrayList != null) {
//            return this.serviceRecordModelArrayList;
//        } else {
//            serviceRecordModelArrayList = new ArrayList<>();
//            String strSql = "SELECT *"
//                    + " FROM "
//                    + DatabaseServiceRecords.TABLE_NAME
//                    + " WHERE " + DatabaseServiceRecords.COL_LICENSE_PLATE + " = '" + licensePlate + "'"
//                    + " ORDER BY " + DatabaseServiceRecords.COL_ID + " DESC";
//
//            Log.d("STRSQL=> ", strSql);
//
//            Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//
//                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_ID));
//                    int service_id = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_ID));
//                    String license_plate = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LICENSE_PLATE));
//                    Double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_ODOMETER));
//                    Double fule_level = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_FUEL_LEVEL));
//                    Double service_cost = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_COST));
//                    Double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LATITUDE));
//                    Double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LONGITUDE));
//                    String location_name = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LOCATION_NAME));
//                    String note = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_NOTE));
//
//                    String temp_date = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_TRANSACTION_DATE));
//                    String[] date = MyDateTimeModify.getStrsDateTimeFromSqlite(temp_date);
//
//                    String create_date = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_DATE_CREATE));
//                    String update_date = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_DATE_UPDATE));
//                    String create_by = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_CREATE_BY));
//                    String update_by = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_UPDATE_BY));
//                    int status = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_STATUS));
//
//                    ServiceRecordModel serviceRecordModel = new ServiceRecordModel(id, service_id, license_plate, odometer, fule_level, service_cost, latitude, longitude,location_name, note, date[0], create_date, update_date, create_by, update_by,status);
//                    serviceRecordModelArrayList.add(serviceRecordModel);
//                    cursor.moveToNext();
//
//                }
//                return serviceRecordModelArrayList;
//            }else{
//                return null;
//            }
}
