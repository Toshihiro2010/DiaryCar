package com.stecon.patipan_on.diarycar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licen_plate);

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licenPlate, "");

        if (!strLicensePlate.equals("")) {
            Intent intent = new Intent(LicensePlateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        binWidGet();
        btnGoMain.setOnClickListener(this);

    }

    private void binWidGet() {
        edtLicensePlate = (EditText) findViewById(R.id.edtLicensePlate);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGoMain){
            strLicensePlate = edtLicensePlate.getText().toString().trim();
            if (strLicensePlate.equals("")) {
                Toast.makeText(this, "Please in put data", Toast.LENGTH_SHORT).show();
            } else {
                mySharePreferences();
//                Intent intent = new Intent(LicensePlateActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        }
    }

    private void mySharePreferences() {
        editor.putString(MyAppConfig.licenPlate, strLicensePlate);
        editor.commit();

    }
}
