package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by patipan_on on 10/28/2017.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CAR";
    private static final int DB_VERSION = 1;

    public String oilTable = DatabaseOilDiary.TABLE_NAME;
    public String carTable = DatabaseCarDiary.TABLE_NAME;

    private String strSqlCar = DatabaseCarDiary.strInsert;
    private String strSqlOil = DatabaseOilDiary.strSqlInsert;



    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strSqlCar);
        db.execSQL(strSqlOil);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + carTable);
        db.execSQL("DROP TABLE IF EXISTS " + oilTable);
        onCreate(db);

    }
}
