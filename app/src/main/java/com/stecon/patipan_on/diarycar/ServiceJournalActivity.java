package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.common_class.MyAddPermissionLocation;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.common_class.MyLocationFirst;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceRecords;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceJournalActivity extends AppCompatActivity implements MyAddPermissionLocation.OnNextFunction, MyAddPermissionLocation.OnCustomClickDialog, MyLocationFirst.OnNextLocationFunction, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView tvLicensePlate;
    private Spinner spinnerService;
    private EditText edtServiceOdometer;
    private EditText edtServiceCost;
    private TextView tvServiceDate;
    private TextView tvServiceTime;

    private ImageButton imgBtnSelectDate;
    private ImageButton imgBtnSelectTime;

    private LinearLayout tvLinearLayout;
    private Switch switchGps;
    private EditText edtLocationService;
    private Button btnServiceCancel;
    private Button btnServiceSave;


    private String strLicensePlate;
    private String strSpinnerService;
    private String strOdometer;
    private String strServiceCost;
    private String strLocationName;
    private String strServiceDate;
    private String strServiceTime;
    private String strNote;
    private String strDateTime;
    private int serviceStatus;

    private Double douOdometer;
    private Double douServiceCost;

    private Double latitude;
    private Double longitude;
    private int data_id;
    private int intServicePosion;

    private int mode = 0; //0 คือ Insert , 2 คือ Update

    private MyDateTimeModify myDateTimeModify;
    private MyAddPermissionLocation myAddPermissionLocation;
    private MyLocationFirst myLocationFirst;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ArrayAdapter<String> stringArrayAdapter;

    private int counterCheck = 0; // use on switch check


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_journal);

        Bundle bundle = getIntent().getExtras();
        myDbHelper = new MyDbHelper(ServiceJournalActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        if (bundle != null) {
            data_id = bundle.getInt("data_id");
        }
        if (data_id != 0) {
            mode = 2;
        }

        bindWidGet();
        btnOnClick();

        switchGps.setOnCheckedChangeListener(this);

        final String serviceName[] = {"test", "Hello", "world"};
        stringArrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_tv2, serviceName);
        spinnerService.setAdapter(stringArrayAdapter);

        if (data_id != 0) {
            Log.d("id = > ", data_id + " ");
            mode = 2;
            mySetQueryText();
        } else {
            Log.d("id = > ", "No id");
            onDateTimeStartModify();
            spinnerService.setAdapter(stringArrayAdapter);
        }


    }

    private void mySetQueryText() {
        String strSql = "SELECT * FROM " + DatabaseServiceRecords.TABLE_NAME + " WHERE " + DatabaseServiceRecords.COL_ID + " = " + data_id;

        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);

        if (cursor != null) {
            cursor.moveToFirst();

            intServicePosion = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_ID));
            strLicensePlate = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LICENSE_PLATE));
            douOdometer = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_ODOMETER));
            douServiceCost = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_COST));
            latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LATITUDE));
            longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LONGITUDE));
            strNote = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_NOTE));
            strDateTime = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_TRANSACTION_DATE));
            serviceStatus = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_STATUS));
            strLocationName = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LOCATION_NAME));

            if (douOdometer != null) {
                strOdometer = String.valueOf(douOdometer);
            }
            if (douServiceCost != null) {
                strServiceCost = String.valueOf(douServiceCost);
            }

            spinnerService.setSelection(intServicePosion);
            edtServiceOdometer.setText(strOdometer);
            edtServiceCost.setText(strServiceCost);
            edtLocationService.setText(strLocationName);

            Log.d("date => ", strDateTime);
            myDateTimeModify = new MyDateTimeModify();
            myDateTimeModify.customDateTimeModifySqlite(strDateTime);

            strServiceDate = myDateTimeModify.getStrDate();
            strServiceTime = myDateTimeModify.getStrTime();
            tvServiceDate.setText(strServiceDate);
            tvServiceTime.setText(strServiceTime);

            if (latitude != 0 || longitude != 0) {
                counterCheck = 1;
                switchGps.setChecked(true);
            }else{
                counterCheck = 0;
            }
            spinnerService.setSelection(intServicePosion);
        }
    }

    private void onDateTimeStartModify() {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);

        myDateTimeModify = new MyDateTimeModify(timeStamp);
        strServiceDate = myDateTimeModify.getStrDate();
        strServiceTime = myDateTimeModify.getStrTime();
        tvServiceDate.setText(strServiceDate);
        tvServiceTime.setText(strServiceTime);
    }

    private void btnOnClick() {
        btnServiceSave.setOnClickListener(this);
        btnServiceCancel.setOnClickListener(this);
        imgBtnSelectDate.setOnClickListener(this);
        imgBtnSelectTime.setOnClickListener(this);
    }


    private void bindWidGet() {
        tvLicensePlate = (TextView) findViewById(R.id.tvLicensePlateService);
        spinnerService = (Spinner) findViewById(R.id.spinnerService);
        edtServiceOdometer = (EditText) findViewById(R.id.edtServiceOdometer);
        edtServiceCost = (EditText) findViewById(R.id.edtServiceCost);
        tvServiceDate = (TextView) findViewById(R.id.tvServiceDate);
        tvServiceTime = (TextView) findViewById(R.id.tvServiceTime);
        tvLinearLayout = (LinearLayout) findViewById(R.id.tvLinearServiceLocation);
        switchGps = (Switch) findViewById(R.id.switchServiceGps);
        edtLocationService = (EditText) findViewById(R.id.edtServiceLocation);
        btnServiceSave = (Button) findViewById(R.id.btnSaveService);
        btnServiceCancel = (Button) findViewById(R.id.btnCancelService);
        imgBtnSelectDate = (ImageButton) findViewById(R.id.imgBtnSelectDate);
        imgBtnSelectTime = (ImageButton) findViewById(R.id.imgBtnSelectServiceTime);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onNewNextFunction();
//                Toast.makeText(this, "onRequestPermissionsResult Open GPS ", Toast.LENGTH_SHORT).show();
            } else {
               // Toast.makeText(this, "onRequestPermissionsResult No Open GPS", Toast.LENGTH_SHORT).show();
                switchGps.setChecked(false);
            }
        }
    }

    private void findLocationFromGps() {
        Geocoder geocoder = new Geocoder(ServiceJournalActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                edtLocationService.setText(address + " " + city + " " + state + " " + country);

            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewNextFunction() {
        myLocationFirst = new MyLocationFirst(ServiceJournalActivity.this);
        myLocationFirst.setListennerNextLocationFunction(ServiceJournalActivity.this);
        myLocationFirst.onLocationStart();
    }

    @Override
    public void onPositiveMyDialog() {
        Toast.makeText(this, "UsE GPS POSITIVE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeMyDialog() {
        if (switchGps.isChecked()) {
            switchGps.setChecked(false);
            edtLocationService.setText("");
        }

    }

    @Override
    public void onStartNextFunction() {
        latitude = myLocationFirst.getLatitude();
        longitude = myLocationFirst.getLongitude();
        findLocationFromGps();
    }

    @Override
    public void onErrorNotFindGps() {
        onNegativeMyDialog();
        Toast.makeText(this, "You Should Allow Gps", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnSelectDate) {
            onSelectDate();
        } else if (v == imgBtnSelectTime) {
            onSelectTime();
        } else if (v == btnServiceSave) {
            onSave();
        } else if (v == btnServiceCancel) {
            onMyCancel();
        }
    }

    private void onMyCancel() {
        finish();
    }

    private void onSave() {
        serviceGetText();
        Boolean checkText = customCheckText();
        if (checkText) {
            douOdometer = Double.valueOf(strOdometer);
            douServiceCost = Double.valueOf(strServiceCost);
            if (mode == 0) {
                onInsert();

            } else if (mode == 2) {
                onUpdate();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean customCheckText() {
        if (strLicensePlate.equals("") || strOdometer.equals("") || strServiceCost.equals("") || strDateTime.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void onInsert() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseServiceRecords.COL_SERVICE_ID, intServicePosion);
        contentValues.put(DatabaseServiceRecords.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabaseServiceRecords.COL_ODOMETER, douOdometer);
        contentValues.put(DatabaseServiceRecords.COL_SERVICE_COST, douServiceCost);
        contentValues.put(DatabaseServiceRecords.COL_LATITUDE, latitude);
        contentValues.put(DatabaseServiceRecords.COL_LONGITUDE, longitude);
        contentValues.put(DatabaseServiceRecords.COL_LOCATION_NAME, strLocationName);
        contentValues.put(DatabaseServiceRecords.COL_TRANSACTION_DATE, strDateTime);

        sqLiteDatabase.insert(DatabaseServiceRecords.TABLE_NAME, null, contentValues);
        finish();

    }

    private void serviceGetText() {

        strOdometer = edtServiceOdometer.getText().toString().trim();
        strServiceCost = edtServiceCost.getText().toString().trim();
        strServiceDate = tvServiceDate.getText().toString().trim();
        strServiceTime = tvServiceTime.getText().toString().trim();
        strLocationName = edtLocationService.getText().toString().trim();
        strSpinnerService = spinnerService.getSelectedItem().toString();
        intServicePosion = spinnerService.getSelectedItemPosition();
        strDateTime = myDateTimeModify.getDateTimeToserver();

    }

    private void onUpdate() {
        //Toast.makeText(this, "ยังไม่่พร้อมใช้งาน", Toast.LENGTH_SHORT).show();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseServiceRecords.COL_SERVICE_ID, intServicePosion);
        contentValues.put(DatabaseServiceRecords.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabaseServiceRecords.COL_ODOMETER, douOdometer);
        contentValues.put(DatabaseServiceRecords.COL_SERVICE_COST, douServiceCost);
        contentValues.put(DatabaseServiceRecords.COL_LATITUDE, latitude);
        contentValues.put(DatabaseServiceRecords.COL_LONGITUDE, longitude);
        contentValues.put(DatabaseServiceRecords.COL_LOCATION_NAME, strLocationName);
        contentValues.put(DatabaseServiceRecords.COL_TRANSACTION_DATE, strDateTime);

        contentValues.put(DatabaseServiceRecords.COL_DATE_UPDATE, dateFormat.format(date));
        sqLiteDatabase.update(DatabaseServiceRecords.TABLE_NAME, contentValues, DatabaseServiceRecords.COL_ID + " = ? ", new String[]{String.valueOf(data_id)});

        Toast.makeText(this, getResources().getString(R.string.message_save_success), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void onSelectTime() {
        timePickerDialog = new TimePickerDialog(ServiceJournalActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myDateTimeModify.setHour(hourOfDay);
                myDateTimeModify.setMinute(minute);

                strServiceTime = myDateTimeModify.getStrTime();
                tvServiceTime.setText(strServiceTime + getResources().getString(R.string.short_minute));

            }
        },myDateTimeModify.getHour(),myDateTimeModify.getMinute(),false);
        timePickerDialog.show();
    }

    private void onSelectDate() {
        datePickerDialog = new DatePickerDialog(ServiceJournalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myDateTimeModify.setYear(year);
                myDateTimeModify.setMonth(month + 1);
                myDateTimeModify.setDay(dayOfMonth);


                strServiceDate = myDateTimeModify.getStrDate();
                tvServiceDate.setText(strServiceDate);
            }
        }, myDateTimeModify.getYear(), myDateTimeModify.getMonth()-1, myDateTimeModify.getDay());
        datePickerDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == switchGps) {
            if (isChecked) {
                if (counterCheck > 0) {
                    counterCheck--;
                }else{
                    myAddPermissionLocation = new MyAddPermissionLocation(ServiceJournalActivity.this);
                    myAddPermissionLocation.setOnNextFunction(ServiceJournalActivity.this);
                    myAddPermissionLocation.setOnCustomClickDialog(ServiceJournalActivity.this);
                    myAddPermissionLocation.checkLocation();
                }
            } else {
                latitude = 0.0;
                longitude = 0.0;
                edtLocationService.setText("");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myOnCustomCheckLicensePlate();
        tvLicensePlate.setText(strLicensePlate);
    }

    public void myOnCustomCheckLicensePlate() {
        if (mode == 0) {
            sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
            strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
            if (strLicensePlate.equals("")) {
                CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
                customAlertDialog.setTitle(getResources().getString(R.string.message_no_license_plate));
                customAlertDialog.setMessage(getResources().getString(R.string.message_should_input_license_plate));
                customAlertDialog.myDefaultDialog();
                customAlertDialog.show();
                customAlertDialog.setOnMyDialogActivity(new CustomAlertDialog.OnMyDialogActivity() {
                    @Override
                    public void onMyDialogPositive() {
                        Intent intent = new Intent(ServiceJournalActivity.this, LicensePlateActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onMyDialogNegative() {
                        finish();
                    }
                });
            }
        }
    }
}
