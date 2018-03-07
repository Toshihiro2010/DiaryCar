package com.stecon.patipan_on.diarycar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stecon.patipan_on.diarycar.common_class.CommonSyncCar;
import com.stecon.patipan_on.diarycar.controller.CustomProgressDialog;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseVehicleMaster;
import com.stecon.patipan_on.diarycar.model.CarModel;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.Recordset;
import com.stecon.patipan_on.diarycar.model.Recordset_;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LicensePlateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLicensePlate;
    private Button btnGoMain;

    private String strLicensePlate;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final int REQUEST_CODE = 201;

    private String strUrl = "http://172.20.20.57:888/getLicenseNo";
    private String strUrlGetCarAll = "http://172.20.20.57:888/findLicenseAll";

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licen_plate);

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");

        if (!strLicensePlate.equals("")) {
            Intent intent = new Intent(LicensePlateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            if (isNetworkAvailable()) {
                Log.d("Internet => ", "Conntected");
//                SyncLicenseCarAll syncLicenseCarAll = new SyncLicenseCarAll();
//                syncLicenseCarAll.execute();
                MyDbHelper myDbHelper = new MyDbHelper(LicensePlateActivity.this);
                SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
                String strSql = "SELECT * FROM " + DatabaseVehicleMaster.TABLE_NAME;
                Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
                if (cursor.getCount() == 0) {
                    Log.d("Data Car ", "=> No Data");
                    CommonSyncCar commonSyncCar = new CommonSyncCar(LicensePlateActivity.this);
                    commonSyncCar.execute();
                } else {
                    Log.d("Data Car ", "=> Data is available.");
                }
            } else {
                Log.d("Internet => ", "No Conncet");
            }
        }
        binWidGet();
        btnGoMain.setOnClickListener(this);

    }

    private void binWidGet() {
        edtLicensePlate =  findViewById(R.id.edtLicensePlate);
        btnGoMain =  findViewById(R.id.btnGoMain);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        Boolean status = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return status;
    }

    @Override
    public void onClick(View v) {
        if(v == btnGoMain){
            strLicensePlate = edtLicensePlate.getText().toString().trim();
            if (strLicensePlate.equals("")) {
                Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
            } else {
                checkLicensePlate();

            }
        }
    }

    private void checkLicensePlate() {
        SyncCheckLicensePlate syncCheckLicensePlate = new SyncCheckLicensePlate();
        syncCheckLicensePlate.execute();

//        mySharePreferences();
//        Intent intent = new Intent();
//        intent.putExtra(MyAppConfig.licensePlate, strLicensePlate);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    private void mySharePreferences(String licensePlate) {
        editor.putString(MyAppConfig.licensePlate, licensePlate);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();

    }

    private class SyncCheckLicensePlate extends AsyncTask<Void,Void,String> {

        CustomProgressDialog customProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog = new CustomProgressDialog(LicensePlateActivity.this);
            customProgressDialog.myDialog();

        }

        @Override
        protected String doInBackground(Void... params) {
            String url = strUrl + "/" + strLicensePlate;

            Request request = new Request.Builder().get().url(url).build();
            OkHttpClient client = new OkHttpClient.Builder().build();

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return response.body().string();
                }
                return "";
            } catch (IOException e) {
                Log.d("IOException => ", e.toString());
            }
            return null;
        }

        String temp;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            customProgressDialog.onDidmiss();
            if (s != null) {
                if (!s.equals("")) {

                    Log.d("s => ", s);
                    Gson gson = new Gson();
                    CarModel carModel = gson.fromJson(s, CarModel.class);
                    int sizeCar = carModel.getRecordset().size();

                    if (sizeCar > 1) {

                        final String carArray[] = new String[sizeCar];

                        for(int i = 0 ; i < sizeCar ; i++) {
                            carArray[i] = carModel.getRecordset().get(i).getLicenseNo();
                        }
                        temp = carArray[0];
                        builder = new AlertDialog.Builder(LicensePlateActivity.this);
                        builder.setTitle("เลือกเลขทะเบียน");
                        builder.setSingleChoiceItems(carArray, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                temp = carArray[which];
                                //Toast.makeText(LicensePlateActivity.this, "like => " + carArray[which], Toast.LENGTH_SHORT).show();
                            }
                        });


                        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(LicensePlateActivity.this, "คุณเลือก " + temp, Toast.LENGTH_SHORT).show();
                                mySetLicensePlateAndFinish(temp);
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("ไม่มีเลขทะเบียนที่ต้องการ", null);
                        builder.show();


                    } else {
                        Recordset_ recordset = carModel.getRecordset().get(0);
                        Log.d("model_id => ", recordset.getModelID() + "");
                        Log.d("getCarTypeID => ", recordset.getCarTypeID() + "");
                        Log.d("getRemark => ", recordset.getRemark() + "");
                        mySetLicensePlateAndFinish(carModel.getRecordset().get(0).getLicenseNo());

                    }
                } else {
                    Toast.makeText(LicensePlateActivity.this, "เลขทะเบียนไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    edtLicensePlate.setText("");
                }
            } else {
                Toast.makeText(LicensePlateActivity.this, "ไม่สามารถเชื่อมต่อ Server ได้", Toast.LENGTH_SHORT).show();
                edtLicensePlate.setText("");

            }

        }
    }

    private void mySetLicensePlateAndFinish(String licensePlate) {
        mySharePreferences(licensePlate);
        Intent intent = new Intent();
        intent.putExtra(MyAppConfig.licensePlate, licensePlate);
        setResult(RESULT_OK, intent);
        finish();


    }

    private class SyncLicenseCarAll extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {

            Request request = new Request.Builder().get().url(strUrlGetCarAll).build();
            OkHttpClient client = new OkHttpClient.Builder().build();

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return response.body().string();
                }
            } catch (IOException e) {
                Log.d("IOException => ", e.toString());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                myDbHelper = new MyDbHelper(LicensePlateActivity.this);
                sqLiteDatabase = myDbHelper.getWritableDatabase();
                Log.d("s => ", s);
                Gson gson = new Gson();
                CarModel carModel = gson.fromJson(s, CarModel.class);
                int size_car = carModel.getRecordset().size();
                if (size_car != 0) {
                    for (int i = 0 ; i < size_car ; i++) {
                        Recordset_ recordset = carModel.getRecordset().get(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseVehicleMaster.COL_VEHICLE_ID, recordset.getCarID());
                        contentValues.put(DatabaseVehicleMaster.COL_LICENSE_PLATE, recordset.getLicenseNo());
                        contentValues.put(DatabaseVehicleMaster.COL_STATUS, recordset.getStatus());

                        sqLiteDatabase.insert(DatabaseVehicleMaster.TABLE_NAME, null, contentValues);
                        Log.d("insert => ", (i + 1) + "");
                    }
                }
            }

        }
    }
}
