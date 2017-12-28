package com.stecon.patipan_on.diarycar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.util.ArrayList;

public class CarDiaryActivity extends AppCompatActivity {


    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private OilDataModel oilDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_diary);


        binWidGet();
        myConnect();



    }

    private void binWidGet() {

    }

    private void myConnect() {
        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
