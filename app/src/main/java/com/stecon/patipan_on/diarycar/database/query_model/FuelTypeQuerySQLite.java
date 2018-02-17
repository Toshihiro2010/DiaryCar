package com.stecon.patipan_on.diarycar.database.query_model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseFuelType;
import com.stecon.patipan_on.diarycar.model.FuelTypeModel;

import java.util.ArrayList;

/**
 * Created by patipan_on on 2/13/2018.
 */

public class FuelTypeQuerySQLite {

    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static ArrayList<FuelTypeModel> fuelTypeModelArrayList;



    public FuelTypeQuerySQLite(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    public FuelTypeQuerySQLite(Context context, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public ArrayList<FuelTypeModel> getFuelType() {

        if (fuelTypeModelArrayList != null) {
            return fuelTypeModelArrayList;
        } else {
            fuelTypeModelArrayList = new ArrayList<>();
            String strSqlFuelType = "SELECT * FROM " + DatabaseFuelType.TABLE_NAME;
            Cursor cursor = sqLiteDatabase.rawQuery(strSqlFuelType, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                for (int i = 0; i < cursor.getCount(); i++) {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseFuelType.COL_ID));
                    int type_id = cursor.getInt(cursor.getColumnIndex(DatabaseFuelType.COL_TYPE_ID));
                    String type_name_th = cursor.getString(cursor.getColumnIndex(DatabaseFuelType.COL_FUEL_NAME_TH));
                    String type_name_en = cursor.getString(cursor.getColumnIndex(DatabaseFuelType.COL_FUEL_NAME_EN));
                    FuelTypeModel fuelTypeModel = new FuelTypeModel(id, type_id, type_name_th, type_name_en);
                    fuelTypeModelArrayList.add(fuelTypeModel);
                    cursor.moveToNext();
                }
                return fuelTypeModelArrayList;
            } else {
                return null;
            }
        }
    }

    public String getFuelTypeById(int id) {
        String strSqlById = "SELECT * FROM "
                + DatabaseFuelType.TABLE_NAME
                + " WHERE "
                + DatabaseFuelType.COL_TYPE_ID + " = " + id;

        String fuel_name;
        Cursor cursor = sqLiteDatabase.rawQuery(strSqlById, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            fuel_name = cursor.getString(cursor.getColumnIndex(DatabaseFuelType.COL_FUEL_NAME_TH));
            return fuel_name;
        }else{
            return null;
        }
    }



}
