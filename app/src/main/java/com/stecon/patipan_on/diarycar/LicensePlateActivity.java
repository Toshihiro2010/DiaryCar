package com.stecon.patipan_on.diarycar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LicensePlateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLicensePlate;
    private Button btnGoMain;

    private String strLicensePlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licen_plate);

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
                edtLicensePlate.setText("");
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("licensePlate", strLicensePlate);
                startActivity(intent);
                finish();
            }
        }
    }
}
