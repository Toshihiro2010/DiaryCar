package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stecon.patipan_on.diarycar.database.DatabaseCarDiary;
import com.stecon.patipan_on.diarycar.database.DatabaseFuelType;
import com.stecon.patipan_on.diarycar.database.DatabaseLog;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.DatabasePriceCost;
import com.stecon.patipan_on.diarycar.database.DatabasePriceType;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceMaster;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceRecords;
import com.stecon.patipan_on.diarycar.database.DatabaseStatusToServer;
import com.stecon.patipan_on.diarycar.database.DatabaseTripDetail;
import com.stecon.patipan_on.diarycar.database.DatabaseUser;
import com.stecon.patipan_on.diarycar.database.DatabaseVehicleMaster;

/**
 * Created by patipan_on on 10/28/2017.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CAR";
    private static final int DB_VERSION = 1;

    public String oilTable = DatabaseOilJournal.TABLE_NAME;
    public String carTable = DatabaseCarDiary.TABLE_NAME;
    public String tripCostTable = DatabasePriceCost.TABLE_NAME;
    public String tripTable = DatabaseTripDetail.TABLE_NAME;
    public String logTable = DatabaseLog.TABLE_NAME;
    public String serviceRecordTable = DatabaseServiceRecords.TABLE_NAME;
    public String serviceMasterTable = DatabaseServiceMaster.TABLE_NAME;
    public String statusToServerTable = DatabaseStatusToServer.TABLE_NAME;
    public String priceTypeTable = DatabasePriceType.TABLE_NAME;
    public String vehicleMasterTable = DatabaseVehicleMaster.TABLE_NAME;
    public String userMasterTable = DatabaseUser.TABLE_NAME;
    public String fuelTypTable = DatabaseFuelType.TABLE_NAME;

    private String strSqlCar = DatabaseCarDiary.strInsert;
    private String strSqlCreateOil = DatabaseOilJournal.strSqlCreate;
    private String strSqlCreateTripCost = DatabasePriceCost.strSqlCreate;
    private String strSqlCreateTrip = DatabaseTripDetail.strSqlCreate;
    private String strSqlCreateLog = DatabaseLog.strSqlCreate;
    private String strSqlCreateServiceRecord = DatabaseServiceRecords.strSqlCreate;
    private String strSqlCreateServiceMaster = DatabaseServiceMaster.strSqlCreate;
    private String strSqlCreateStatusToServer = DatabaseStatusToServer.strSqlCreate;
    private String strSqlCreatePriceType = DatabasePriceType.strSqlCreate;
    private String strSqlCreateVehicleMaster = DatabaseVehicleMaster.strSqlCreate;
    private String strSqlCreateUser = DatabaseUser.strCreate;
    private String strSqlCreateFuelType = DatabaseFuelType.strSqlCreate;


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
        db.execSQL(strSqlCreateServiceRecord);
        db.execSQL(strSqlCreateStatusToServer);
        db.execSQL(strSqlCreatePriceType);
        db.execSQL(strSqlCreateServiceMaster);
        db.execSQL(strSqlCreateVehicleMaster);
        db.execSQL(strSqlCreateUser);
        db.execSQL(strSqlCreateFuelType);

        String fuelSystem[] = DatabaseFuelType.myFuelDefault();
        for(int i = 0 ; i < fuelSystem.length ; i++) {
            String strInsertTemp = fuelSystem[i];
            db.execSQL(strInsertTemp);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade DB=> ", "test");
        db.execSQL("DROP TABLE IF EXISTS " + carTable);
        db.execSQL("DROP TABLE IF EXISTS " + oilTable);
        db.execSQL("DROP TABLE IF EXISTS " + tripCostTable);
        db.execSQL("DROP TABLE IF EXISTS " + tripTable);
        db.execSQL("DROP TABLE IF EXISTS " + logTable);
        db.execSQL("DROP TABLE IF EXISTS " + strSqlCreateServiceRecord);
        db.execSQL("DROP TABLE IF EXISTS " + serviceRecordTable);
        db.execSQL("DROP TABLE IF EXISTS " + statusToServerTable);
        db.execSQL("DROP TABLE IF EXISTS " + priceTypeTable);
        db.execSQL("DROP TABLE IF EXISTS " + serviceMasterTable);
        db.execSQL("DROP TABLE IF EXISTS " + vehicleMasterTable);
        db.execSQL("DROP TABLE IF EXISTS " + userMasterTable);
        onCreate(db);

//        switch (oldVersion) {
//            case 1 :
//        }

    }
}
