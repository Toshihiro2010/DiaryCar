package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.stecon.patipan_on.diarycar.model.MonitorInfo;
import com.stecon.patipan_on.diarycar.model.MyDateModify;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;


public class VerifiedActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText edtMember;
    private EditText edtIdCard;
    private TextView txtDate;

    private Button btnSelectDate;
    private Button btnVerified;


    private int mDay;
    private int mMonth;
    private int mYear;

    private int tvDay , tvMonth , tvYear;

    private String strMember;
    private String strIdCard;
    private String strDateShow;

    private String strUrl = "http://csclub.ssru.ac.th/s56122201044/csc4202/monitor/php_get_monitor.php";
    //private String strUrl = "http://128.1.10.62:8081/BenzApi/Data?Date=0311201703112017";
    //private String strUrl = "http://203.146.239.40:8081/BenzApi/Data?Date=0311201703112017";
    private String strNodejs = "http://172.20.20.57:7777/checkuser";


    private MyDateModify myDateModify;
    private JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        bindWidget();
        Date date = new Date();

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        myDateModify = new MyDateModify(timeStamp);
        strDateShow = myDateModify.getStrDate();
        txtDate.setText(strDateShow);
        mySetOnclick();

    }

    private void mySetOnclick() {
        btnSelectDate.setOnClickListener(this);
        btnVerified.setOnClickListener(this);
    }


    private void bindWidget() {
        edtMember = (EditText) findViewById(R.id.edtMember);
        edtIdCard = (EditText) findViewById(R.id.edtIdCard);
        txtDate = (TextView) findViewById(R.id.txtDate);
        btnVerified = (Button) findViewById(R.id.btnVerified);
        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectDate) {
            mySetDate();
        } else if (v == btnVerified) {
            //Toast.makeText(this, "click => Verified", Toast.LENGTH_SHORT).show();
            myVertified();
        }

    }



    private void myVertified() {
        strDateShow = txtDate.getText().toString();
        strIdCard = edtIdCard.getText().toString().trim();
        strMember = edtMember.getText().toString().trim().toLowerCase();
        if (strDateShow.equals("") || strIdCard.equals("") || strMember.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
        }else{
            //พรุ่งนี้ทดสอบ Doin background นะ ไอ้เบนซ์ มิงทำ error ให้มันแสดง UI ม่ได้ จะลองใช้ Background ดุ Ok
            Boolean checkId = myCheckdate();
            if (checkId == true) {
                //Toast.makeText(this, "เลขบัตรประชาชนไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                //myTestHttp();
                SynJson synJson = new SynJson();
                synJson.execute();

                //checkDataToServer(dateToServer);
                //Log.d("sucess=> ", " ok");
                //Toast.makeText(VerifiedActivity.this, "สำเร็จ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "เลขบัตรประชาชนไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void checkDataToServer(String strDate) {
        String myUrl = strNodejs + "/" + strMember + "/" + strIdCard + "/" + strDate;
        Request request = new Request.Builder()
                .get()
                .url(myUrl)
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Check =>", "onFailed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("Check => ", "onResponse");
                int code = response.code();
                String dataResponse = response.body().string();
                Log.d("code => ", code +"");


                if(code == 200){
                    //CheckStatus checkStatus = new Gson().fromJson(dataResponse, CheckStatus.class);
                    Log.d("status => ", "ok");
                    //Toast.makeText(VerifiedActivity.this, "สำเร็จ", Toast.LENGTH_SHORT).show();
                    //myIntent();
                }else{
                    Log.d("content => " , "No data");

                }
            }

        });

    }



    private Boolean myCheckdate() {

        if (strIdCard.length() != 13) {
            Log.d("check = > ", strIdCard.length() + "");
            return false;
        }
        int check = Integer.parseInt(strIdCard.substring(12));
        int sum = 0;
        int myMultiple = 13;
        for (int i = 0 ; i <12; i++) {
            int c = Integer.parseInt(strIdCard.substring(i, i + 1)) * myMultiple;
            sum = sum + c;
            myMultiple--;
        }
        int result = sum%11;
        result = 11 - result;
        if (result % 10 == check) {
            return true;
        } else {
            return false;
        }

    }

    private void myTestHttp() {

        Request request = new Request.Builder()
                .get()
                .url(strUrl)
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Check =>", "onFailed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Check => ", "onResponse");
                Log.d("data => ", response.body().charStream().toString());

                String dataResponse = response.body().string();

                JsonArray jsonArray = (JsonArray) new JsonParser().parse(dataResponse);
                int json_length = jsonArray.size();

               for(int i = 0 ; i < json_length ; i++) {
                   //Log.d("test=> ", jsonArray.get(i) + "");
                   MonitorInfo monitorInfo = new Gson().fromJson(jsonArray.get(i), MonitorInfo.class);
                   Log.d("test=> ", monitorInfo.getProductName());

               }
            }
        });
    }


    private void mySetDate() {
        //String strDate = txtDate.getText().toString();
        strDateShow = txtDate.getText().toString();

        myDateModify = new MyDateModify(strDateShow);
        mDay = myDateModify.getDay();
        mMonth = myDateModify.getMonth();
        mYear = myDateModify.getYear();

        DatePickerDialog datePickerDialog = new DatePickerDialog(VerifiedActivity.this , VerifiedActivity.this , mYear, mMonth-1, mDay);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tvDay = year;
        tvMonth = month+1;
        tvYear = dayOfMonth;

        txtDate.setText(tvYear + "/" + tvMonth + "/" + tvDay);
        //txtDate.setText(strDateShow);

        //hello sword

    }

    private class SynJson extends AsyncTask<Object, Object, Response> {
        @Override
        protected Response doInBackground(Object... params) {

            String strDate = myDateModify.getStrDateToServer(strDateShow);
            String myUrl = strNodejs + "/" + strMember + "/" + strIdCard + "/" + strDate;
            Request request = new Request.Builder()
                    .get()
                    .url(myUrl)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder().build();

            try {
                Response response = client.newCall(request).execute();
                //Log.d("response => ", response.body().toString());
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("response error => ", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Response s) {
            super.onPostExecute(s);
            if (s.code() != 200) {
                Toast.makeText(VerifiedActivity.this, "ข้อมูลไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(VerifiedActivity.this, "ข้อมูลถูกต้อง", Toast.LENGTH_SHORT).show();
            }
            

        }
    }
}
