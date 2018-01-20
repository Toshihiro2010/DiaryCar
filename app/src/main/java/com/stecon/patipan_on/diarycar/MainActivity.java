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

import com.stecon.patipan_on.diarycar.controller.MyLocationFirst;
import com.stecon.patipan_on.diarycar.controller.MySendToServer;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyLocationFirst.OnNextLocationFunction {

    private Button btnGoDiary;
    private Button btnGoOil;
    private Button btnGoService;
    private TextView tvMainLicensePlate;
    private Button btnGoExit;

    private String strLicensePlate;

    private MyCallBack myCallBack = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidGet();

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licenPlate, "");
        editor = sharedPreferences.edit();
        Toast.makeText(this, strLicensePlate, Toast.LENGTH_SHORT).show();
        if (strLicensePlate.equals("")) {
            Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
            startActivity(intent);
        }
        tvMainLicensePlate.setText(strLicensePlate);



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
//        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
//        strLicensePlate = sharedPreferences.getString(MyAppConfig.licenPlate, "");
//        editor = sharedPreferences.edit();
//        if (strLicensePlate.equals("")) {
//            Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
//            startActivity(intent);
//        }
//        tvMainLicensePlate.setText(strLicensePlate);

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
        btnGoService = (Button) findViewById(R.id.btnGoService);
        tvMainLicensePlate = (TextView) findViewById(R.id.tvMainLicensePlate);
        btnGoExit = (Button) findViewById(R.id.btnExitLicensePlate);
    }

    private void myOnClick() {
        btnGoDiary.setOnClickListener(this);
        btnGoOil.setOnClickListener(this);
        btnGoService.setOnClickListener(this);
        btnGoExit.setOnClickListener(this);

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

        } else if (v == btnGoService) {
            Intent intent = new Intent(MainActivity.this, ServiceListActivity.class);
            startActivity(intent);
        } else if (v == btnGoExit) {
            onExit();
        }
    }

    private void onExit() {
        editor.remove(MyAppConfig.licenPlate);
        editor.commit();
        Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
        startActivity(intent);
        finish();

    }

    private void myCustomTest() {
        MySendToServer mySendToServer = new MySendToServer(MainActivity.this);
        mySendToServer.syncToServer();

    }

    @Override
    public void onStartNextFunction() {

    }


    public interface MyCallBack {
        void callBack();
    }

    public void registerMyCallBack(MyCallBack listener) {
        this.myCallBack = listener;
    }




}
