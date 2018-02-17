package com.stecon.patipan_on.diarycar.database.query_model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseFuelType;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.model.MyDateModify;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by patipan_on on 2/13/2018.
 */

public class FuelDataQuerySQLIte {

    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<OilDataModel> oilDataModelArrayList;


    public FuelDataQuerySQLIte(Context context) {
        this.context = context;
        this.myDbHelper = new MyDbHelper(context);
        this.sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    public ArrayList<OilDataModel> getDataFuelFromSqlite(String licensePlate) {

        if (oilDataModelArrayList != null) {
            return this.oilDataModelArrayList;
        } else {
            oilDataModelArrayList = new ArrayList<>();
            String strSql = "SELECT "
                    + "o." + DatabaseOilJournal.COL_ID + ","
                    + DatabaseOilJournal.COL_ODOMETER + ","
                    + DatabaseOilJournal.COL_UNIT_PRICE + ","
                    + DatabaseOilJournal.COL_VOLUME + ","
                    + DatabaseOilJournal.COL_TOTAL_PRICE + ","
                    + DatabaseOilJournal.COL_PAYMENT_TYPE + ","
                    + DatabaseOilJournal.COL_LATITUDE + ","
                    + DatabaseOilJournal.COL_LONGITUDE + ","
                    + DatabaseOilJournal.COL_NOTE + ","
                    + DatabaseOilJournal.COL_FUEL_TYPE + ","
                    + "o." + DatabaseOilJournal.COL_TRANSACTION_DATE + ","
                    + "f." + DatabaseFuelType.COL_FUEL_NAME_TH
                    + " FROM "
                    + DatabaseOilJournal.TABLE_NAME + " o "
                    + " JOIN " + DatabaseFuelType.TABLE_NAME + " f on o." + DatabaseOilJournal.COL_FUEL_TYPE + " = f." + DatabaseFuelType.COL_TYPE_ID
                    + " WHERE " + DatabaseOilJournal.COL_LICENSE_PLATE + " = '" + licensePlate + "'"
                    + " ORDER BY " + "o." + DatabaseOilJournal.COL_ID + " DESC";

            Log.d("STRSQL=> ", strSql);


            Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
            List<Address> addresses;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_ID));
                    double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
                    double unit_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
                    double volume = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
                    double total_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
                    String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
                    double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LATITUDE));
                    double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LONGITUDE));
                    String note = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_NOTE));
                    int fuel_type = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_FUEL_TYPE));
                    String tempDate = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_TRANSACTION_DATE));
                    String[] customDate = MyDateModify.getStrsDateTimeFromSqlite(tempDate);
                    String fuel_type_name = cursor.getString(cursor.getColumnIndex(DatabaseFuelType.COL_FUEL_NAME_TH));

                    OilDataModel oilDataModel = new OilDataModel(id, odometer, unit_price, volume, fuel_type, total_price, payment_type, latitude, longitude, note, customDate[0]);
                    oilDataModel.setFuel_type_name(fuel_type_name);
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null) {
                            String address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            oilDataModel.setStrLocation(address + " " + city + " " + state + " " + country);
                        } else {
                            oilDataModel.setStrLocation("Location Not found.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    oilDataModelArrayList.add(oilDataModel);
                    if (!cursor.isLast()) {
                        cursor.moveToNext();
                    }
                }
                return oilDataModelArrayList;
            } else {
                return null;
            }
        }
    }

    public OilDataModel getFuelDataById(int param_id) {

        String strSql = "SELECT "
                + "o." + DatabaseOilJournal.COL_ID + ","
                + DatabaseOilJournal.COL_ODOMETER + ","
                + DatabaseOilJournal.COL_UNIT_PRICE + ","
                + DatabaseOilJournal.COL_VOLUME + ","
                + DatabaseOilJournal.COL_TOTAL_PRICE + ","
                + DatabaseOilJournal.COL_PAYMENT_TYPE + ","
                + DatabaseOilJournal.COL_LATITUDE + ","
                + DatabaseOilJournal.COL_LONGITUDE + ","
                + DatabaseOilJournal.COL_NOTE + ","
                + DatabaseOilJournal.COL_FUEL_TYPE + ","
                + "o." + DatabaseOilJournal.COL_TRANSACTION_DATE + ","
                + "o." + DatabaseOilJournal.COL_STATUS + ","
                + "f." + DatabaseFuelType.COL_FUEL_NAME_TH
                + " FROM "
                + DatabaseOilJournal.TABLE_NAME + " o "
                + " JOIN " + DatabaseFuelType.TABLE_NAME + " f on o." + DatabaseOilJournal.COL_FUEL_TYPE + " = f." + DatabaseFuelType.COL_TYPE_ID
                + " WHERE o." + DatabaseOilJournal.COL_ID + " = " + param_id
                + " ORDER BY " + "o." + DatabaseOilJournal.COL_ID + " DESC";

        Log.d("STRSQL=> ", strSql);


        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
        List<Address> addresses;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_ID));
            double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
            double unit_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
            double volume = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
            double total_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
            String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
            double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LONGITUDE));
            String note = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_NOTE));
            int fuel_type = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_FUEL_TYPE));
            String tempDate = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_TRANSACTION_DATE));
            int status = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_STATUS));
            String[] customDate = MyDateModify.getStrsDateTimeFromSqlite(tempDate);
            String fuel_type_name = cursor.getString(cursor.getColumnIndex(DatabaseFuelType.COL_FUEL_NAME_TH));

            OilDataModel oilDataModel = new OilDataModel(id, odometer, unit_price, volume, fuel_type, total_price, payment_type, latitude, longitude, note, customDate[0]);
            oilDataModel.setStatus(status);
            oilDataModel.setFuel_type_name(fuel_type_name);

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    oilDataModel.setStrLocation(address + " " + city + " " + state + " " + country);
                } else {
                    oilDataModel.setStrLocation("Location Not found.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!cursor.isLast()) {
                cursor.moveToNext();
            }

            return oilDataModel;
        } else {
            return null;
        }
    }



}
