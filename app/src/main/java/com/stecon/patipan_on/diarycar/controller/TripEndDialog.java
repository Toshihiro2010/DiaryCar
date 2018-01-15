package com.stecon.patipan_on.diarycar.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.TripStartActivity;
import com.stecon.patipan_on.diarycar.database.DatabaseTripDetail;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by patipan_on on 1/12/2018.
 */

public class TripEndDialog implements View.OnClickListener {

    private Context context;
    private TextView tvArrivalDate;
    private TextView tvArrivalTime;
    private Button btnSelectDate;
    private Button btnSelectTime;
    private EditText edtArrivalParkingLocation;
    private EditText edtArrivalOdometer;
    private EditText edtTripNote;
    private Button btnCancel;
    private Button btnOk;

    private String strTvArrivalDate;
    private String strTvArrivalTime;
    private String strArrivalParkingLocation;
    private String strArrivalOdometer;
    private String strTripNote;

    private Double arrivalOdometerDouble;
    private Double latitude;
    private Double longitude;

    private Dialog dialog;
    private MyDateTimeModify myDateTimeModify;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private MyLocationFirst myLocationFirst;
    private OnNextListener onNextListener = null;


    public TripEndDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_trip_dialog);
        dialog.setTitle("End Trip");

        tvArrivalDate = (TextView) dialog.findViewById(R.id.tvArrivalDate);
        tvArrivalTime = (TextView) dialog.findViewById(R.id.tvArrivalTime);
        btnSelectDate = (Button) dialog.findViewById(R.id.btnArrivalSelectDate);
        btnSelectTime = (Button) dialog.findViewById(R.id.btnArrivalSelectTime);
        edtArrivalParkingLocation = (EditText) dialog.findViewById(R.id.edtArrivalParkingLocation);
        edtArrivalOdometer = (EditText) dialog.findViewById(R.id.edtArrivalOdometer);
        edtTripNote = (EditText) dialog.findViewById(R.id.edtEndNote);
        btnCancel = (Button) dialog.findViewById(R.id.btnCacel);
        btnOk = (Button) dialog.findViewById(R.id.btnOk);

        btnSelectDate.setOnClickListener(this);
        btnSelectTime.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        onDateTimeModify();

    }

    private void onDateTimeModify() {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy h:mm").format(date);
        Log.d("timeStamp => ", timeStamp);
        myDateTimeModify = new MyDateTimeModify(timeStamp);
        strTvArrivalDate = myDateTimeModify.getStrDate();
        strTvArrivalTime = myDateTimeModify.getStrTime();
        tvArrivalDate.setText(strTvArrivalDate);
        tvArrivalTime.setText(strTvArrivalTime + " น.");

    }

    public void onShow() {
        dialog.show();
    }

    public void onSelectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myDateTimeModify.setYear(year);
                myDateTimeModify.setMonth(month + 1);
                myDateTimeModify.setDay(dayOfMonth);


                strTvArrivalDate = myDateTimeModify.getStrDate();
                tvArrivalDate.setText(strTvArrivalDate);
            }
        }, myDateTimeModify.getYear(), myDateTimeModify.getMonth()-1, myDateTimeModify.getDay());
        datePickerDialog.show();
    }

    public void onSelectTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myDateTimeModify.setHour(hourOfDay);
                myDateTimeModify.setMinute(minute);

                strTvArrivalTime = myDateTimeModify.getStrTime();
                tvArrivalTime.setText(strTvArrivalTime + " น.");

            }
        },myDateTimeModify.getHour(),myDateTimeModify.getMinute(),false);
        timePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectDate) {
            onSelectDate();

        } else if (v == btnSelectTime) {
            onSelectTime();

        } else if (v == btnCancel) {
            dialog.dismiss();
            Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
        } else if (v == btnOk) {
            onDialogOk();
        }


    }

    private void onDialogOk() {
        dialogGetText();
        if (strArrivalParkingLocation.equals("") || strArrivalOdometer.equals("") || strTvArrivalDate.equals("") || strTvArrivalTime.equals("") || strTripNote.equals("")) {
            Toast.makeText(context, "No Text", Toast.LENGTH_SHORT).show();
        } else {
            customOnLocation();
        }
    }

    private void customOnLocation() {

        arrivalOdometerDouble = Double.valueOf(strArrivalOdometer);
        myLocationFirst = new MyLocationFirst(context);
        myLocationFirst.onLocationStart();
        myLocationFirst.registerOnextLocationFunction(new MyLocationFirst.OnNextLocationFunction() {
            @Override
            public void onStartNextFunction() {
                latitude = myLocationFirst.getLatitude();
                longitude = myLocationFirst.getLongitude();
                onSqlUpdate();
            }
        });
    }

    private void onSqlUpdate() {
        myDbHelper = new MyDbHelper(context);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        String arrivalDate = myDateTimeModify.getDateTimeToserver();
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").format(date);
        Log.d("timeStamp => ", timeStamp);

        SharedPreferences sharedPreferences = context.getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        long tripId = sharedPreferences.getLong(MyAppConfig.trip_id, 0);
        if (tripId == 0) {
            Toast.makeText(context, "Program not Process", Toast.LENGTH_SHORT).show();
        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseTripDetail.COL_ARRIVAL_DATETIME, arrivalDate);
            contentValues.put(DatabaseTripDetail.COL_ARRIVAL_ODOMETER, arrivalOdometerDouble);
            contentValues.put(DatabaseTripDetail.COL_ARRIVAL_PARKING_LOCATION, strArrivalParkingLocation);
            contentValues.put(DatabaseTripDetail.COL_UPDATE_BY, "User");
            contentValues.put(DatabaseTripDetail.COL_DATE_UPDATE, timeStamp);
            contentValues.put(DatabaseTripDetail.COL_NOTE, strTripNote);
            contentValues.put(DatabaseTripDetail.COL_ARRIVAL_LATITUDE, latitude);
            contentValues.put(DatabaseTripDetail.COL_ARRIVAL_LONGITUDE, longitude);
            int row = sqLiteDatabase.update(DatabaseTripDetail.TABLE_NAME, contentValues, DatabaseTripDetail.COL_ID + " = ?", new String[]{String.valueOf(tripId)});
            Log.d("row => ", row + "");
            if (onNextListener != null) {
                onNextListener.onStartNextListener();
            }
        }
        dialog.dismiss();

    }

    private void dialogGetText() {
        strTvArrivalDate = tvArrivalDate.getText().toString().trim();
        strTvArrivalTime = tvArrivalTime.getText().toString().trim();
        strArrivalOdometer = edtArrivalOdometer.getText().toString().trim();
        strArrivalParkingLocation = edtArrivalParkingLocation.getText().toString().trim();
        strTripNote = edtTripNote.getText().toString().trim();
    }


    public interface OnNextListener{
        void onStartNextListener();
    }

    public void registerOnNextListener(OnNextListener listener) {
        this.onNextListener = listener;
    }


}
