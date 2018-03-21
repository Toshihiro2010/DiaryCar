package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.common_class.MyAddPermissionLocation;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.common_class.MyLocationFirst;
import com.stecon.patipan_on.diarycar.controller.TripEndDialog;
import com.stecon.patipan_on.diarycar.database.DatabaseTripDetail;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.MyDateModify;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripStartActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, MyAddPermissionLocation.OnCustomClickDialog, MyAddPermissionLocation.OnNextFunction, MyLocationFirst.OnNextLocationFunction {

//    private EditText edtReservationNumber;
//    private EditText edtPurpose;
    private EditText edtDepartureOdometer;
    //private EditText edtFuelLevel;
    private EditText edtNameLocation;

    private ImageButton imgBtnDepartureSelectDate;
    private ImageButton imgBtnDepartureSelectTime;
    private Button btnGoToMain;
    private Button btnCancelTripEnd;

    private TextView tvDepartureDate;
    private TextView tvDepartureTime;
    private TextView txtNameLocation;
    private TextView tvShowTrip;

    private ProgressBar progressBar;

    private Switch switchLocation;

    private LinearLayout tvLinearLocation;
    private ConstraintLayout constraintLayout;

//    private String strReservationNumber;
//    private String strPurpose;
    private String strDepartureOdometer;
//    private String strFuelLevel;
    private String strDepartureDateTime;
    private String strDepartureLocationName;

    private Double odometerDouble;
    //private Double fuellLevelDouble;
    private Double latitude;
    private Double longtiude;


    private String str_licenseplate;
    private long tripId;

    private int tvHour , tvMinute;
    private int tvYear , tvMonth , tvDay;

    private MyDateModify myDateModify;
    private MyLocationFirst myLocationFirst;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MyAddPermissionLocation myAddPermissionLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_start);

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        myDbHelper = new MyDbHelper(TripStartActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        str_licenseplate = sharedPreferences.getString(MyAppConfig.licensePlate, "");

        bindWidget();
        onMySetDateSpinner();

        myStartSetText();
        myOnClick();

//        progressBar.setVisibility(View.INVISIBLE);
//        constraintLayout.setBackgroundColor(Color.RED);
//        btnGoToMain.setBackgroundColor(Color.BLACK);

    }

    private void onMySetDateSpinner() {
        Date date = new Date();
        Log.d("date => ", date.toString());
        tvHour = date.getHours();
        tvMinute = date.getMinutes();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        myDateModify = new MyDateModify(timeStamp);
        tvYear = myDateModify.getYear();
        tvMonth = myDateModify.getMonth();
        tvDay = myDateModify.getDay();
    }


    private void myOnClick() {
        imgBtnDepartureSelectDate.setOnClickListener(this);
        imgBtnDepartureSelectTime.setOnClickListener(this);
        btnGoToMain.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(this);
        btnCancelTripEnd.setOnClickListener(this);

    }

    private void myStartSetText() {
        tvDepartureDate.setText(tvDay + "/" + tvMonth + "/" + tvYear);
        tvDepartureTime.setText(tvHour + "." + tvMinute + " " + getResources().getString(R.string.short_minute));

    }

    private void bindWidget() {
//        edtReservationNumber = (EditText) findViewById(R.id.edtReservationNumber);
//        edtPurpose = (EditText) findViewById(R.id.edtPurpose);
        edtDepartureOdometer = (EditText) findViewById(R.id.edtDapartureOdometer);
        //edtFuelLevel = (EditText) findViewById(R.id.edtFuelLevel);
        tvDepartureDate = (TextView) findViewById(R.id.tvDepartureDate);
        tvDepartureTime = (TextView) findViewById(R.id.tvDepartureTime);
        btnGoToMain = (Button) findViewById(R.id.btnGoToMain);
        switchLocation = (Switch) findViewById(R.id.switchGps);
        txtNameLocation = (TextView) findViewById(R.id.txtNameLocation);
        edtNameLocation = (EditText) findViewById(R.id.edtNameLocation);
        tvLinearLocation = (LinearLayout) findViewById(R.id.tvLinearLocation);
        imgBtnDepartureSelectDate = (ImageButton) findViewById(R.id.imgBtnDepartureSelectDate);
        imgBtnDepartureSelectTime = (ImageButton) findViewById(R.id.imgBtnDepartureSelectTime);
        constraintLayout = (ConstraintLayout) findViewById(R.id.layoutTripStartActivity);
        tvShowTrip = (TextView) findViewById(R.id.tvShowTrip);
        progressBar = (ProgressBar) findViewById(R.id.progressBarTrip);
        btnCancelTripEnd = (Button) findViewById(R.id.btnCancelTripEnd);
    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnDepartureSelectDate) {
            mySelectDate();
        } else if (v == imgBtnDepartureSelectTime) {
            mySelectTime();
        } else if (v == btnGoToMain) {
            onSQLiteSaveData();
        } else if (v == btnCancelTripEnd) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(TripStartActivity.this, "Trip End", "คุณต้องการจบการเดินทาง ใช่ หรือ ไม่");
            customAlertDialog.myDefaultDialog();
            customAlertDialog.show();
            customAlertDialog.setOnMyDialogActivity(new CustomAlertDialog.OnMyDialogActivity() {
                @Override
                public void onMyDialogPositive() {
                    onDialogTripEnd();
                }

                @Override
                public void onMyDialogNegative() {

                }
            });
        }
    }

    private void onDialogTripEnd() {
        TripEndDialog tripEndDialog = new TripEndDialog(TripStartActivity.this, odometerDouble);
        tripEndDialog.onShow();
        tripEndDialog.registerOnNextListener(new TripEndDialog.OnNextListener() {
            @Override
            public void onStartNextListener() {
                customSetButtonTripEnd();
                editor.remove(MyAppConfig.trip_id);
                editor.commit();
                tripId = 0;
                mySetEmptyUi();
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tripId = sharedPreferences.getLong(MyAppConfig.trip_id, 0);
        Log.d("tripId = > ", tripId + " ");
        mySetStatusStartTrip();
    }

    private void customSetButtonTripStart() {
        btnGoToMain.setVisibility(View.INVISIBLE);
        btnCancelTripEnd.setVisibility(View.VISIBLE);
        btnCancelTripEnd.setBackgroundColor(getResources().getColor(R.color.default_color_button));
        constraintLayout.setBackgroundColor(Color.RED);
        edtDepartureOdometer.setEnabled(false);
        imgBtnDepartureSelectDate.setEnabled(false);
        imgBtnDepartureSelectTime.setEnabled(false);
        switchLocation.setEnabled(false);
        edtNameLocation.setEnabled(false);
        tvShowTrip.setVisibility(View.VISIBLE);
        tvShowTrip.setText(getResources().getString(R.string.message_trip_on_start));
        progressBar.setVisibility(View.VISIBLE);

    }

    private void customSetButtonTripEnd() {
        btnGoToMain.setVisibility(View.VISIBLE);
        btnCancelTripEnd.setVisibility(View.INVISIBLE);
        constraintLayout.setBackgroundResource(R.drawable.car_background);
        edtDepartureOdometer.setEnabled(true);
        imgBtnDepartureSelectDate.setEnabled(true);
        imgBtnDepartureSelectTime.setEnabled(true);
        switchLocation.setEnabled(true);
        edtNameLocation.setEnabled(true);
        tvShowTrip.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
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
                tvDepartureTime.setText(hourOfDay + "." + temp + getResources().getString(R.string.short_minute));

            }
        },tvHour,tvMinute,false);
        timePickerDialog.show();
    }

    private void onSQLiteSaveData() {

        myGetText();
        Boolean checkText = myCheck();
        if (checkText) {
            myConvertData();
            saveToDatabase();

            customSetButtonTripStart();
        }
    }

    private void saveToDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseTripDetail.COL_LICENSEPLATE, str_licenseplate);
//        contentValues.put(DatabaseTripDetail.COL_RESERVATION_NUMBER, strReservationNumber);
//        contentValues.put(DatabaseTripDetail.COL_PURPOSE, strPurpose);
        contentValues.put(DatabaseTripDetail.COL_DEPARTURE_ODOMETER, odometerDouble);
       // contentValues.put(DatabaseTripDetail.COL_FUEL_LEVEL, fuellLevelDouble);
        contentValues.put(DatabaseTripDetail.COL_DEPARTURE_DATETIME, strDepartureDateTime);
        contentValues.put(DatabaseTripDetail.COL_DEPARTURE_LOCATION_NAME, strDepartureLocationName);
        contentValues.put(DatabaseTripDetail.COL_DEPARTURE_LATITUDE, latitude);
        contentValues.put(DatabaseTripDetail.COL_DEPARTURE_LONGITUDE, longtiude);

        long insertId = sqLiteDatabase.insert(DatabaseTripDetail.TABLE_NAME, null, contentValues);
        tripId = insertId;
        Log.d("insert id = > ", insertId + "");
        editor.putLong(MyAppConfig.trip_id, insertId);
        editor.commit();
    }

    private void myConvertData() {
        odometerDouble = Double.valueOf(strDepartureOdometer);
        //fuellLevelDouble = Double.valueOf(strFuelLevel);
    }

    private boolean myCheck() {
        if (strDepartureOdometer.equals("") || strDepartureDateTime.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void mySetStatusStartTrip() {
        if (tripId != 0) { // กำลังเดินทาง
            mySetUiFromSQLite();
        }else{ // ยังไม่มีการเดินทาง
            customSetButtonTripEnd();
        }
    }

    private void mySetUiFromSQLite() {

        String strSql = "SELECT * FROM " + DatabaseTripDetail.TABLE_NAME + " WHERE " + DatabaseTripDetail.COL_ID + "=" + tripId;
        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
        cursor.moveToFirst();
        if (cursor != null) {
            cursor.moveToFirst();

            double departure_odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_ODOMETER));
            String departure_date = cursor.getString(cursor.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_DATETIME));
            double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_LONGITUDE));
            String location_name = cursor.getString(cursor.getColumnIndex(DatabaseTripDetail.COL_DEPARTURE_LOCATION_NAME));

            if (!departure_date.equals("")) {
                MyDateTimeModify myDateTimeModify = new MyDateTimeModify();
                myDateTimeModify.customDateTimeModifySqlite(departure_date);

                edtDepartureOdometer.setText(departure_odometer + "");
                tvDepartureDate.setText(myDateTimeModify.getStrDate() + "");
                tvDepartureTime.setText(myDateTimeModify.getStrTime() + "");
            }
            if (latitude != 0 || longitude != 0) {
                switchLocation.setChecked(true);
            }
            if (!location_name.equals("")) {
                edtNameLocation.setText(location_name);
            }
        }
        customSetButtonTripStart();
    }

    private void mySetUI2() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void mySetEmptyUi() {
        edtDepartureOdometer.setText("");
        switchLocation.setChecked(false);
        edtNameLocation.setText("");

    }


    private void myGetText() {
        str_licenseplate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
//        strReservationNumber = edtReservationNumber.getText().toString().trim();
//        strPurpose = edtPurpose.getText().toString().trim();
        strDepartureOdometer = edtDepartureOdometer.getText().toString().trim();
        //strFuelLevel = edtFuelLevel.getText().toString().trim();
        strDepartureDateTime = myDateModify.getStrDateTimeModify(tvDay, tvMonth, tvYear, tvHour, tvMinute);
        strDepartureLocationName = edtNameLocation.getText().toString().trim();


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView == switchLocation) {
            if (isChecked) {
                if (tripId == 0) {
                    myAddPermissionLocation = new MyAddPermissionLocation(TripStartActivity.this);
                    myAddPermissionLocation.setOnNextFunction(TripStartActivity.this);
                    myAddPermissionLocation.setOnCustomClickDialog(TripStartActivity.this);
                    myAddPermissionLocation.checkLocation();
                }

            } else {
                edtNameLocation.setText("");

            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Open GPS", Toast.LENGTH_SHORT).show();
                onNewNextFunction();
                myAddPermissionLocation.setStatusLocation(true);

            } else {
                onNegativeMyDialog();

            }
        }

    }


    @Override
    public void onPositiveMyDialog() {
        Log.d("gps => ", "Positive");

    }

    @Override
    public void onNegativeMyDialog() {
        Log.d("gps => ", "onNegativeMyDialog");
        if (switchLocation.isChecked()) {
            switchLocation.setChecked(false);
            Toast.makeText(this, "No Open GPS ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNewNextFunction() {

        myLocationFirst = new MyLocationFirst(TripStartActivity.this);
        myLocationFirst.setListennerNextLocationFunction(TripStartActivity.this);
        myLocationFirst.onLocationStart();
    }

    @Override
    public void onStartNextFunction() {
        latitude = myLocationFirst.getLatitude();
        longtiude = myLocationFirst.getLongitude();

        Geocoder geocoder = new Geocoder(TripStartActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longtiude, 1);

            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String temp = address + " " + city + " " + state + " " + country;
                edtNameLocation.setText(temp);
                Toast.makeText(TripStartActivity.this, temp, Toast.LENGTH_SHORT).show();

            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.d("latitude/longtitude => ", latitude + " / " + longtiude);
    }

    @Override
    public void onErrorNotFindGps() {
        if (switchLocation.isChecked()) {
            switchLocation.setChecked(false);
        }
        Toast.makeText(this, "You Should Allow Gps", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Log.d("activity = > ", "onBackPressed");
        if (tripId != 0) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(TripStartActivity.this, "TripStart", "คุณไม่สามารถออกได้");
            customAlertDialog.myDefaultDialog();
            customAlertDialog.setOnMyDialogActivity(new CustomAlertDialog.OnMyDialogActivity() {
                @Override
                public void onMyDialogPositive() {
                    onResume();
                }

                @Override
                public void onMyDialogNegative() {
                    onResume();

                }
            });
            customAlertDialog.show();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity = > ", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity = > ", "onDestroy");

    }
}
