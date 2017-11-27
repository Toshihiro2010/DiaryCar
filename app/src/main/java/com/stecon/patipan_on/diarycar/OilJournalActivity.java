package com.stecon.patipan_on.diarycar;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

public class OilJournalActivity extends AppCompatActivity implements View.OnClickListener, OnLocationUpdatedListener {

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

    MyListener disListener = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_journal);

        bindWidget();
        mySetSpinner();

        btnOilSave.setOnClickListener(this);
        btnOilSave.setOnClickListener(this);

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
            Boolean check = myCheckData();
            if (!check) {
                Toast.makeText(this, "กรุณากรอกข้อมูลที่จำเป็นให้ครับ", Toast.LENGTH_SHORT).show();
            } else {
                //MainSave

                myLocation();
                //Toast.makeText(this, "สำเร็จ =>" + latitude + "/" + longitude, Toast.LENGTH_SHORT).show();
                //String temp = spinnerOil.getSelectedItem().toString() + " / " + spinnerPayMentType.getSelectedItem().toString();
//                Toast.makeText(this, "สำเร็จ =>" + latitude + "/" + longitude, Toast.LENGTH_SHORT).show();
            }
        }
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

//            Toast.makeText(this, "สำเร็จ =>" + latitude + "/" + longitude, Toast.LENGTH_SHORT).show();

        } else {
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
        Log.d("Process => ", "onLocationUpdate");
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    public void setMyListener(MyListener listener) {
        disListener = listener;
    }

    public interface MyListener{
        //public boolean myCallback();
        void setTest(MyListener test);


    }



}
