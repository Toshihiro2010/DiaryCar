package com.stecon.patipan_on.diarycar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.model.MyDateModify;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripStartActivity extends AppCompatActivity {

    private EditText edtReservationNumber;
    private EditText edtPurpose;
    private EditText edtDepartureOdometer;
    private EditText edtFuelLevel;

    private Button btnSelectDepartureDate;
    private Button btnSelectDepartureTime;
    private Button btnGoToMain;

    private TextView tvDepartureDate;
    private TextView tvDepartureTime;

    private String strReservationNumber;

    //private TimePickerDialog timePickerDialog;

    private int tvHour , tvMinute;
    private int tvYear , tvMonth , tvDay;

    private MyDateModify myDateModify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_start);

        bindWidget();

        Date date = new Date();

        tvHour = date.getHours();
        tvMinute = date.getMinutes();

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        myDateModify = new MyDateModify(timeStamp);

        tvYear = myDateModify.getYear();
        tvMonth = myDateModify.getMonth();
        tvDay = myDateModify.getDay();


        btnSelectDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        tvDepartureTime.setText(hourOfDay + ":" + temp);
                        btnSelectDepartureTime.setText(hourOfDay + ":" + temp);

                    }
                },tvHour,tvMinute,false);
                timePickerDialog.show();

            }
        });

        btnSelectDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        btnGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TripStartActivity.this);
                alertDialog.setTitle("Test");
                alertDialog.setMessage("Loadding .....");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("ยกเลิกเฟ้ย", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TripStartActivity.this, "test", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("ใช้งาน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TripStartActivity.this, "Test2", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

//                Dialog dialog = new Dialog(TripStartActivity.this);
//                dialog.setTitle("Test");
//                Message message = new Message();
//                message.setData("testhhh");
//                dialog.setCancelMessage(new Message());

            }
        });


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
    }
}
