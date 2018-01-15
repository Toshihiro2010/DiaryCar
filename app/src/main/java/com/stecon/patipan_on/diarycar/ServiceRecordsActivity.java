package com.stecon.patipan_on.diarycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ServiceRecordsActivity extends AppCompatActivity {

    private TextView tvLicensePlate;
    private Spinner spinnerService;
    private EditText edtServiceOdometer;
    private EditText edtService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_records);
    }
}
