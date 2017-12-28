package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stecon.patipan_on.diarycar.database.DatabaseCarDiary;
import com.stecon.patipan_on.diarycar.database.DatabaseLog;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.DatabaseTripCost;
import com.stecon.patipan_on.diarycar.database.DatabaseTripDetail;

/**
 * Created by patipan_on on 10/28/2017.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CAR";
    private static final int DB_VERSION = 1;

    public String oilTable = DatabaseOilJournal.TABLE_NAME;
    public String carTable = DatabaseCarDiary.TABLE_NAME;
    public String tripCostTable = DatabaseTripCost.TABLE_NAME;
    public String tripTable = DatabaseTripDetail.TABLE_NAME;
    public String logTable = DatabaseLog.TABLE_NAME;

    private String strSqlCar = DatabaseCarDiary.strInsert;
    private String strSqlCreateOil = DatabaseOilJournal.strSqlCreate;
    private String strSqlCreateTripCost = DatabaseTripCost.strSqlCreate;
    private String strSqlCreateTrip = DatabaseTripDetail.strSqlCreate;
    private String strSqlCreateLog = DatabaseLog.strSqlCreate;



    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strSqlCar);
        db.execSQL(strSqlCreateOil);
        db.execSQL(strSqlCreateTripCost);
        db.execSQL(strSqlCreateTrip);
        db.execSQL(strSqlCreateLog);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade DB=> ", "test");
        db.execSQL("DROP TABLE IF EXISTS " + carTable);
        db.execSQL("DROP TABLE IF EXISTS " + oilTable);
        db.execSQL("DROP TABLE IF EXISTS " + tripCostTable);
        db.execSQL("DROP TABLE IF EXISTS " + tripTable);
        db.execSQL("DROP TABLE IF EXISTS " + logTable);
        onCreate(db);

//        switch (oldVersion) {
//            case 1 :
//        }

    }
}
