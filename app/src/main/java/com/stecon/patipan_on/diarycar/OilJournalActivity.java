package com.stecon.patipan_on.diarycar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.stecon.patipan_on.diarycar.controller.CustomSpinnerAdapter;
import com.stecon.patipan_on.diarycar.common_class.MyAddPermissionLocation;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.common_class.MyLocationFirst;
import com.stecon.patipan_on.diarycar.controller.StatusCheckServer;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.query_model.FuelDataQuerySQLIte;
import com.stecon.patipan_on.diarycar.database.query_model.FuelTypeQuerySQLite;
import com.stecon.patipan_on.diarycar.model.FuelTypeModel;
import com.stecon.patipan_on.diarycar.common_class.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class OilJournalActivity extends AppCompatActivity implements View.OnClickListener, CustomAlertDialog.OnMyDialogActivity, TextWatcher, MyAddPermissionLocation.OnNextFunction {

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
    private int fuelTypePosition;

    // get for System
    private String strLicensePlate;
    private Double latitude;
    private Double longitude;

    //trance to double
    private Double douOdometer;
    private Double douUnitPrice;
    private Double douMoneyTotal;

    //calculate
    private Double douVolume;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    private String tempShowVoulume;

    private int id;
    private int mode = 0;

    //status to server 0 = no , 1 = yes , 2 = edit
    private int statusToServer = 0;

    String[] oilArray;

    private MyAddPermissionLocation myAddPermissionLocation;
    private MyLocationFirst myLocationFirst;

    private FuelTypeQuerySQLite fuelTypeQuerySQLite;
    private ArrayList<FuelTypeModel> fuelTypeModelArrayList;



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
        myAddPermissionLocation.setOnCustomClickDialog(new MyAddPermissionLocation.OnCustomClickDialog() {
            @Override
            public void onPositiveMyDialog() {

            }

            @Override
            public void onNegativeMyDialog() {
                Toast.makeText(OilJournalActivity.this, "No Open GPS ", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        myAddPermissionLocation.checkLocation();


        myDbHelper = new MyDbHelper(OilJournalActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        onSetSpinnerDataFromDB();
        mySetSpinner();
        if (id != 0) {
            mySetQueryText();
            mode = 2;
        }
        onOilButtonClick();


    }

    private void onSetSpinnerDataFromDB() {
        FuelTypeQuerySQLite fuelTypeQuerySQLite = new FuelTypeQuerySQLite(this);
        fuelTypeModelArrayList = new ArrayList<>(fuelTypeQuerySQLite.getFuelType());

    }

    private void onOilButtonClick() {
        btnOilSave.setOnClickListener(this);
        btnOilCancel.setOnClickListener(this);
        edtUnitPrice.addTextChangedListener(this);
        edtMoneyTotal.addTextChangedListener(this);
    }

    private void mySetQueryText() {
        FuelDataQuerySQLIte fuelDataQuerySQLIte = new FuelDataQuerySQLIte(OilJournalActivity.this);
        OilDataModel oilDataModel = fuelDataQuerySQLIte.getFuelDataById(id);
        douOdometer = oilDataModel.getOdometer();
        douUnitPrice = oilDataModel.getUnit_price();
        douMoneyTotal = oilDataModel.getTotal_price();
        douVolume = oilDataModel.getVolume();
        strNote = oilDataModel.getNote();
        strFuelType = oilDataModel.getFuel_type_name();
        fuelTypePosition = oilDataModel.getFuel_type();
        strPaymentType = oilDataModel.getPayment_type();
        statusToServer = oilDataModel.getStatus();

        edtOdometer.setText(douOdometer + "");
        edtUnitPrice.setText(douUnitPrice + "");
        edtMoneyTotal.setText(douMoneyTotal + "");
        tvShowVolume.setText(douVolume + "");
        edtNote.setText(strNote);

        int tempPosition = -1;
        for(int i = 0 ; i < fuelTypeModelArrayList.size(); i++) {
            if (fuelTypePosition == fuelTypeModelArrayList.get(i).getType_id()) {
                tempPosition = i;
                break;
            }
        }

        if (tempPosition != -1) {
            spinnerOil.setSelection(tempPosition);
        }

        //ArrayAdapter<CharSequence> moneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.support_simple_spinner_dropdown_item);
        String moneyPayMentArray[] = getResources().getStringArray(R.array.money_pay_array);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, moneyPayMentArray);
        if (!strPaymentType.equals(null)) {
            int spinnerPosition = stringArrayAdapter.getPosition(strPaymentType);
            spinnerPayMentType.setSelection(spinnerPosition);
        } else {
            spinnerPayMentType.setAdapter(stringArrayAdapter);
        }
    }

    private void testSpinner() {

        String[] oilArray = getResources().getStringArray(R.array.oil_array);
        ArrayList<String> oilArrayList = new ArrayList<>(Arrays.asList(oilArray));

//        Log.d("Oil Id => ",spinnerOil.getSelectedItemPosition() + "");
//        Log.d("spinnerOil => ", oilArrayList.get(spinnerOil.getSelectedItemPosition()) );
//        Log.d("spinnerPayType => ", spinnerPayMentType.getSelectedItem().toString() );
        Log.d("SelectedItemPosition =>",spinnerOil.getSelectedItemPosition() + "");

    }

    @Override
    protected void onStart() {
        super.onStart();

        myCheckLicensePlte();
    }

    private void myCheckLicensePlte() {
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
        //ArrayAdapter<CharSequence> oilList = ArrayAdapter.createFromResource(this, R.array.oil_array, R.layout.custom_spinner_tv2);
        //ArrayAdapter<CharSequence> moneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> moneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.custom_spinner_tv2);
        spinnerPayMentType.setAdapter(moneyPayType);


        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(OilJournalActivity.this, fuelTypeModelArrayList);
        spinnerOil.setAdapter(customSpinnerAdapter);

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
            myOnSaveData();
        } else if (v == btnOilCancel) {
            mySetEmptyText();
            finish();
        }
    }

    private void myOnSaveData() {
        Boolean check = myCheckData();
        if (!check) {
            //Cancel Save
            Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
        } else {
            //MainSave
            processCalculate(); // calculate Volume fuel
            if (mode == 0) {
                myMainInsert();
            } else if (mode == 2) {
                onEditSQLite();
            }
        }
    }

    private void processCalculate() {
        douUnitPrice = Double.valueOf(strUnitPrice);
        douMoneyTotal = Double.valueOf(strMoneyTotal);
        douVolume = douMoneyTotal / douUnitPrice;
    }

    private void myMainInsert() {
        myLocationFirst = new MyLocationFirst(this);
        myLocationFirst.onLocationStart();
        myLocationFirst.setListennerNextLocationFunction(new MyLocationFirst.OnNextLocationFunction() {
            @Override
            public void onStartNextFunction() {
                latitude = myLocationFirst.getLatitude();
                longitude = myLocationFirst.getLongitude();
                onInsertSQLite();
            }

            @Override
            public void onErrorNotFindGps() {
                mySetEmptyText();
                Toast.makeText(OilJournalActivity.this, "You Should Allow Gps", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Boolean myCheckData() {
        myGetData();
        if (strOdometer.equals("") || strUnitPrice.equals("") || strMoneyTotal.equals("") || strPaymentType.equals("")){
            return false;
        } else {
            douOdometer = Double.valueOf(strOdometer);
            return true;
        }
    }

    private void myGetData() {
        strOdometer = edtOdometer.getText().toString().trim();
        strPaymentType = spinnerPayMentType.getSelectedItem().toString().trim();
        strNote = edtNote.getText().toString().trim();
        strMoneyTotal = edtMoneyTotal.getText().toString().trim();
        strUnitPrice = edtUnitPrice.getText().toString().trim();

        int tempPositionSpinnerFuel = spinnerOil.getSelectedItemPosition();
        fuelTypePosition = fuelTypeModelArrayList.get(tempPositionSpinnerFuel).getType_id();

    }

    private void onEditSQLite() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOilJournal.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabaseOilJournal.COL_ODOMETER, douOdometer);
        contentValues.put(DatabaseOilJournal.COL_UNIT_PRICE, douUnitPrice);
        contentValues.put(DatabaseOilJournal.COL_FUEL_TYPE, fuelTypePosition);
        contentValues.put(DatabaseOilJournal.COL_VOLUME, douVolume);
        contentValues.put(DatabaseOilJournal.COL_TOTAL_PRICE, douMoneyTotal);
        contentValues.put(DatabaseOilJournal.COL_PAYMENT_TYPE, strPaymentType);
        contentValues.put(DatabaseOilJournal.COL_NOTE, strNote);
        contentValues.put(DatabaseOilJournal.COL_DATE_UPDATE, dateFormat.format(date));

        if (statusToServer == 1) {
            statusToServer = 2;
            contentValues.put(DatabaseOilJournal.COL_STATUS, statusToServer);
        }

        sqLiteDatabase.update(DatabaseOilJournal.TABLE_NAME, contentValues, DatabaseOilJournal.COL_ID + " = ? ", new String[]{String.valueOf(id)});
        mySetEmptyText();
        finish();
    }

    private void onInsertSQLite() {

        StatusCheckServer statusCheckServer = new StatusCheckServer(OilJournalActivity.this);
        statusCheckServer.setOnMyListener(new StatusCheckServer.MyOnListener() {
            @Override
            public void onInsertListener() {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseOilJournal.COL_LICENSE_PLATE, strLicensePlate);
                contentValues.put(DatabaseOilJournal.COL_ODOMETER, douOdometer);
                contentValues.put(DatabaseOilJournal.COL_UNIT_PRICE, douUnitPrice);
                contentValues.put(DatabaseOilJournal.COL_FUEL_TYPE, fuelTypePosition);
                contentValues.put(DatabaseOilJournal.COL_VOLUME, douVolume);
                contentValues.put(DatabaseOilJournal.COL_TOTAL_PRICE, douMoneyTotal);
                contentValues.put(DatabaseOilJournal.COL_PAYMENT_TYPE, strPaymentType);
                contentValues.put(DatabaseOilJournal.COL_LATITUDE, latitude);
                contentValues.put(DatabaseOilJournal.COL_LONGITUDE, longitude);
                contentValues.put(DatabaseOilJournal.COL_NOTE, strNote);

                sqLiteDatabase.insert(DatabaseOilJournal.TABLE_NAME, null, contentValues);
                mySetEmptyText();
                finish();
            }

            @Override
            public void onUpdateListener() {

            }
        });
        statusCheckServer.checkInsert();

    }

    private void mySetEmptyText() {
        edtOdometer.setText("");
        edtUnitPrice.setText("");
        edtMoneyTotal.setText("");
        edtNote.setText("");
        tvShowVolume.setText("0 " + getResources().getString(R.string.lit));

        mySetSpinner();
    }

    @Override
    public void onMyDialogPositive() {
        Log.d("progress => ", "onMyDialogPositive");
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

            douMoneyTotal = Double.valueOf(strMoneyTotal);
            douUnitPrice = Double.valueOf(strUnitPrice);

        }else if (!strMoneyTotal.equals("") && strUnitPrice.equals("")) {
            douUnitPrice = 0.0;
            douMoneyTotal = Double.valueOf(strMoneyTotal);

        }else if (strMoneyTotal.equals("") && !strUnitPrice.equals("")) {
            douMoneyTotal = 0.0;
            douUnitPrice = Double.valueOf(strUnitPrice);
        } else if (strMoneyTotal.equals("") && strUnitPrice.equals("")) {
            douMoneyTotal = 0.0;
            douUnitPrice = 0.0;
        }

        douVolume = douMoneyTotal / douUnitPrice;
        DecimalFormat df = new DecimalFormat("#.000");
        tempShowVoulume = df.format(douVolume);
        tvShowVolume.setText(tempShowVoulume + " " + getResources().getString(R.string.lit));

    }

    @Override
    public void onNewNextFunction() {
        myAddPermissionLocation.setStatusLocation(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Open GPS", Toast.LENGTH_SHORT).show();
                onNewNextFunction();
            } else {
                Toast.makeText(this, "No Open GPS ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
