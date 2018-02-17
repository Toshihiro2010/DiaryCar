package com.stecon.patipan_on.diarycar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.TripEndDialog;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

import java.util.ArrayList;

public class CarDiaryActivity extends AppCompatActivity implements View.OnClickListener, CustomAlertDialog.OnMyDialogActivity {

    private Button btnEndTrip;
    private Button btnPriceOtherTrip;

    private CustomAlertDialog customAlertDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_diary);

        binWidGet();
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v == btnEndTrip) {
            //Toast.makeText(this, "End Trip", Toast.LENGTH_SHORT).show();
            endTrip();
        } else if (v == btnPriceOtherTrip) {
            Intent intent = new Intent(getApplicationContext(), PriceOtherList.class);
            startActivity(intent);
        }
    }

    private void endTrip() {
        customAlertDialog = new CustomAlertDialog(CarDiaryActivity.this, getResources().getString(R.string.message_end_trip), getResources().getString(R.string.message_you_want_ent_trip));
        customAlertDialog.myDefaultDialog();
        customAlertDialog.show();
        customAlertDialog.setOnMyDialogActivity(this);
    }

    @Override
    public void onMyDialogPositive() {
        onUpdateDBEndTrip();
    }

    private void onUpdateDBEndTrip() {
        TripEndDialog tripEndDialog = new TripEndDialog(CarDiaryActivity.this);
        tripEndDialog.onShow();
        tripEndDialog.registerOnNextListener(new TripEndDialog.OnNextListener() {
            @Override
            public void onStartNextListener() {
                sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.remove(MyAppConfig.trip_id);
                editor.commit();
                finish();
            }
        });
        Log.d("listener => ", "onUpdateDBEndTrip");
    }

    @Override
    public void onMyDialogNegative() {
        Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
    }
}
