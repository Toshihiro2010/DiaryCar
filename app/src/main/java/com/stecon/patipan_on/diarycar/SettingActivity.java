package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.model.MyAppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEng;
    private Button btnThai;
    private ImageButton imgChangePin;

    private RadioGroup radioLanguageGroup;
    private RadioButton radioEng;
    private RadioButton radioThai;

    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String strLanguage;

    private String strEng = "EN";
    private String strThai = "TH";

    public static final int REQUEST_CODE = 301;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_new);
        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        bindWidGet();
        setOnClick();
        toolbar.setTitle(getResources().getString(R.string.title_setting));
        setSupportActionBar(toolbar);

//        SyncData syncData = new SyncData();
//        syncData.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRadioChecked();
    }

    private void setRadioChecked() {
        strLanguage = sharedPreferences.getString(MyAppConfig.language_app, "");
        if (strLanguage.equals(strEng)) {
            radioEng.setChecked(true);
        } else if (strLanguage.equals(strThai)) {
            radioThai.setChecked(true);
        } else if (strLanguage.equals("")) {

        }
    }

    private void setOnClick() {
        btnEng.setOnClickListener(this);
        btnThai.setOnClickListener(this);
        radioEng.setOnClickListener(this);
        radioThai.setOnClickListener(this);
        imgChangePin.setOnClickListener(this);


    }

    private void bindWidGet() {
        btnEng = (Button) findViewById(R.id.btnToEng);
        btnThai = (Button) findViewById(R.id.btnToThai);
        radioLanguageGroup = (RadioGroup) findViewById(R.id.radioLanguageGroup);
        radioEng = (RadioButton) findViewById(R.id.radioEng);
        radioThai = (RadioButton) findViewById(R.id.radioThai);
        imgChangePin = (ImageButton) findViewById(R.id.imgChangePin);
        toolbar = (Toolbar) findViewById(R.id.toolbarSetting);
    }

    @Override
    public void onClick(View v) {
        if (v == btnEng || v == radioEng) {
            customEng();
            radioEng.setChecked(true);
        } else if (v == btnThai || v == radioThai) {
            customThai();
            radioThai.setChecked(true);
        } else if (v == imgChangePin) {
            Intent intent = new Intent(SettingActivity.this, PinCodeActivity.class);
            intent.putExtra(PinCodeActivity.PIN_MODE, 0);
            startActivity(intent);

        }
    }

    private class SyncData extends AsyncTask<Void,String,String> {
        @Override
        protected String doInBackground(Void... params) {
            String myUrl = "http://sthq50.stecon.co.th/SinoWS/SinoWebService.asmx/GetEmployeeByPosition?positionNo=5520";
            //String myUrl = "http://csclub.ssru.ac.th/s56122201044/csc4202/monitor/php_get_monitor.php";
            //String myUrl = "http://www.akexorcist.com/2015/08/smart-location-the-powerful-location-service-library-for-android.html";
            Request request = new Request.Builder()
                    .get()
                    .url(myUrl)
                    .build();


            OkHttpClient client = new OkHttpClient.Builder().build();
            String result = "";


            try {
                Response response = client.newCall(request).execute();
                //ResponseBody responseBody = response.body();


                Log.d("response => ", response.body().byteStream() + " ");
                InputStream inputStream = response.body().byteStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result = result + line;
                }
                Log.d("resultLine => ", result);


                //String xmlData = response.body().string();
//                Log.d("xmldata => ", xmlData);

                return "";
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("IOException error => ", e.toString());

            }catch (Exception e){
                e.printStackTrace();
                Log.d("Exception => ", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("s = > ", s.toString());

        }
    }

    private void customThai() {
        Log.d("click => ", "Thai");
        Locale locale = new Locale(strThai);
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

        onDestroy();
        onCreate(null);

        editor.putString(MyAppConfig.language_app, strThai);
        editor.commit();



    }

    private void customEng() {
        Log.d("click => ", "ENG");

        Locale locale = Locale.ENGLISH;
        //Locale locale = new Locale("EN");
        Configuration config = new Configuration();
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        }else{
            config.locale = locale;
        }
        getResources().updateConfiguration(config, null);
        onDestroy();
        onCreate(null);
        editor.putString(MyAppConfig.language_app, strEng);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
