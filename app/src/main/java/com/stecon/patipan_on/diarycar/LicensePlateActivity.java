package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stecon.patipan_on.diarycar.model.CarModel;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.Recordset_;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LicensePlateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLicensePlate;
    private Button btnGoMain;

    private String strLicensePlate;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final int REQUEST_CODE = 201;

    private String strUrl = "http://172.20.20.57:888/getLicenseNo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licen_plate);

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");

        if (!strLicensePlate.equals("")) {
            Intent intent = new Intent(LicensePlateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        binWidGet();
        btnGoMain.setOnClickListener(this);

    }

    private void binWidGet() {
        edtLicensePlate =  findViewById(R.id.edtLicensePlate);
        btnGoMain =  findViewById(R.id.btnGoMain);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGoMain){
            strLicensePlate = edtLicensePlate.getText().toString().trim();
            if (strLicensePlate.equals("")) {
                Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
            } else {
                checkLicensePlate();

            }
        }
    }

    private void checkLicensePlate() {
        SyncCheckLicensePlate syncCheckLicensePlate = new SyncCheckLicensePlate();
        syncCheckLicensePlate.execute();

//        mySharePreferences();
//        Intent intent = new Intent();
//        intent.putExtra(MyAppConfig.licensePlate, strLicensePlate);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    private void mySharePreferences() {
        editor.putString(MyAppConfig.licensePlate, strLicensePlate);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();

    }

    private class SyncCheckLicensePlate extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            String url = strUrl + "/ษณ-4792";

            Request request = new Request.Builder().get().url(url).build();
            OkHttpClient client = new OkHttpClient.Builder().build();

            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return response.body().string();
                }
            } catch (IOException e) {

                Log.d("IOException => ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.d("s => ", s);
                Gson gson = new Gson();
                CarModel carModel = gson.fromJson(s, CarModel.class);
                Recordset_ recordset = carModel.getRecordset().get(0);
                Log.d("model_id => ", recordset.getModelID() + "");
                Log.d("getCarTypeID => ", recordset.getCarTypeID() + "");
                Log.d("getRemark => ", recordset.getRemark() + "");
                //        mySharePreferences();
            Intent intent = new Intent();
            intent.putExtra(MyAppConfig.licensePlate, strLicensePlate);
            setResult(RESULT_OK, intent);
            finish();
            }

        }
    }
}
