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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyAddPermissionLocation;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseTripDetail;
import com.stecon.patipan_on.diarycar.model.MyDateModify;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TripStartActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText edtReservationNumber;
    private EditText edtPurpose;
    private EditText edtDepartureOdometer;
    private EditText edtFuelLevel;

    private Button btnSelectDepartureDate;
    private Button btnSelectDepartureTime;
    private Button btnGoToMain;

    private TextView tvDepartureDate;
    private TextView tvDepartureTime;

    private Switch switchLocation;

    private String strReservationNumber;
    private String strPurpose;
    private String strDepartureOdometer;
    private String strFuelLevel;
    private String strDepartureDateTime;

    private Double odometerDouble;
    private Double fuellLevelDouble;

    private String str_licenseplate;

    private int tvHour , tvMinute;
    private int tvYear , tvMonth , tvDay;

    private MyDateModify myDateModify;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static String trip_id = "trip_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_start);
        sharedPreferences = getSharedPreferences(LicensePlateActivity.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        str_licenseplate = sharedPreferences.getString(LicensePlateActivity.licenPlate, "");
        if (str_licenseplate.equals("")) {
            Toast.makeText(this, "No License Plate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TripStartActivity.this, LicensePlateActivity.class);
            startActivity(intent);
        }

        bindWidget();

        Date date = new Date();
        Log.d("date => ", date.toString());
        tvHour = date.getHours();
        tvMinute = date.getMinutes();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        myDateModify = new MyDateModify(timeStamp);
        tvYear = myDateModify.getYear();
        tvMonth = myDateModify.getMonth();
        tvDay = myDateModify.getDay();

        myStartSetText();
        myBtnOnClick();

        switchLocation.setOnCheckedChangeListener(this);
    }

    private void myBtnOnClick() {
        btnSelectDepartureTime.setOnClickListener(this);
        btnSelectDepartureDate.setOnClickListener(this);
        btnGoToMain.setOnClickListener(this);
    }

    private void myStartSetText() {
        tvDepartureDate.setText(tvDay + "/" + tvMonth + "/" + tvYear);
        tvDepartureTime.setText(tvHour + "." + tvMinute + " น.");

    }

    private void bindWidget() {
        edtReservationNumber = (EditText) findViewById(R.id.edtReservationNumber);
        edtPurpose = (EditText) findViewById(R.id.edtPurpose);
        edtDepartureOdometer = (EditText) findViewById(R.id.edtDapartureOdometer);
        edtFuelLevel = (EditText) findViewById(R.id.edtFuelLevel);
        btnSelectDepartureDate = (Button) findViewById(R.id.btnDepartureSelectDate);
        btnSelectDepartureTime = (Button) findViewById(R.id.btnDepartureSelectTime);
        tvDepartureDate = (TextView) findViewById(R.id.tvDepartureDate);
        tvDepartureTime = (TextView) findViewById(R.id.tvDepartureTime);
        btnGoToMain = (Button) findViewById(R.id.btnGoToMain);
        switchLocation = (Switch) findViewById(R.id.switchGps);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectDepartureDate) {
            mySelectDate();
        } else if (v == btnSelectDepartureTime) {
            mySelectTime();
        } else if (v == btnGoToMain) {
            myGoActivity();
        }
    }

    private void mySelectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(TripStartActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvYear = year;
                tvMonth = month+1;
                tvDay = dayOfMonth;
                Log.d("date listener => ", tvDay + "/" + tvMonth + "/" + tvYear);
                tvDepartureDate.setText(tvDay + "/" + tvMonth + "/" + tvYear);
            }
        }, tvYear, tvMonth-1, tvDay);
        datePickerDialog.show();
    }

    private void mySelectTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(TripStartActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvHour = hourOfDay;
                tvMinute = minute;
                String temp;
                if (minute < 10) {
                    temp = 0 + String.valueOf(minute);
                } else {
                    temp = String.valueOf(minute);
                }
                tvDepartureTime.setText(hourOfDay + "." + temp + " น.");

            }
        },tvHour,tvMinute,false);
        timePickerDialog.show();
    }

    private void myGoActivity() {


        // TODO CheckData Location
        myGetText();
        Boolean checkText = myCheck();
        if (checkText) {
            myConvertData();
            saveToDatabase();
        }
    }

    private void saveToDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseTripDetail.COL_LICENSEPLATE, str_licenseplate);
        contentValues.put(DatabaseTripDetail.COL_RESERVATION_NUMBER, strReservationNumber);
        contentValues.put(DatabaseTripDetail.COL_PURPOSE, strPurpose);
        contentValues.put(DatabaseTripDetail.COL_ARRIVAL_ODOMETER, odometerDouble);
        contentValues.put(DatabaseTripDetail.COL_FUEL_LEVEL, fuellLevelDouble);
        contentValues.put(DatabaseTripDetail.COL_DEPARTURE_DATETIME, strDepartureDateTime);

        myDbHelper = new MyDbHelper(TripStartActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        long insertId = sqLiteDatabase.insert(DatabaseTripDetail.TABLE_NAME, null, contentValues);
        Log.d("insert id = > ", insertId + "");
        editor.putLong(trip_id, insertId);
    }

    private void myConvertData() {
        odometerDouble = Double.valueOf(strDepartureOdometer);
        fuellLevelDouble = Double.valueOf(strFuelLevel);
    }

    private boolean myCheck() {
        if (strReservationNumber.equals("") || strPurpose.equals("") || strDepartureOdometer.equals("") || strFuelLevel.equals("") || strDepartureDateTime.equals("")) {
//        if (strReservationNumber.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void myGetText() {
        str_licenseplate = sharedPreferences.getString(LicensePlateActivity.licenPlate, "");
        Toast.makeText(this, str_licenseplate, Toast.LENGTH_SHORT).show();

        strReservationNumber = edtReservationNumber.getText().toString().trim();
        strPurpose = edtPurpose.getText().toString().trim();
        strDepartureOdometer = edtDepartureOdometer.getText().toString().trim();
        strFuelLevel = edtFuelLevel.getText().toString().trim();
        strDepartureDateTime = myDateModify.getStrDateTimeModify(tvDay, tvMonth, tvYear, tvHour, tvMinute);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            MyAddPermissionLocation myAddPermissionLocation = new MyAddPermissionLocation(TripStartActivity.this);
            myAddPermissionLocation.checkLocation();
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No use", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }

    }


}
