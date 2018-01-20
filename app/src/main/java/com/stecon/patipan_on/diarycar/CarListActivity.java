package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.database.DatabaseCarDiary;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;

public class CarListActivity extends AppCompatActivity {


    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseCarDiary myDbCar;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();


    }

    private void mySetList() {


    }

    @Override
    protected void onPause() {
        super.onPause();
        sqLiteDatabase.close();
        myDbHelper.close();
    }
}
