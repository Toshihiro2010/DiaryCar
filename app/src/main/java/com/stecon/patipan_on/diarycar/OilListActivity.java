package com.stecon.patipan_on.diarycar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stecon.patipan_on.diarycar.controller.PostInfoAdapter;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.controller.MyAdapter;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OilListActivity extends AppCompatActivity {

    private RecyclerView oilRecycleList;
    private MyDbHelper myDbHelper;
    private DatabaseOilJournal mDbOilDiary;
    private SQLiteDatabase sqLiteDatabase;

    private Cursor cursor;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_list);

        oilRecycleList = (RecyclerView) findViewById(R.id.oilRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OilListActivity.this);
        oilRecycleList.setLayoutManager(linearLayoutManager);

        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        arrayList = new ArrayList<>();


        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseOilJournal.TABLE_NAME, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0 ; i < cursor.getCount(); i++) {
                OilDataModel oilDataModel = new OilDataModel();
                double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
                double unit_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
                double volume = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
                double total_rpice = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
                int partial_fillup = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_PARTIAL_FILL_UP));
                String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
                double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LONGITUDE));



                String tempDouble = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_TRANSACTION_DATE));


                Log.d("tempDouble =>", tempDouble + " ");
                arrayList.add(String.valueOf(tempDouble));
                arrayList.add("test");
            }

        }



        PostInfoAdapter postInfoAdapter = new PostInfoAdapter(arrayList);
        oilRecycleList.setAdapter(postInfoAdapter);




    }


    @Override
    protected void onPause() {
        super.onPause();
        myDbHelper.close();
        sqLiteDatabase.close();
    }
}
