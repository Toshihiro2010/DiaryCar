package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CarListActivity extends AppCompatActivity {

    private Context context;
    private ListView listView;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseCarDiary myDbCar;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        listView = (ListView) findViewById(R.id.car_listview);

        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        String strSql = "SELECT * FROM " + myDbCar.TABLE_NAME;

        cursor = sqLiteDatabase.rawQuery(strSql , null);

        Log.d("cursor => ", cursor.getCount() + "");
        if (cursor.getCount() > 0) {
            mySetList();
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }



    }

    private void mySetList() {
        cursor.moveToFirst();
        int temp = cursor.getCount();
        String strDate[] = new String[temp];
        String strTitle[] = new String[temp];
        String strDetail[] = new String[temp];
        String strKm[] = new String[temp];



        for (int i = 0 ; i < temp ; i ++) {
            strDate[i] = cursor.getString(cursor.getColumnIndex(myDbCar.COL_DATE));
            strTitle[i] = cursor.getString(cursor.getColumnIndex(myDbCar.COL_TITLE));
            strDetail[i] = cursor.getString(cursor.getColumnIndex(myDbCar.COL_DETAIL));
            strKm[i] = cursor.getString(cursor.getColumnIndex(myDbCar.COL_KILOMETER));
            cursor.moveToNext();
        }
        MyAdapterTwo myAdapterTwo = new MyAdapterTwo(this, strTitle, strDate, strKm);
        listView.setAdapter(myAdapterTwo);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sqLiteDatabase.close();
        myDbHelper.close();
    }
}
