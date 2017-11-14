package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.model.MyDateModify;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifiedActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText edtMember;
    private EditText edtIdCard;
    private TextView txtDate;

    private Button btnSelectDate;
    private Button btnVerified;


    private int mDay;
    private int mMonth;
    private int mYear;

    private int tvDay , tvMonth , tvYear;

    private String strMember;
    private String strIdCard;
    private String strDateShow;


    private MyDateModify myDateModify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        bindWidget();
        Date date = new Date();

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        myDateModify = new MyDateModify(timeStamp);
        strDateShow = myDateModify.getStrDate();
        txtDate.setText(strDateShow);
        mySetOnclick();

    }

    private void mySetOnclick() {
        btnSelectDate.setOnClickListener(this);
        btnVerified.setOnClickListener(this);
    }


    private void bindWidget() {
        edtMember = (EditText) findViewById(R.id.edtMember);
        edtIdCard = (EditText) findViewById(R.id.edtIdCard);
        txtDate = (TextView) findViewById(R.id.txtDate);
        btnVerified = (Button) findViewById(R.id.btnVerified);
        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectDate) {
            mySetDate();
        } else if (v == btnVerified) {
            //Toast.makeText(this, "click => Verified", Toast.LENGTH_SHORT).show();
            myVertified();
        }

    }

    private void myVertified() {
        strDateShow = txtDate.getText().toString();
        strIdCard = edtIdCard.getText().toString().trim();
        strMember = edtMember.getText().toString().trim();
        if (strDateShow.equals("") || strIdCard.equals("") || strMember.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, strIdCard + "\n" + strMember + "\n" + strDateShow, Toast.LENGTH_SHORT).show();
        }

    }


    private void mySetDate() {
        //String strDate = txtDate.getText().toString();
        strDateShow = txtDate.getText().toString();

        myDateModify = new MyDateModify(strDateShow);
        mDay = myDateModify.getDay();
        mMonth = myDateModify.getMonth();
        mYear = myDateModify.getYear();

        DatePickerDialog datePickerDialog = new DatePickerDialog(VerifiedActivity.this , VerifiedActivity.this , mYear, mMonth-1, mDay);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tvDay = year;
        tvMonth = month+1;
        tvYear = dayOfMonth;

        txtDate.setText(tvYear + "/" + tvMonth + "/" + tvDay);
        //txtDate.setText(strDateShow);

    }
}
