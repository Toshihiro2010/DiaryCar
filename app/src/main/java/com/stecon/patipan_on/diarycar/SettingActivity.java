package com.stecon.patipan_on.diarycar;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bindWidGet();
        setOnClick();

//        SyncData syncData = new SyncData();
//        syncData.execute();
    }

    private void setOnClick() {
        btnEng.setOnClickListener(this);
        btnThai.setOnClickListener(this);
    }

    private void bindWidGet() {
        btnEng = (Button) findViewById(R.id.btnToEng);
        btnThai = (Button) findViewById(R.id.btnToThai);
    }

    @Override
    public void onClick(View v) {
        if (v == btnEng) {
            customEng();
        } else if (v == btnThai) {
            customThai();
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
        String strTh = "TH";
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
        //bundle.putString("license_plate", tvMainLicensePlate.getText().toString().trim());
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
        //bundle.putString("license_plate", tvMainLicensePlate.getText().toString().trim());
        onDestroy();
        onCreate(null);
        onRestoreInstanceState(bundle);

    }
}
