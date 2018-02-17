package com.stecon.patipan_on.diarycar.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;
import com.stecon.patipan_on.diarycar.database.DatabaseLog;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.DatabasePriceCost;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceRecords;
import com.stecon.patipan_on.diarycar.database.DatabaseStatusToServer;
import com.stecon.patipan_on.diarycar.database.DatabaseTripDetail;
import com.stecon.patipan_on.diarycar.model.LogModel;
import com.stecon.patipan_on.diarycar.model.MyModelSynToServer;
import com.stecon.patipan_on.diarycar.model.OilDataModel;
import com.stecon.patipan_on.diarycar.model.ServiceRecordModel;
import com.stecon.patipan_on.diarycar.model.PriceCostModel;
import com.stecon.patipan_on.diarycar.model.TripDetailModel;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by patipan_on on 1/8/2018.
 */

public class MySendToServer {

    private Context context;
    private Boolean statusNetWork;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MySendToServer(Context context) {
        this.context = context;
        this.statusNetWork = false;
        this.myDbHelper = new MyDbHelper(context);
        this.sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    public void statusIdCheck() {
        String strSQL = "SELECT * FROM " + DatabaseStatusToServer.TABLE_NAME + " WHERE " + DatabaseStatusToServer.COL_STATUS + " = 0";
        Cursor cursor = sqLiteDatabase.rawQuery(strSQL, null);
        long insertId;
        Log.d("t = > ", cursor.getCount() + "");
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseStatusToServer.COL_MESSAGE, "Create");
            insertId = sqLiteDatabase.insert(DatabaseStatusToServer.TABLE_NAME, null, contentValues);
            Log.d("sql => ", "insert");
        }else{
            cursor.moveToFirst();
            insertId = cursor.getInt(cursor.getColumnIndex(DatabaseStatusToServer.COL_ID));
            Log.d("sql => ", "select");
        }
        Log.d("id => = > ", insertId + "");
    }

    public long checkInsertStatusOfSharePerference() {
        String strSQL = "SELECT * FROM " + DatabaseStatusToServer.TABLE_NAME + " WHERE " + DatabaseStatusToServer.COL_STATUS + " = 0";
        Cursor cursor = sqLiteDatabase.rawQuery(strSQL, null);
        long insertId = 0;

        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseStatusToServer.COL_MESSAGE, "Create");
            insertId = sqLiteDatabase.insert(DatabaseStatusToServer.TABLE_NAME, null, contentValues);
            Log.d("sql => ", "insert");
        }else{
            cursor.moveToFirst();
            insertId = cursor.getInt(cursor.getColumnIndex(DatabaseStatusToServer.COL_ID));
            Log.d("sql => ", "select");
        }
        Log.d("id => = > ", insertId + "");
        return insertId;
    }

    public long myCheckIdToServer() {
        String strSQL = "SELECT * FROM " + DatabaseStatusToServer.TABLE_NAME + " WHERE " + DatabaseStatusToServer.COL_STATUS + " = 0";
        Cursor cursor = sqLiteDatabase.rawQuery(strSQL, null);
        long insertId = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            insertId = cursor.getInt(cursor.getColumnIndex(DatabaseStatusToServer.COL_ID));
            Log.d("sql => ", "select");
        }
        return insertId;

    }




    public Boolean checkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        Boolean status = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return status;
    }

    public void syncToServer() {
        statusNetWork = checkStatus();
        long idToServer = myCheckIdToServer();
        if (statusNetWork == true && idToServer != 0) {
            Log.d("syncToServer =>", "Sync To Server");
            MySyncToServer mySyncToServer = new MySyncToServer();
            mySyncToServer.execute();

        } else if(statusNetWork == false && idToServer != 0) {
            Log.d("syncToServer =>", "No Internet connected");
        }else if(statusNetWork == true && idToServer == 0) {
            Log.d("syncToServer =>", "No Content");
        }else{
            Log.d("syncToServer =>", "No Internet and No Content");

        }
    }



    public class MySyncToServer extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... params) {

            String dataSyncToServer = myCustomQueryArrayList();
            String strURL = "http://172.20.20.57:7777/testobject";
            MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");

            RequestBody body = RequestBody.create(mediaTypeJson, dataSyncToServer);
            Request request = new Request.Builder()
                    .url(strURL)
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder().build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("response => ", response.body().toString());
                return response.body().string();
            } catch (Exception e) {
                Log.d("response error => ", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    Log.d("res => ", s);
                    //changeStatus();

                } catch (Exception e) {
                    Log.d("exception => ", e.toString());
                }
            }
        }
    }

    private void changeStatus() {

        String sqlUpdateOil = "UPDATE " + DatabaseOilJournal.TABLE_NAME
                        + " set " + DatabaseOilJournal.COL_STATUS + " = 1 "
                        + " WHERE " + DatabaseOilJournal.COL_STATUS + " != 1";
        sqLiteDatabase.rawQuery(sqlUpdateOil, null);

        String sqlUpdateTripCost = "UPDATE " + DatabasePriceCost.TABLE_NAME
                + " set " + DatabasePriceCost.COL_STATUS + " = 1 "
                + " WHERE " + DatabasePriceCost.COL_STATUS + " != 1";
        sqLiteDatabase.rawQuery(sqlUpdateTripCost, null);

        String sqlUpdateTripDetail = "UPDATE " + DatabaseTripDetail.TABLE_NAME
                + " set " + DatabaseTripDetail.COL_STATUS + " = 1 "
                + " WHERE " + DatabaseTripDetail.COL_STATUS + " != 1";
        sqLiteDatabase.rawQuery(sqlUpdateTripDetail, null);

        String sqlUpdateServiceRecord = "UPDATE " + DatabaseServiceRecords.TABLE_NAME
                + " set " + DatabaseServiceRecords.COL_STATUS + " = 1 "
                + " WHERE " + DatabaseServiceRecords.COL_STATUS + " != 1";
        sqLiteDatabase.rawQuery(sqlUpdateServiceRecord, null);




    }

    private String myCustomQueryArrayList() {

        MyModelSynToServer myModelSynToServer1 = customQueryData();
        String dataSync = new Gson().toJson(myModelSynToServer1);
        Log.d("dataSync => ", dataSync);

        return dataSync;
    }

    private MyModelSynToServer customQueryData() {
        ArrayList<OilDataModel> oilDataModelArrayList = new ArrayList<>();
        String sqlFuel = "SELECT * FROM " + DatabaseOilJournal.TABLE_NAME + " WHERE " + DatabaseOilJournal.COL_STATUS + " = 0";
        Cursor cursorFuel = sqLiteDatabase.rawQuery(sqlFuel, null);

        ArrayList<PriceCostModel> priceCostModelArrayList = new ArrayList<>();
        String sqlTripCost = "SELECT * FROM " + DatabasePriceCost.TABLE_NAME + " WHERE " + DatabasePriceCost.COL_STATUS + " = 0";
        Cursor cursorTripCost = sqLiteDatabase.rawQuery(sqlTripCost, null);

        ArrayList<TripDetailModel> tripDetailModelArrayList = new ArrayList<>();
        String sqlTripDetail = "SELECT * FROM " + DatabaseTripDetail.TABLE_NAME + " WHERE " + DatabaseTripDetail.COL_STATUS + " = 0";
        Cursor cursorTripDetail = sqLiteDatabase.rawQuery(sqlTripDetail, null);

        ArrayList<ServiceRecordModel> serviceRecordModelArrayList = new ArrayList<>();
        String sqlServiceRecord = "SELECT * FROM " + DatabaseServiceRecords.TABLE_NAME + " WHERE " + DatabaseServiceRecords.COL_STATUS + " = 0";
        Cursor cursorServiceRecord = sqLiteDatabase.rawQuery(sqlServiceRecord, null);

        ArrayList<LogModel> logModelArrayList = new ArrayList<>();
        String sqlLog = "SELECT * FROM " + DatabaseLog.TABLE_NAME + " WHERE " + DatabaseLog.COL_STATUS + " = 0";
        Cursor cursorLog= sqLiteDatabase.rawQuery(sqlLog, null);

        int sizeCursor[] = {cursorFuel.getCount(), cursorServiceRecord.getCount(), cursorTripCost.getCount(), cursorTripDetail.getCount(), cursorLog.getCount()};

        int max = findSize(sizeCursor);

        for(int i = 0 ; i < max ; i++) {
            if (cursorFuel != null && !cursorFuel.isAfterLast()) {
                if (i == 0) {
                    cursorFuel.moveToFirst();
                }
                int id = cursorFuel.getInt(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_ID));
                String license_plate = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_LICENSE_PLATE));
                double odometer = cursorFuel.getDouble(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
                double unit_price = cursorFuel.getDouble(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
                double volume = cursorFuel.getDouble(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
                double total_rpice = cursorFuel.getDouble(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
                String payment_type = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
                double latitude = cursorFuel.getDouble(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_LATITUDE));
                double longitude = cursorFuel.getDouble(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_LONGITUDE));
                String note = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_NOTE));
                int fueltype = cursorFuel.getInt(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_FUEL_TYPE));
                String transaction_date = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_TRANSACTION_DATE));
                String location_date = "";
                String create_date = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_DATE_CREATE));
                String update_date = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_DATE_UPDATE));
                String create_by = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_CREATE_BY));
                String update_by = cursorFuel.getString(cursorFuel.getColumnIndex(DatabaseOilJournal.COL_UPDATE_BY));
                int status = cursorFuel.getInt(cursorTripCost.getColumnIndex(DatabaseOilJournal.COL_STATUS));

                OilDataModel oilDataModel = new OilDataModel(id, license_plate, odometer, unit_price, volume, fueltype, total_rpice, payment_type, latitude, longitude, note, transaction_date,location_date, create_date, update_date, create_by, update_by,status);

                oilDataModelArrayList.add(oilDataModel);

                cursorFuel.moveToNext();
            }

            if (cursorServiceRecord != null && !cursorServiceRecord.isAfterLast() ) {
                if (i == 0) {
                    cursorServiceRecord.moveToFirst();
                }
                int id = cursorServiceRecord.getInt(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_ID));
                int service_id = cursorServiceRecord.getInt(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_ID));
                String license_plate = cursorServiceRecord.getString(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_LICENSE_PLATE));
                Double odometeer = cursorServiceRecord.getDouble(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_ODOMETER));
                Double fuel = cursorServiceRecord.getDouble(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_FUEL_LEVEL));
                Double service_cost = cursorServiceRecord.getDouble(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_COST));
                Double latitude = cursorServiceRecord.getDouble(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_LATITUDE));
                Double longitude = cursorServiceRecord.getDouble(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_LONGITUDE));
                String location_name = cursorServiceRecord.getString(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_LOCATION_NAME));
                String note = cursorServiceRecord.getString(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_NOTE));
                String transaction_date = cursorServiceRecord.getString(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_TRANSACTION_DATE));
                String create_date = cursorServiceRecord.getString(cursorFuel.getColumnIndex(DatabaseServiceRecords.COL_DATE_CREATE));
                String update_date = cursorServiceRecord.getString(cursorFuel.getColumnIndex(DatabaseServiceRecords.COL_DATE_UPDATE));
                String create_by = cursorServiceRecord.getString(cursorFuel.getColumnIndex(DatabaseServiceRecords.COL_CREATE_BY));
                String update_by = cursorServiceRecord.getString(cursorFuel.getColumnIndex(DatabaseServiceRecords.COL_UPDATE_BY));
                int status = cursorServiceRecord.getInt(cursorTripCost.getColumnIndex(DatabaseServiceRecords.COL_STATUS));

                ServiceRecordModel serviceRecordModel = new ServiceRecordModel(
                        id,
                        service_id,
                        license_plate,
                        odometeer,
                        fuel,
                        service_cost,
                        latitude,
                        longitude,
                        location_name,
                        note,
                        transaction_date,
                        create_date,
                        update_date,
                        create_by,
                        update_by,
                        status);

                serviceRecordModelArrayList.add(serviceRecordModel);


                Log.d("serviceRecord => ", cursorServiceRecord.getString(cursorServiceRecord.getColumnIndex(DatabaseServiceRecords.COL_NOTE)));
                cursorServiceRecord.moveToNext();
            }
            if (cursorTripCost != null && !cursorTripCost.isAfterLast()) {
                if (i == 0) {
                    cursorTripCost.moveToFirst();
                }
                int id = cursorTripCost.getInt(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_ID));
                String license_plate = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_LICENSE_PLATE));
                String price_type = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_PRICE_TYPE));
                String title = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_PRICE_TITLE));
                double money = cursorTripCost.getDouble(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_PRICE_MONEY));
                String note = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_NOTE));
                String transaction_date = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_TRANSACTION_DATE));
                String create_date = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_DATE_CREATE));
                String update_date = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_DATE_UPDATE));
                String create_by = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_CREATE_BY));
                String update_by = cursorTripCost.getString(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_UPDATE_BY));
                int status = cursorTripCost.getInt(cursorTripCost.getColumnIndex(DatabasePriceCost.COL_STATUS));

                PriceCostModel priceCostModel = new PriceCostModel(id, license_plate, price_type, title, money, note, transaction_date, create_date, update_date, create_by, update_by, status);

                priceCostModelArrayList.add(priceCostModel);

                cursorTripCost.moveToNext();
            }
            if (cursorTripDetail != null && !cursorTripDetail.isAfterLast()) {
                if (i == 0) {
                    cursorTripDetail.moveToFirst();
                }
                int id = cursorTripDetail.getInt(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ID));
                String licen_plate = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_LICENSEPLATE));
                String reservation_number = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_RESERVATION_NUMBER));
                String purpose = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_PURPOSE));
                String depatrture_date = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_DATETIME));
                Double departure_odometer = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_ODOMETER));
                Double departure_latitude = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_LATITUDE));
                Double departure_longitude = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_LONGITUDE));
                Double departure_location_name = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_LOCATION_NAME));
                String arrival_date = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ARRIVAL_DATETIME));
                Double arrival_odometer = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ARRIVAL_ODOMETER));
                Double arrival_latitude = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ARRIVAL_LATITUDE));
                Double arrival_longitude = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ARRIVAL_LONGITUDE));
                String arrival_location_name = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ARRIVAL_LOCATION_NAME));
                String arrival_parking_location = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_ARRIVAL_PARKING_LOCATION));
                Double fuel_level = cursorTripDetail.getDouble(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_FUEL_LEVEL));
                String note = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_NOTE));
                String transaction_date = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_TRANSACTION_DATE));
                String date_create = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DATE_CREATE));
                String date_update = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_DATE_UPDATE));
                String create_by = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_BY_CREATE));
                String update_by = cursorTripDetail.getString(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_UPDATE_BY));
                int status = cursorTripDetail.getInt(cursorTripDetail.getColumnIndex(DatabaseTripDetail.COL_STATUS));

                TripDetailModel tripDetailModel = new TripDetailModel(
                        id, licen_plate,
                        reservation_number,
                        purpose,
                        depatrture_date,
                        departure_odometer,
                        departure_latitude,
                        departure_longitude,
                        departure_location_name,
                        arrival_date,
                        arrival_odometer,
                        arrival_latitude,
                        arrival_longitude,
                        arrival_location_name,
                        arrival_parking_location,
                        fuel_level, note,
                        transaction_date,
                        date_create, date_update,
                        create_by,
                        update_by,
                        status);
                tripDetailModelArrayList.add(tripDetailModel);

                cursorTripDetail.moveToNext();
            }
        }

        MyModelSynToServer myModelSynToServer = new MyModelSynToServer();
        myModelSynToServer.setDate_sync("1/10/2560");
        myModelSynToServer.setSync_by("User");

        myModelSynToServer.setFuelData(oilDataModelArrayList);
        myModelSynToServer.setTripCost(priceCostModelArrayList);
        myModelSynToServer.setTripDetail(tripDetailModelArrayList);
        myModelSynToServer.setServiceData(serviceRecordModelArrayList);

        return myModelSynToServer;
    }

    private int findSize(int[] sizeCursor) {

        int max = sizeCursor[0];
        for(int i = 1 ; i < sizeCursor.length ; i++) {
            if (max < sizeCursor[i]) {
                max = sizeCursor[i];
            }
        }
        return max;
    }


}
