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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.MyLocationFirst;
import com.stecon.patipan_on.diarycar.controller.MySendToServer;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.PinCodeStatic;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyLocationFirst.OnNextLocationFunction, CustomAlertDialog.OnMyDialogActivity {

    private Button btnGoDiary;
    private Button btnGoOil;
    private Button btnGoService;
    private TextView tvMainLicensePlate;
    private Button btnGoExit;
    private Button btnEng;
    private Button btnThai;


    private String strLicensePlate;
    private String strLanguage;

    private ImageButton imgVehicleJournal;
    private ImageButton imgPriceJournal;
    private ImageButton imgSetting;
    private ImageButton imgChangeCar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("MainMenu");
//
//        setSupportActionBar(toolbar);


        Bundle bundle = savedInstanceState;
        bindWidGet();

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
        Log.d("strLicensePlate => ", strLicensePlate);
        if (strLicensePlate.equals("")) {
            strLicensePlate = getResources().getString(R.string.message_no_license_plate);
            Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
            startActivity(intent);
        }else{

        }
//        toolbar.setSubtitle(strLicensePlate);
        tvMainLicensePlate.setText(strLicensePlate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        strLanguage = sharedPreferences.getString(MyAppConfig.language_app, "");
        Log.d("str_lang => ", strLanguage);
        Log.d("onStart => ", "onStart");
        if (strLanguage != "") {
            Locale locale = new Locale(strLanguage);
            Resources resources = getResources();
            Configuration configuration = new Configuration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }
            getResources().updateConfiguration(configuration, resources.getDisplayMetrics());
            Bundle bundle = new Bundle();
            onDestroy();
            onCreate(null);
//            onSaveInstanceState(bundle);
//            onRestoreInstanceState(bundle);
        } else {
            Log.d("str_lang => ", "empty");
        }

        onCustomSetLicensePlate();



        MyDbHelper myDbHelper = new MyDbHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Log.d("myDbHelper => ", myDbHelper.toString());
        Log.d("sqLiteDatabase => ", sqLiteDatabase + " ");

        //onCheckPinFirst();

    }

    private void onCheckPinFirst() {
        String pincode = PinCodeStatic.getPinNumber();
        if (pincode == null) {
            Intent intent = new Intent(MainActivity.this, PinCodeActivity.class);
            intent.putExtra(PinCodeActivity.PIN_MODE, 0);
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

        tvMainLicensePlate = (TextView) findViewById(R.id.tvMainLicensePlate);

        imgVehicleJournal = (ImageButton) findViewById(R.id.imgVehicleJournal);
        imgPriceJournal = (ImageButton) findViewById(R.id.imgPriceJournal);
        imgChangeCar = (ImageButton) findViewById(R.id.imgChangeCar);
        imgSetting = (ImageButton) findViewById(R.id.imgSettingMain);
    }

    private void myOnClick() {
        imgVehicleJournal.setOnClickListener(this);
        imgPriceJournal.setOnClickListener(this);
        imgChangeCar.setOnClickListener(this);
        imgSetting.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == imgVehicleJournal) {
            Intent intent = new Intent(this, TripStartActivity.class);
            startActivity(intent);

        } else if (v == imgPriceJournal) {
            //Intent intent = new Intent(getApplicationContext(), PriceAllActivity.class);
            Intent intent = new Intent(getApplicationContext(), PriceAllActivity.class);
            startActivity(intent);

        } else if (v == imgSetting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (v == imgChangeCar) {
            onExit();
        }
    }



    private void onExit() {
        String titleChangCar = this.getResources().getString(R.string.message_title_change_car);
        String messageChangCar = this.getResources().getString(R.string.message_change_car);


        CustomAlertDialog customAlertDialog = new CustomAlertDialog(MainActivity.this, titleChangCar, messageChangCar);
        customAlertDialog.myDefaultDialog();
        customAlertDialog.setOnMyDialogActivity(this);
        customAlertDialog.show();
    }

    private void myCustomTest() {
        MySendToServer mySendToServer = new MySendToServer(MainActivity.this);
        mySendToServer.syncToServer();

    }

    @Override
    public void onStartNextFunction() {

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

    @Override
    public void onMyDialogPositive() {
        editor.remove(MyAppConfig.licensePlate);
        editor.commit();
        Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMyDialogNegative() {

    }
}
