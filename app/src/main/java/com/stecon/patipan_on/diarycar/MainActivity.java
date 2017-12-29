package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyStartFirst;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGoDiary;
    private Button btnGoOil;
    private Button btnGoPriceOther;
    private TextView txtTvMainTitle;

    private String strLicensePlate;
    private long tripIdALong;

    private MyCallBack myCallBack = null;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidGet();
        myOnClick();
        if (isNetworkAvailable()) {
            Log.d("Internet => ", "Conntected");
        } else {
            Log.d("Internet => ", "No Conncet");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences(LicensePlateActivity.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sharedPreferences.getString(LicensePlateActivity.licenPlate, "");
        if (strLicensePlate.equals("")) {
            Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
            startActivity(intent);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        Boolean status = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return status;
    }

    private void bindWidGet() {
        btnGoDiary = (Button) findViewById(R.id.btnGoDiary);
        btnGoOil = (Button) findViewById(R.id.btnGoOil);
        btnGoPriceOther = (Button) findViewById(R.id.btnPriceOther);
        txtTvMainTitle = (TextView) findViewById(R.id.tvMainTitle);
    }

    private void myOnClick() {
        btnGoDiary.setOnClickListener(this);
        btnGoOil.setOnClickListener(this);
        btnGoPriceOther.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnGoDiary) {
            Log.d("test => ", "button go diary");
            Intent intent = new Intent(this, TripStartActivity.class);
            startActivity(intent);

        } else if (v == btnGoOil) {
            Log.d("test => ", "button btnGoOil");
            Intent intent = new Intent(getApplicationContext(), OilListActivity.class);
            startActivity(intent);

        } else if (v == btnGoPriceOther) {

            Toast.makeText(this, "No Function", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, PriceOtherList.class);
//            startActivity(intent);

        }
    }


    public interface MyCallBack {
        void callBack();
    }

    public void registerMyCallBack(MyCallBack listener) {
        this.myCallBack = listener;
    }




}
