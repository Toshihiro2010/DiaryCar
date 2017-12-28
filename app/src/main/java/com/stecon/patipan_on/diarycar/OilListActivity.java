package com.stecon.patipan_on.diarycar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.PostInfoAdapter;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.model.MyDateModify;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OilListActivity extends AppCompatActivity {

    private RecyclerView oilRecycleList;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Cursor cursor;
    private ArrayList<OilDataModel> arrayList;
    private List<Address> addresses;

    private Button btnOilJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_list);

        oilRecycleList = (RecyclerView) findViewById(R.id.oilRecyclerView);
        btnOilJournal = (Button) findViewById(R.id.btnOilJournal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OilListActivity.this);
        oilRecycleList.setLayoutManager(linearLayoutManager);

        btnOilJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OilListActivity.this, OilJournalActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OnActivity = > ", "onStart - OilListActivity");
        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        arrayList = new ArrayList<>();

        String strSql = "SELECT * FROM " + DatabaseOilJournal.TABLE_NAME + " ORDER BY " + DatabaseOilJournal.COL_ID + " DESC";
        cursor = sqLiteDatabase.rawQuery(strSql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0 ; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
                double unit_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
                double volume = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
                double total_rpice = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
                int partial_fillup = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_PARTIAL_FILL_UP));
                String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
                double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LONGITUDE));
                String note = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_NOTE));
                String fueltype = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_FUEL_TYPE));
                String tempDate = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_DATE_UPDATE));
                String[] customDate = MyDateModify.getStrsDateTimeFromSqlite(tempDate);

                OilDataModel oilDataModel = new OilDataModel(id,odometer, unit_price, volume,fueltype, total_rpice, partial_fillup, payment_type, latitude, longitude, note, customDate[0]);
                arrayList.add(oilDataModel);


                Geocoder geocoder = new Geocoder(OilListActivity.this , Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if (addresses != null) {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        oilDataModel.setStrLocation(address + " " + city + " " + state + " " + country);

                    } else {

//                        oilDataModel.setStrLocation("No location");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!cursor.isLast()) {
                    cursor.moveToNext();
                }

            }

        }

        PostInfoAdapter postInfoAdapter = new PostInfoAdapter(OilListActivity.this,arrayList);
        oilRecycleList.setAdapter(postInfoAdapter);



    }

    @Override
    protected void onPause() {
        super.onPause();
        myDbHelper.close();
        sqLiteDatabase.close();
    }
}
