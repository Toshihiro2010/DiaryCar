package com.stecon.patipan_on.diarycar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyAddPermissionLocation;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.MyLocationFirst;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

public class OilJournalActivity extends AppCompatActivity implements View.OnClickListener, OnLocationUpdatedListener, CustomAlertDialog.OnMyDialogActivity, TextWatcher, MyAddPermissionLocation.OnNextFunction, MyAddPermissionLocation.OnCustomClickDialog {

    //view
    private EditText edtOdometer;
    private EditText edtUnitPrice;
    private EditText edtMoneyTotal;
    private EditText edtNote;

    private TextView tvShowVolume;

    private Button btnOilSave;
    private Button btnOilCancel;

    private Spinner spinnerOil;
    private Spinner spinnerPayMentType;


    //getDataCheck
    private String strOdometer;
    private String strUnitPrice;
    private String strMoneyTotal;

    //getValueToDataToSQLite
    private String strNote;
    private String strFuelType;
    private String strPaymentType;

    private int partialFillUp;

    // get for System
    private String strLicensePlate;
    private Double latitude;
    private Double longitude;

    //trance to double
    private Double douOdometer;
    private Double douUnitPrice;
    private Double douMoneytTotal;

    //calculate
    private Double douVolume;

    //ProgresDialog
    private ProgressDialog progressDialog;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    private String tempShowVoulume;

    private int id;
    private int mode = 0;

    //status to server 0 = no , 1 = yes , 2 = edit
    private int statusToServer = 0;

    private MyAddPermissionLocation myAddPermissionLocation;
    private MyLocationFirst myLocationFirst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_journal);
        bindWidget();
        try {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getInt("data_id");
        } catch (NullPointerException e) {
            Log.d("id => ", id + " ");
        }

        myAddPermissionLocation = new MyAddPermissionLocation(OilJournalActivity.this);
        myAddPermissionLocation.setOnNextFunction(OilJournalActivity.this);
        myAddPermissionLocation.setOnCustomClickDialog(OilJournalActivity.this);
        myAddPermissionLocation.checkLocation();


        myDbHelper = new MyDbHelper(OilJournalActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        mySetSpinner();
        if (id != 0) {
            mySetQueryText();
            mode = 2;
        }
        btnOilSave.setOnClickListener(this);
        btnOilCancel.setOnClickListener(this);
        edtUnitPrice.addTextChangedListener(this);
        edtMoneyTotal.addTextChangedListener(this);

    }

    private void mySetQueryText() {
        String strSql = "SELECT * FROM " + DatabaseOilJournal.TABLE_NAME + " WHERE " + DatabaseOilJournal.COL_ID + " = " + id;
        cursor = sqLiteDatabase.rawQuery(strSql , null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            douOdometer = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
            douUnitPrice = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
            douMoneytTotal = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
            douVolume = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
            strNote = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_NOTE));
            strFuelType = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_FUEL_TYPE));
            strPaymentType = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
            statusToServer = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_STATUS));



            edtOdometer.setText(douOdometer + "");
            edtUnitPrice.setText(douUnitPrice + "");
            edtMoneyTotal.setText(douMoneytTotal + "");
            tvShowVolume.setText(douVolume + "");
            edtNote.setText(strNote);
            ArrayAdapter<CharSequence> oilList = ArrayAdapter.createFromResource(this, R.array.oil_array, R.layout.support_simple_spinner_dropdown_item);
            if (!strFuelType.equals(null)) {
                int spinnerPosition = oilList.getPosition(strFuelType);
                spinnerOil.setSelection(spinnerPosition);
            } else {
                spinnerOil.setAdapter(oilList);
            }
            ArrayAdapter<CharSequence> moneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.support_simple_spinner_dropdown_item);
            if (!strPaymentType.equals(null)) {
                int spinnerPosition = moneyPayType.getPosition(strPaymentType);
                spinnerPayMentType.setSelection(spinnerPosition);
            } else {
                spinnerPayMentType.setAdapter(moneyPayType);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sp.getString(MyAppConfig.licensePlate, "");
        if (strLicensePlate.equals("")) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
            customAlertDialog.setTitle(getResources().getString(R.string.message_no_license_plate));
            customAlertDialog.setMessage(getResources().getString(R.string.message_should_input_license_plate));
            customAlertDialog.myDefaultDialog();
            customAlertDialog.show();
            customAlertDialog.setOnMyDialogActivity(OilJournalActivity.this);
        }
    }


    private void mySetSpinner() {
        ArrayAdapter<CharSequence> oilList = ArrayAdapter.createFromResource(this, R.array.oil_array, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> moneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.support_simple_spinner_dropdown_item);
        spinnerOil.setAdapter(oilList);
        spinnerPayMentType.setAdapter(moneyPayType);
    }

    private void bindWidget() {
        edtOdometer = (EditText) findViewById(R.id.edtOdometer);
        edtUnitPrice = (EditText) findViewById(R.id.edtUnitPrice);
        edtMoneyTotal = (EditText) findViewById(R.id.edtMoneyTotal);
        spinnerOil = (Spinner) findViewById(R.id.spinnerOil);
        btnOilSave = (Button) findViewById(R.id.btnOilSave);
        spinnerPayMentType = (Spinner) findViewById(R.id.spinnerMoneyPay);
        edtNote = (EditText) findViewById(R.id.edtNote);
        tvShowVolume = (TextView) findViewById(R.id.txtVolumeShow);
        btnOilCancel = (Button) findViewById(R.id.btnOilCancel);
    }


    @Override
    public void onClick(View v) {

        if (v == btnOilSave) {
            progressDialog = new ProgressDialog(OilJournalActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setTitle(getResources().getString(R.string.message_save_data_title));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            Boolean check = myCheckData();
            Log.d("check => ", check.toString());
            if (!check) {
                //Cancel Save
                progressDialog.dismiss();
                Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
            } else {
                //MainSave
                processCalculate(); // calculate Voulume fuel
                if (mode == 0) {
                    myLocation();
                } else if (mode == 2) {
                    onEditSQLite();
                }
            }
        } else if (v == btnOilCancel) {
            mySetEmptyText();
            finish();
        }
    }

    private void processCalculate() {
        douUnitPrice = Double.valueOf(strUnitPrice);
        douMoneytTotal = Double.valueOf(strMoneyTotal);
        douVolume = douMoneytTotal / douUnitPrice;
    }

    private void myLocation() {

        if (SmartLocation.with(this).location().state().locationServicesEnabled()) {
            Log.d("process => ", "true");
            LocationParams params = new LocationParams.Builder()
                    .setAccuracy(LocationAccuracy.HIGH)
                    .build();

            SmartLocation.with(this)
                    .location(new LocationGooglePlayServicesWithFallbackProvider(this))
                    .oneFix()
                    .config(params)
                    .start(this);

        } else {
            progressDialog.dismiss();
            Log.d("process => ", "false");
            locationServiceUnavailabled();
        }
    }

    private void locationServiceUnavailabled() {
        Toast.makeText(this, getResources().getString(R.string.message_error), Toast.LENGTH_SHORT).show();
    }

    private Boolean myCheckData() {
        myGetData();
        if (strOdometer.equals("") || strUnitPrice.equals("") || strMoneyTotal.equals("") || strFuelType.equals("") || strPaymentType.equals("")) {

            return false;
        } else {
            douOdometer = Double.valueOf(strOdometer);
            return true;
        }
    }

    private void myGetData() {
        strOdometer = edtOdometer.getText().toString().trim();
        strFuelType = spinnerOil.getSelectedItem().toString().trim();
        strPaymentType = spinnerPayMentType.getSelectedItem().toString().trim();
        strNote = edtNote.getText().toString().trim();
        strMoneyTotal = edtMoneyTotal.getText().toString().trim();
        strUnitPrice = edtUnitPrice.getText().toString().trim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OnActivity => ", "onPause");
        SmartLocation
                .with(this)
                .location()
                .stop();

        myDbHelper.close();
        sqLiteDatabase.close();

    }

    @Override
    public void onLocationUpdated(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        onInsertSQLite();
    }

    private void onEditSQLite() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOilJournal.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabaseOilJournal.COL_ODOMETER, douOdometer);
        contentValues.put(DatabaseOilJournal.COL_UNIT_PRICE, douUnitPrice);
        contentValues.put(DatabaseOilJournal.COL_FUEL_TYPE, strFuelType);
        contentValues.put(DatabaseOilJournal.COL_VOLUME, douVolume);
        contentValues.put(DatabaseOilJournal.COL_TOTAL_PRICE, douMoneytTotal);
        contentValues.put(DatabaseOilJournal.COL_PAYMENT_TYPE, strPaymentType);
        contentValues.put(DatabaseOilJournal.COL_NOTE, strNote);
        contentValues.put(DatabaseOilJournal.COL_DATE_UPDATE, dateFormat.format(date));

        if (statusToServer == 1) {
            statusToServer = 2;
            contentValues.put(DatabaseOilJournal.COL_STATUS, statusToServer);
        }

        sqLiteDatabase.update(DatabaseOilJournal.TABLE_NAME, contentValues, DatabaseOilJournal.COL_ID + " = ? ", new String[]{String.valueOf(id)});
        mySetEmptyText();
        progressDialog.dismiss();
        finish();
    }

    private void onInsertSQLite() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOilJournal.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabaseOilJournal.COL_ODOMETER, douOdometer);
        contentValues.put(DatabaseOilJournal.COL_UNIT_PRICE, douUnitPrice);
        contentValues.put(DatabaseOilJournal.COL_FUEL_TYPE, strFuelType);
        contentValues.put(DatabaseOilJournal.COL_VOLUME, douVolume);
        contentValues.put(DatabaseOilJournal.COL_TOTAL_PRICE, douMoneytTotal);
        contentValues.put(DatabaseOilJournal.COL_PAYMENT_TYPE, strPaymentType);
        contentValues.put(DatabaseOilJournal.COL_LATITUDE, latitude);
        contentValues.put(DatabaseOilJournal.COL_LONGITUDE, longitude);
        contentValues.put(DatabaseOilJournal.COL_NOTE, strNote);

        sqLiteDatabase.insert(DatabaseOilJournal.TABLE_NAME, null, contentValues);
        mySetEmptyText();
        progressDialog.dismiss();

    }

    private void mySetEmptyText() {
        edtOdometer.setText("");
        edtUnitPrice.setText("");
        edtMoneyTotal.setText("");
        edtNote.setText("");
        tvShowVolume.setText("0 " + getResources().getString(R.string.lit));

        mySetSpinner();
        progressDialog.dismiss();
    }


    @Override
    public void onMyDialogPosititve() {
        Log.d("progress => ", "onMyDialogPosititve");
        Intent intent = new Intent(OilJournalActivity.this, LicensePlateActivity.class);
        startActivity(intent);

    }

    @Override
    public void onMyDialogNegative() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        strMoneyTotal = edtMoneyTotal.getText().toString().trim();
        strUnitPrice = edtUnitPrice.getText().toString().trim();

        if (!strMoneyTotal.equals("") && !strUnitPrice.equals("")) {

            douMoneytTotal = Double.valueOf(strMoneyTotal);
            douUnitPrice = Double.valueOf(strUnitPrice);

        }else if (!strMoneyTotal.equals("") && strUnitPrice.equals("")) {
            douUnitPrice = 0.0;
            douMoneytTotal = Double.valueOf(strMoneyTotal);

        }else if (strMoneyTotal.equals("") && !strUnitPrice.equals("")) {
            douMoneytTotal = 0.0;
            douUnitPrice = Double.valueOf(strUnitPrice);
        } else if (strMoneyTotal.equals("") && strUnitPrice.equals("")) {
            douMoneytTotal = 0.0;
            douUnitPrice = 0.0;
        }

        douVolume = douMoneytTotal / douUnitPrice;
        DecimalFormat df = new DecimalFormat("#.000");
        tempShowVoulume = df.format(douVolume);
        tvShowVolume.setText(tempShowVoulume + " " + getResources().getString(R.string.lit));

    }

    @Override
    public void onNewNextFunction() {
        myAddPermissionLocation.setStatusLocation(true);
    }

    @Override
    public void onPositiveMyDialog() {

    }

    @Override
    public void onNegativeMyDialog() {
        Toast.makeText(this, "No Open GPS ", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Open GPS", Toast.LENGTH_SHORT).show();
                onNewNextFunction();

            } else {
                onNegativeMyDialog();

            }
        }
    }
}
