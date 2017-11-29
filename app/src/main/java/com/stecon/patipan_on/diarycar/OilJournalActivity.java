package com.stecon.patipan_on.diarycar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

public class OilJournalActivity extends AppCompatActivity implements View.OnClickListener, OnLocationUpdatedListener, CustomAlertDialog.OnMyDialogActivity {

    //view
    private EditText edtOdometer;
    private EditText edtUnitPrice;
    private EditText edtMoneyTotal;
    private EditText edtNote;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_journal);

        myDbHelper = new MyDbHelper(OilJournalActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();


        bindWidget();
        mySetSpinner();

        btnOilSave.setOnClickListener(this);
        btnOilSave.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp = getSharedPreferences(LicensePlateActivity.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sp.getString(LicensePlateActivity.licenPlate, "");
        if (strLicensePlate.equals("")) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
            customAlertDialog.myDialog();
            customAlertDialog.show();
            customAlertDialog.setOnMyDialogActivity(OilJournalActivity.this);

        }
    }


    private void mySetSpinner() {
        ArrayAdapter<CharSequence> myList = ArrayAdapter.createFromResource(this, R.array.oil_array, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> moneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.support_simple_spinner_dropdown_item);
        spinnerOil.setAdapter(myList);
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
    }


    @Override
    public void onClick(View v) {
        if (v == btnOilSave) {
            progressDialog = new ProgressDialog(OilJournalActivity.this);
            progressDialog.setMessage("Loadding..........");
            progressDialog.setTitle("Save Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
            Boolean check = myCheckData();
            if (!check) {
                progressDialog.dismiss();
                Toast.makeText(this, "กรุณากรอกข้อมูลที่จำเป็นให้ครับ", Toast.LENGTH_SHORT).show();
            } else {
                //MainSave
                processCalculate();
                myLocation();
            }
        }
    }

    private void processCalculate() {
        douOdometer = Double.valueOf(strOdometer);
        douUnitPrice = Double.valueOf(strUnitPrice);
        douMoneytTotal = Double.valueOf(strMoneyTotal);
        douVolume = douMoneytTotal / douUnitPrice;
        //Toast.makeText(this, "Volume : " + douVolume, Toast.LENGTH_SHORT).show();
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
        strOdometer = edtOdometer.getText().toString().trim();
        strUnitPrice = edtUnitPrice.getText().toString().trim();
        strMoneyTotal = edtMoneyTotal.getText().toString().trim();
        strFuelType = spinnerOil.getSelectedItem().toString().trim();
        strPaymentType = spinnerPayMentType.getSelectedItem().toString().trim();
        strNote = edtNote.getText().toString().trim();

        if (chkOilFull.isChecked()) {
            partialFillUp = 1;
        } else {
            partialFillUp = 0;
        }
        strNote = edtNote.getText().toString().trim();

        if (strOdometer.equals("") || strUnitPrice.equals("") || strMoneyTotal.equals("") || strFuelType.equals("") || strPaymentType.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SmartLocation
                .with(this)
                .location()
                .stop();

    }

    @Override
    public void onLocationUpdated(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        onPushSQLite();
    }

    private void onPushSQLite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOilJournal.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabaseOilJournal.COL_ODOMETER, douOdometer);
        contentValues.put(DatabaseOilJournal.COL_UNIT_PRICE, douUnitPrice);
        contentValues.put(DatabaseOilJournal.COL_VOLUME, douVolume);
        contentValues.put(DatabaseOilJournal.COL_TOTAL_PRICE, douMoneytTotal);
        contentValues.put(DatabaseOilJournal.COL_PARTIAL_FILL_UP, partialFillUp);
        contentValues.put(DatabaseOilJournal.COL_PAYMENT_TYPE, strPaymentType);
        contentValues.put(DatabaseOilJournal.COL_LATITUDE, latitude);
        contentValues.put(DatabaseOilJournal.COL_LONGITUDE, longitude);
        contentValues.put(DatabaseOilJournal.COL_NOTE, strNote);

        sqLiteDatabase.insert(DatabaseOilJournal.TABLE_NAME, null, contentValues);
        progressDialog.dismiss();
        mySetEmptyText();
        Toast.makeText(this, "บันทึกข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
    }

    private void mySetEmptyText() {
        edtOdometer.setText("");
        edtUnitPrice.setText("");
        edtMoneyTotal.setText("");
        edtNote.setText("");
        if (chkOilFull.isChecked()) {
            chkOilFull.setChecked(false);

        }
        mySetSpinner();
    }


    @Override
    public void onMyStartActivity() {
        Log.d("progress => ", "onMyStartActivity");
        Intent intent = new Intent(OilJournalActivity.this, LicensePlateActivity.class);
        startActivity(intent);

    }

}
