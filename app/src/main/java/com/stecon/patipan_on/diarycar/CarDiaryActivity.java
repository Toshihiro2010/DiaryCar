package com.stecon.patipan_on.diarycar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.util.ArrayList;

public class CarDiaryActivity extends AppCompatActivity implements View.OnClickListener {


    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private OilDataModel oilDataModel;

    private Button btnEndTrip;
    private Button btnPriceOtherTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_diary);


        binWidGet();
        myConnect();
        btnOnClick();



    }

    private void btnOnClick() {
        btnEndTrip.setOnClickListener(this);
        btnPriceOtherTrip.setOnClickListener(this);
    }

    private void binWidGet() {
        btnEndTrip = (Button) findViewById(R.id.btnEndTrip);
        btnPriceOtherTrip = (Button) findViewById(R.id.btnPriceOtherTrip);

    }

    private void myConnect() {
        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v == btnEndTrip) {
            Toast.makeText(this, "End Trip", Toast.LENGTH_SHORT).show();
        } else if (v == btnPriceOtherTrip) {
            Intent intent = new Intent(getApplicationContext(), PriceOtherActivity.class);
            startActivity(intent);
        }
    }
}
