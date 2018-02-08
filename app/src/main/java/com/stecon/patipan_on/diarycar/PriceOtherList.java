package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.PriceOtherAdapter;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.DatabaseTripCost;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.MyDateModify;
import com.stecon.patipan_on.diarycar.model.TripCostModel;

import java.util.ArrayList;

public class PriceOtherList extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView priceRecycleView;
    private Button btnPriceAdd;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private long trip_id;

    private ArrayList<TripCostModel> costModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_other_list);
        binWidGet();

        myDbHelper = new MyDbHelper(PriceOtherList.this);
        sqLiteDatabase = myDbHelper.getReadableDatabase();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PriceOtherList.this);
        priceRecycleView.setLayoutManager(linearLayoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        trip_id = sharedPreferences.getLong(MyAppConfig.trip_id, 0);

//        String strSql = "SELECT * FROM " + DatabaseTripCost.TABLE_NAME + " WHERE " + DatabaseTripCost.COL_TRIP_ID + " = " + trip_id;

        btnPriceAdd.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        costModelArrayList = new ArrayList<>();
        String strSql = "SELECT * FROM " + DatabaseTripCost.TABLE_NAME + " WHERE " + DatabaseTripCost.COL_TRIP_ID + " = " + trip_id;
        //String strSql = "SELECT * FROM " + DatabaseTripCost.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(strSql , null);

        int i = 0 ;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                i++;
                Log.d("I => ", i + "");
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_ID));
                int trip_id = cursor.getInt(cursor.getColumnIndex(DatabaseTripCost.COL_TRIP_ID));
                String price_type = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_PRICE_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_PRICE_TITLE));
                double money = cursor.getDouble(cursor.getColumnIndex(DatabaseTripCost.COL_PRICE_MONEY));
                String note = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_NOTE));
                String temp_date = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_TRANSACTION_DATE));

                String[] date = MyDateModify.getStrsDateTimeFromSqlite(temp_date);

                TripCostModel tripCostModel = new TripCostModel(id,trip_id, price_type, title, money, note, date[0]);
                costModelArrayList.add(tripCostModel);
                cursor.moveToNext();
            }
        }

        PriceOtherAdapter priceOtherAdapter = new PriceOtherAdapter(PriceOtherList.this, costModelArrayList);
        priceRecycleView.setAdapter(priceOtherAdapter);
    }

    private void binWidGet() {
        priceRecycleView = (RecyclerView) findViewById(R.id.priceRecycleView);
        btnPriceAdd = (Button) findViewById(R.id.btnAddPrice);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPriceAdd) {
            Intent intent = new Intent(PriceOtherList.this, PriceCostJournalActivity.class);
            startActivity(intent);
        }
    }
}
