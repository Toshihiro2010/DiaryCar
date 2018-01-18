package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyAddPermissionLocation;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.MyLocationFirst;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceRecords;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceRecordsActivity extends AppCompatActivity implements MyAddPermissionLocation.OnNextFunction, MyAddPermissionLocation.OnCustomClickDialog, MyLocationFirst.OnNextLocationFunction, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView tvLicensePlate;
    private Spinner spinnerService;
    private EditText edtServiceOdometer;
    private EditText edtServiceFuel;
    private EditText edtServiceCost;
    private TextView tvServiceDate;
    private TextView tvServiceTime;

    private Button btnSelectDate;
    private Button btnSelectTime;
    private Button btnServiceSave;
    private LinearLayout tvLinearLayout;
    private Switch switchGps;
    private EditText edtLocationService;

    private String strLicensePlate;
    private String strSpinnerService;
    private String strOdometer;
    private String strFuel;
    private String strServiceCost;
    private String strLocationName;
    private String strServiceDate;
    private String strServiceTime;
    private String strNote;
    private String strDateTime;

    private Double douOdometer;
    private Double douFuelLevel;
    private Double douServiceCost;

    private Double latitude;
    private Double longitude;
    private int serviceId;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_records);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            serviceId = bundle.getInt("serviceId");
        }

        if (serviceId != 0) {
            mode = 2;
        }


        bindWidGet();
        btnOnClick();
        onDateTimeModify();

        switchGps.setOnCheckedChangeListener(this);

        final String serviceName[] = {"test", "Hello", "world"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, serviceName);
        spinnerService.setAdapter(stringArrayAdapter);
//        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("position => ", test[position] + " ");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void onDateTimeModify() {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);

        Log.d("timeStamp => ", timeStamp);
        myDateTimeModify = new MyDateTimeModify(timeStamp);
        strServiceDate = myDateTimeModify.getStrDate();
        strServiceTime = myDateTimeModify.getStrTime();
        tvServiceDate.setText(strServiceDate);
        tvServiceTime.setText(strServiceTime);
    }

    private void btnOnClick() {
        btnServiceSave.setOnClickListener(this);
        btnSelectTime.setOnClickListener(this);
        btnSelectDate.setOnClickListener(this);
    }


    private void bindWidGet() {
        tvLicensePlate = (TextView) findViewById(R.id.tvLicensePlateService);
        spinnerService = (Spinner) findViewById(R.id.spinnerService);
        edtServiceOdometer = (EditText) findViewById(R.id.edtServiceOdometer);
        edtServiceFuel = (EditText) findViewById(R.id.edtFuelLevel);
        edtServiceCost = (EditText) findViewById(R.id.edtServiceCost);
        tvServiceDate = (TextView) findViewById(R.id.tvServiceDate);
        tvServiceTime = (TextView) findViewById(R.id.tvServiceTime);
        btnSelectDate = (Button) findViewById(R.id.btnSelectServiceDate);
        btnSelectTime = (Button) findViewById(R.id.btnSelectServiceTime);
        tvLinearLayout = (LinearLayout) findViewById(R.id.tvLinearServiceLocation);
        switchGps = (Switch) findViewById(R.id.switchServiceGps);
        edtLocationService = (EditText) findViewById(R.id.edtServiceLocation);
        btnServiceSave = (Button) findViewById(R.id.btnServiceSave);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "onRequestPermissionsResult Open GPS ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "onRequestPermissionsResult No Open GPS", Toast.LENGTH_SHORT).show();
                switchGps.setChecked(false);
            }
        }
    }

    @Override
    public void onNewNextFunction() {
        myLocationFirst = new MyLocationFirst(ServiceRecordsActivity.this);
        myLocationFirst.registerOnextLocationFunction(ServiceRecordsActivity.this);
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
            tvLinearLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Open GPS ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStartNextFunction() {
        latitude = myLocationFirst.getLatitude();
        longitude = myLocationFirst.getLongitude();
        Log.d("latitude/longtitude => ", latitude + " / " + longitude);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectDate) {
            onSelectDate();
        } else if (v == btnSelectTime) {
            onSelectTime();
        } else if (v == btnServiceSave) {
            onSave();
        }
    }

    private void onSave() {
        serviceGetText();
//        Log.d("text => ", spinnerService.getSelectedItemPosition() + " ");
        Boolean checkText = customCheckText();
        if (checkText) {
            douOdometer = Double.valueOf(strOdometer);
            douFuelLevel = Double.valueOf(strFuel);
            douServiceCost = Double.valueOf(strServiceCost);
            if (mode == 0) {
                onInsert();

            } else if (mode == 2) {
                onUpdate();
            }
        } else {
            Toast.makeText(this, "Input ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean customCheckText() {
        if (strLicensePlate.equals("") || strOdometer.equals("") || strFuel.equals("") || strServiceCost.equals("") || strDateTime.equals("")) {
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
        contentValues.put(DatabaseServiceRecords.COL_FUEL_LEVEL, douFuelLevel);
        contentValues.put(DatabaseServiceRecords.COL_SERVICE_COST, douServiceCost);
        contentValues.put(DatabaseServiceRecords.COL_LATITUDE, latitude);
        contentValues.put(DatabaseServiceRecords.COL_LONGITUDE, longitude);
        contentValues.put(DatabaseServiceRecords.COL_TRANSACTION_DATE, strDateTime);

        myDbHelper = new MyDbHelper(ServiceRecordsActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        sqLiteDatabase.insert(DatabaseServiceRecords.TABLE_NAME, null, contentValues);
        finish();

    }

    private void serviceGetText() {
        strOdometer = edtServiceOdometer.getText().toString().trim();
        strFuel = edtServiceFuel.getText().toString().trim();
        strServiceCost = edtServiceCost.getText().toString().trim();
        strServiceDate = tvServiceDate.getText().toString().trim();
        strServiceTime = tvServiceTime.getText().toString().trim();
        strLocationName = edtLocationService.getText().toString().trim();
        strSpinnerService = spinnerService.getSelectedItem().toString();
        intServicePosion = spinnerService.getSelectedItemPosition();

        strDateTime = myDateTimeModify.getDateTimeToserver();
    }

    private void onUpdate() {

    }

    private void onSelectTime() {
        timePickerDialog = new TimePickerDialog(ServiceRecordsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myDateTimeModify.setHour(hourOfDay);
                myDateTimeModify.setMinute(minute);

                strServiceTime = myDateTimeModify.getStrTime();
                tvServiceTime.setText(strServiceTime + " น.");

            }
        },myDateTimeModify.getHour(),myDateTimeModify.getMinute(),false);
        timePickerDialog.show();
    }

    private void onSelectDate() {
        datePickerDialog = new DatePickerDialog(ServiceRecordsActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if (switchGps == buttonView) {
            if (isChecked) {
                tvLinearLayout.setVisibility(View.INVISIBLE);
                myAddPermissionLocation = new MyAddPermissionLocation(ServiceRecordsActivity.this);
                myAddPermissionLocation.setOnNextFunction(ServiceRecordsActivity.this);
                myAddPermissionLocation.setOnCustomClickDialog(ServiceRecordsActivity.this);
                myAddPermissionLocation.checkLocation();
            } else {
                edtLocationService.setText("");
                tvLinearLayout.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences(LicensePlateActivity.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sp.getString(LicensePlateActivity.licenPlate, "");
        tvLicensePlate.setText(strLicensePlate);
        Log.d("licensePlate => ", strLicensePlate + " / 555");
        if (strLicensePlate.equals("")) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
            customAlertDialog.setTitle("No LicensePlate");
            customAlertDialog.setMessage("You should in put LicensePlate............");
            customAlertDialog.myDefaultDialog();
            customAlertDialog.show();
            customAlertDialog.setOnMyDialogActivity(new CustomAlertDialog.OnMyDialogActivity() {
                @Override
                public void onMyDialogPosititve() {
                    Log.d("progress => ", "onMyDialogPosititve");
                    Intent intent = new Intent(ServiceRecordsActivity.this, LicensePlateActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onMyDialogNegative() {
                    onStart();
                }
            });
        }
    }
}
