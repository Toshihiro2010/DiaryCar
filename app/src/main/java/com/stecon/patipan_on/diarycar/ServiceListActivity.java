package com.stecon.patipan_on.diarycar;

import android.content.Intent;
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
import com.stecon.patipan_on.diarycar.controller.ServiceAdapter;
import com.stecon.patipan_on.diarycar.database.DatabaseServiceRecords;
import com.stecon.patipan_on.diarycar.database.DatabaseTripCost;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;
import com.stecon.patipan_on.diarycar.model.ServiceRecordModel;

import java.util.ArrayList;

public class ServiceListActivity extends AppCompatActivity {

    private RecyclerView serviceRecyclerView;
    private Button btnAddService;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ArrayList<ServiceRecordModel> serviceRecordModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        binWidGet();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceListActivity.this);
        serviceRecyclerView.setLayoutManager(linearLayoutManager);

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceListActivity.this, ServiceRecordsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void binWidGet() {
        serviceRecyclerView = (RecyclerView) findViewById(R.id.serviceRecycle);
        btnAddService = (Button) findViewById(R.id.btnAddService);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mySetList();

    }

    public void mySetList() {
        Log.d("OnActivity = > ", "onStart - OilListActivity");
        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        serviceRecordModelArrayList = new ArrayList<>();

        String strSql = "SELECT * FROM "
                + DatabaseServiceRecords.TABLE_NAME
                + " ORDER BY " + DatabaseServiceRecords.COL_ID + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {


                int id = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_ID));
                int service_id = cursor.getInt(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_ID));
                String license_plate = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LICENSE_PLATE));
                Double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_ODOMETER));
                Double fule_level = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_FUEL_LEVEL));
                Double service_cost = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_SERVICE_COST));
                Double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LATITUDE));
                Double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseServiceRecords.COL_LONGITUDE));
                //String location_name = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_LOCATION_NAME));
                String note = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_NOTE));

                String temp_date = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_TRANSACTION_DATE));
                String[] date = MyDateTimeModify.getStrsDateTimeFromSqlite(temp_date);

                String create_date = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_DATE_CREATE));
                String update_date = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_DATE_UPDATE));
                String create_by = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_CREATE_BY));
                String update_by = cursor.getString(cursor.getColumnIndex(DatabaseServiceRecords.COL_UPDATE_BY));

                ServiceRecordModel serviceRecordModel = new ServiceRecordModel(id, service_id, license_plate, odometer, fule_level, service_cost, latitude, longitude, note, date[0],create_date,update_date,create_by,update_by);
                serviceRecordModelArrayList.add(serviceRecordModel);
                cursor.moveToNext();
            }
        }

        ServiceAdapter serviceAdapter = new ServiceAdapter(ServiceListActivity.this, serviceRecordModelArrayList);
        serviceRecyclerView.setAdapter(serviceAdapter);


    }

}
