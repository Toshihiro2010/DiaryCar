package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.MyLocationFirst;
import com.stecon.patipan_on.diarycar.controller.MySendToServer;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyLocationFirst.OnNextLocationFunction {

    private Button btnGoDiary;
    private Button btnGoOil;
    private Button btnGoService;
    private TextView tvMainLicensePlate;
    private Button btnGoExit;
    private Button btnEng;
    private Button btnThai;

    private String strLicensePlate;

    private MyCallBack myCallBack = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("activity => ", " onCreate");

        Bundle bundle = savedInstanceState;
        bindWidGet();
        if (bundle != null) {
//            String temp = bundle.getString("license_plate");
//            Log.d("temp => ", temp);
//            tvMainLicensePlate.setText(temp);
//            Log.d("bundle => ", bundle.toString());
            Log.d("bundle => ", "not null");
        } else {
            Log.d("bundle => ", "null");
        }
        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        myOnClick();
        if (isNetworkAvailable()) {
            Log.d("Internet => ", "Conntected");
        } else {
            Log.d("Internet => ", "No Conncet");
        }

    }


    public void onCustomSetLicensePlate() {
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        if (strLicensePlate.equals("")) {
            strLicensePlate = getResources().getString(R.string.message_no_license_plate);
            Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
            startActivity(intent);
        }else{

        }
        tvMainLicensePlate.setText(strLicensePlate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onCustomSetLicensePlate();


        MyDbHelper myDbHelper = new MyDbHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Log.d("myDbHelper => ", myDbHelper.toString());
        Log.d("sqLiteDatabase => ", sqLiteDatabase + " ");
//        //String strPath = "/data/data/com.stecon.patipan_on.lettertracking/databases/Letter";
//        //String strPath = "/data/data/com.stecon.patipan_on.diarycar/databases/CAR";
//        //String strPath = "/data/custom_db/Letter";
//        String strPath = "/data/data/com.stecon.patipan_on.lettertracking/databases/Letter";
//        //SQLiteDatabase dbCustom = SQLiteDatabase.openDatabase(strPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        SQLiteDatabase dbCustom = SQLiteDatabase.openDatabase(strPath, null, SQLiteDatabase.OPEN_READWRITE);
//        Log.d("dbCustom => ", dbCustom + " ");
//        Cursor cursor2 = dbCustom.rawQuery("SELECT * FROM User", null);


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
        btnEng = (Button) findViewById(R.id.btnToEng);
        btnThai = (Button) findViewById(R.id.btnToThai);
    }

    private void myOnClick() {
        btnGoDiary.setOnClickListener(this);
        btnGoOil.setOnClickListener(this);
        btnGoService.setOnClickListener(this);
        btnGoExit.setOnClickListener(this);
        btnThai.setOnClickListener(this);
        btnEng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnGoDiary) {
            Intent intent = new Intent(this, TripStartActivity.class);
            startActivity(intent);

        } else if (v == btnGoOil) {
            Intent intent = new Intent(getApplicationContext(), OilListActivity.class);
            startActivity(intent);

        } else if (v == btnGoService) {
            Intent intent = new Intent(MainActivity.this, ServiceListActivity.class);
            startActivity(intent);
        } else if (v == btnGoExit) {
            onExit();
        } else if (v == btnEng) {
            customEng();
        } else if (v == btnThai) {
            customThai();
        }
    }

    private void customThai() {
        Log.d("click => ", "Thai");
        String strTh = "th";
        Locale locale = new Locale(strTh);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = new Configuration();

        Log.d("Jellybean => ", Build.VERSION_CODES.JELLY_BEAN_MR1 + " ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        }else{
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, resources.getDisplayMetrics());

        Bundle bundle = new Bundle();
        bundle.putString("license_plate", tvMainLicensePlate.getText().toString().trim());
        onDestroy();
        onCreate(null);
        onRestoreInstanceState(bundle);



    }

    private void customEng() {
        Log.d("click => ", "ENG");

        Locale locale = Locale.ENGLISH;
        Configuration config = new Configuration();
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        }else{
            config.locale = locale;
        }
        getResources().updateConfiguration(config, null);

        Bundle bundle = new Bundle();
        bundle.putString("license_plate", tvMainLicensePlate.getText().toString().trim());
        onDestroy();
        onCreate(null);
        onRestoreInstanceState(bundle);

    }

    private void onExit() {
        editor.remove(MyAppConfig.licensePlate);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("activity => ", " onSaveInstanceState");
        outState.putString("license_plate", tvMainLicensePlate.getText().toString().trim());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("activity => ", " restore");
        tvMainLicensePlate.setText(savedInstanceState.getString("license_plate"));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
