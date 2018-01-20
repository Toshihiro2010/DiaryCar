package com.stecon.patipan_on.diarycar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
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

public class OilJournalActivity extends AppCompatActivity implements View.OnClickListener, OnLocationUpdatedListener, CustomAlertDialog.OnMyDialogActivity, TextWatcher {

    //view
    private EditText edtOdometer;
    private EditText edtUnitPrice;
    private EditText edtMoneyTotal;
    private EditText edtNote;

    private TextView tvShowVolume;

    private Button btnOilSave;

    private Spinner spinnerOil;
    private Spinner spinnerPayMentType;

    private CheckBox chkOilFull;

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

        myDbHelper = new MyDbHelper(OilJournalActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        mySetSpinner();
        if (id != 0) {
            mode = 2;
            mySetQueryText();
        } else {

        }
        btnOilSave.setOnClickListener(this);
        btnOilSave.setOnClickListener(this);

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
            partialFillUp = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_PARTIAL_FILL_UP));



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
                int spinnerPostion = moneyPayType.getPosition(strPaymentType);
                spinnerPayMentType.setSelection(spinnerPostion);
            } else {
                spinnerPayMentType.setAdapter(moneyPayType);
            }
            if (partialFillUp == 1) {
                chkOilFull.setChecked(true);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sp.getString(MyAppConfig.licenPlate, "");
        if (strLicensePlate.equals("")) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
            customAlertDialog.setTitle("No LicensePlate");
            customAlertDialog.setMessage("You should in put LicensePlate............");
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
        chkOilFull = (CheckBox) findViewById(R.id.chkOilFull);
        spinnerPayMentType = (Spinner) findViewById(R.id.spinnerMoneyPay);
        edtNote = (EditText) findViewById(R.id.edtNote);
        tvShowVolume = (TextView) findViewById(R.id.txtVolumeShow);
    }


    @Override
    public void onClick(View v) {

        if (v == btnOilSave) {
            progressDialog = new ProgressDialog(OilJournalActivity.this);
            progressDialog.setMessage("Loadding..........");
            progressDialog.setTitle("Save Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            Boolean check = myCheckData();
            Log.d("check => ", check.toString());
            if (!check) {
                progressDialog.dismiss();
                Toast.makeText(this, "กรุณากรอกข้อมูลที่จำเป็นให้ครับ", Toast.LENGTH_SHORT).show();
            } else {
                //MainSave
                processCalculate();
                if (mode == 0) {
                    myLocation();
                } else if (mode == 2) {
                    onEditSQLite();
                }
            }
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
        Toast.makeText(this, "Eroor", Toast.LENGTH_SHORT).show();
    }

    private Boolean myCheckData() {
        myGetData();

        if (chkOilFull.isChecked()) {
            partialFillUp = 1;
        } else {
            partialFillUp = 0;
        }
        if (strOdometer.equals("") || strUnitPrice.equals("") || strMoneyTotal.equals("") || strFuelType.equals("") || strPaymentType.equals("")) {

            Log.d("Return => ", "false");
            return false;
        } else {
            douOdometer = Double.valueOf(strOdometer);
            Log.d("Return => ", "true");
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
        contentValues.put(DatabaseOilJournal.COL_PARTIAL_FILL_UP, partialFillUp);
        contentValues.put(DatabaseOilJournal.COL_PAYMENT_TYPE, strPaymentType);
        contentValues.put(DatabaseOilJournal.COL_NOTE, strNote);
        contentValues.put(DatabaseOilJournal.COL_DATE_UPDATE, dateFormat.format(date));

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
        contentValues.put(DatabaseOilJournal.COL_PARTIAL_FILL_UP, partialFillUp);
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
        if (chkOilFull.isChecked()) {
            chkOilFull.setChecked(false);

        }
        tvShowVolume.setText("0 ลิตร");

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
        tvShowVolume.setText(tempShowVoulume + " ลิตร");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("OnActivity => ", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("OnActivity => ", "onStop");
    }
}
