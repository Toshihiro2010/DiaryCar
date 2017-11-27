package com.stecon.patipan_on.diarycar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.controller.MyAdapter;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;

public class OilListActivity extends AppCompatActivity {

    private ListView list_oil_diary;

    private MyDbHelper myDbHelper;
    private DatabaseOilJournal mDbOilDiary;
    private SQLiteDatabase sqLiteDatabase;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_list);
        list_oil_diary = (ListView) findViewById(R.id.oil_listview);

        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        // TODO: 10/27/2017 เขียนฟังก์ชัน Query ใหม่
        String strSql = "SELECT strftime('%d/%m/%Y'," + mDbOilDiary.COL_TRANSACTION_DATE + ") as date," + mDbOilDiary.COL_ODOMETER + "," + mDbOilDiary.COL_TOTAL_PRICE + " FROM " + mDbOilDiary.TABLE_NAME ;
        //String strSql2 = "select strftime('%d/%m/%Y', date) as date,liter_amount , money from OIL";
        Log.d("strsql => ", strSql);
        cursor = sqLiteDatabase.rawQuery(strSql , null);
        Log.d("test=> ", cursor.getCount() + " ");

        if (cursor.getCount() > 0) {
            mySetListView();
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

    }

    private void mySetListView() {
        int temp = cursor.getCount();
        String[] strDate = new String[temp];
        String[] strMoney = new String[temp];
        cursor.moveToFirst();
        Log.d("sql = > ", cursor.getString(cursor.getColumnIndex("date")));

        for (int i = 0 ; i < temp ; i++) {
            strDate[i] = cursor.getString(cursor.getColumnIndex(mDbOilDiary.COL_TRANSACTION_DATE));
            strMoney[i] = cursor.getString(cursor.getColumnIndex(mDbOilDiary.COL_TOTAL_PRICE));
            if (!cursor.isLast()) {
                cursor.moveToNext();
            }
        }
        MyAdapter myAdapter = new MyAdapter(this, strDate, strMoney);
        list_oil_diary.setAdapter(myAdapter);
        cursor.moveToFirst();

    }

    @Override
    protected void onPause() {
        super.onPause();
        myDbHelper.close();
        sqLiteDatabase.close();
    }
}
