package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.model.MyAppConfig;

public class LicensePlateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLicensePlate;
    private Button btnGoMain;

    private String strLicensePlate;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final int REQUEST_CODE = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licen_plate);

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");

        if (!strLicensePlate.equals("")) {
            Intent intent = new Intent(LicensePlateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        binWidGet();
        btnGoMain.setOnClickListener(this);

    }

    private void binWidGet() {
        edtLicensePlate =  findViewById(R.id.edtLicensePlate);
        btnGoMain =  findViewById(R.id.btnGoMain);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGoMain){
            strLicensePlate = edtLicensePlate.getText().toString().trim();
            if (strLicensePlate.equals("")) {
                Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
            } else {
                mySharePreferences();
//                Intent intent = new Intent(LicensePlateActivity.this, MainActivity.class);
//                startActivity(intent);
                Intent intent = new Intent();
                intent.putExtra(MyAppConfig.licensePlate, strLicensePlate);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private void mySharePreferences() {
        editor.putString(MyAppConfig.licensePlate, strLicensePlate);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();

    }
}
