package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MySendToServer;
import com.stecon.patipan_on.diarycar.common_class.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.PinCodeStatic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CustomAlertDialog.OnMyDialogActivity {


    //private TextView tvMainLicensePlate;

    private String strLicensePlate;
    private String strLanguage;

    private Button imgVehicleJournal;
    private Button imgPriceJournal;
    //private ImageButton imgSetting;
    private Button imgChangeCar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        bindWidGet();
        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        myOnClick();

        myCustomTest();


//        if (isNetworkAvailable()) {
//            Log.d("Internet => ", "Conntected");
//        } else {
//            Log.d("Internet => ", "No Conncet");
//        }

        //onCustomSetLicensePlate();

        //myCustomConnectActivity();
        long tripId = sharedPreferences.getLong(MyAppConfig.trip_id, 0);
        if (tripId != 0) {
            Intent intent = new Intent(MainActivity.this, TripStartActivity.class);
            startActivity(intent);

        }

    }

    private void myCustomConnectActivity() {
        String pincode = PinCodeStatic.getPinNumber();
        if (pincode == null) {
            Intent intent = new Intent(MainActivity.this, PinCodeActivity.class);
            intent.putExtra(PinCodeActivity.PIN_MODE, 0);
            startActivityForResult(intent, PinCodeActivity.REQUEST_CODE);
            //startActivity(intent);
        }
    }

    private void mySetToolbar() {
        toolbar.setTitle(getResources().getString(R.string.title_main));
        toolbar.setSubtitle(strLicensePlate);
        setSupportActionBar(toolbar);
    }


    public void onCustomSetLicensePlate() {
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        Log.d("strLicensePlate => ", strLicensePlate);
        if (strLicensePlate.equals("")) {
            strLicensePlate = getResources().getString(R.string.message_no_license_plate);
            Intent intent = new Intent(MainActivity.this, LicensePlateActivity.class);
            startActivityForResult(intent, LicensePlateActivity.REQUEST_CODE);
        }
    }

    private void myCustomLanguageMainApp() {
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
        } else {
            Log.d("str_lang => ", "empty");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity => ", "onStart");
        onCustomSetLicensePlate();
        mySetToolbar();
//        Log.d("str_lang => ", strLanguage);
//        Log.d("onStart => ", "onStart");
//        if (strLanguage != "") {
//            Locale locale = new Locale(strLanguage);
//            Resources resources = getResources();
//            Configuration configuration = new Configuration();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                configuration.setLocale(locale);
//            } else {
//                configuration.locale = locale;
//            }
//            getResources().updateConfiguration(configuration, resources.getDisplayMetrics());
//            Bundle bundle = new Bundle();
//            onDestroy();
//            onCreate(null);
//        } else {
//            Log.d("str_lang => ", "empty");
//        }

        //onCheckPinFirst();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            intent.putExtra(PinCodeActivity.PIN_MODE, PinCodeActivity.pin_change);
            startActivityForResult(intent, SettingActivity.REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void onCheckPinFirst() {
        String pincode = PinCodeStatic.getPinNumber();
        if (pincode == null) {
            Intent intent = new Intent(MainActivity.this, PinCodeActivity.class);
            intent.putExtra(PinCodeActivity.PIN_MODE, 0);
            startActivityForResult(intent, PinCodeActivity.REQUEST_CODE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        Boolean status = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return status;
    }

    private void bindWidGet() {

        //tvMainLicensePlate = (TextView) findViewById(R.id.tvMainLicensePlate);
        imgVehicleJournal =  findViewById(R.id.imgVehicleJournal);
        imgPriceJournal =  findViewById(R.id.imgPriceJournal);
        imgChangeCar =  findViewById(R.id.imgChangeCar);
        //imgSetting = (ImageButton) findViewById(R.id.imgSettingMain);

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);

    }

    private void myOnClick() {
        imgVehicleJournal.setOnClickListener(this);
        imgPriceJournal.setOnClickListener(this);
        imgChangeCar.setOnClickListener(this);
        //imgSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgVehicleJournal) {
            Intent intent = new Intent(this, TripStartActivity.class);
            startActivity(intent);

        } else if (v == imgPriceJournal) {
            Intent intent = new Intent(getApplicationContext(), PriceAllActivity.class);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("activity => ", " onSaveInstanceState");
        //outState.putString("license_plate", tvMainLicensePlate.getText().toString().trim());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("activity => ", " restore");
        //tvMainLicensePlate.setText(savedInstanceState.getString("license_plate"));

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
        startActivityForResult(intent, LicensePlateActivity.REQUEST_CODE);
        //finish();
    }

    @Override
    public void onMyDialogNegative() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //resulut_ok = -1 , result_cancel = 0

        Log.d("27/2/2018 => ", "code :" + requestCode + " / resultcode :" + resultCode);
        if (requestCode == PinCodeActivity.REQUEST_CODE ) { // mode pin apply result
            if (resultCode == RESULT_OK) {
                String pinForResult = data.getStringExtra(PinCodeActivity.PIN_RESULT);
                PinCodeStatic.setPinNumber(pinForResult);

            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
        if (requestCode == LicensePlateActivity.REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                PinCodeStatic.setPinNumber(null);
                finish();
            } else if (requestCode == RESULT_OK) {
                String licensePlateForResult = data.getStringExtra(MyAppConfig.licensePlate);
                Log.d("licenseResult => ", licensePlateForResult);
            }
        }

        if (requestCode == SettingActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                myCustomLanguageMainApp();
                Log.d("activity => ", "onActivityResult");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PinCodeStatic.setPinNumber(null);

    }

}
