package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.model.MyDateModify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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

    //private String strUrl = "http://csclub.ssru.ac.th/s56122201044/csc4202/monitor/php_get_monitor.php";
    //private String strUrl = "http://128.1.10.62:8081/BenzApi/Data?Date=0311201703112017";
    private String strUrl = "http://203.146.239.40:8081/BenzApi/Data?Date=0311201703112017";


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
        strMember = edtMember.getText().toString().trim();
        if (strDateShow.equals("") || strIdCard.equals("") || strMember.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
            myTestHttp();

        }else{
            Toast.makeText(this, strIdCard + "\n" + strMember + "\n" + strDateShow, Toast.LENGTH_SHORT).show();
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
                String dataResponse = response.body().string();
                int codeResponse = response.code();
                Log.d("Response data=> ", dataResponse);
                Log.d("codeResponse => ", codeResponse +"");

                try {
//                    JSONObject jsonObject = new JSONObject(dataResponse);
//                    String product_id = jsonObject.getString("product_id");
//                    Log.d("prpduct id => ", product_id.toString());
                    final JSONArray jsonArray = new JSONArray(dataResponse);
                    final String[] iconString = new String[jsonArray.length()];
                    final String[] titleString = new String[jsonArray.length()];
                    final String[] priceString = new String[jsonArray.length()];
                    final String[] brandString = new String[jsonArray.length()];
                    final String[] sizeStrings = new String[jsonArray.length()];
                    final String[] curveStrings = new String[jsonArray.length()];
                    final String[] typeStrings = new String[jsonArray.length()];
                    final String[] detailStrings = new String[jsonArray.length()];

                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        iconString[i] = jsonObject.getString("image");
                        titleString[i] = jsonObject.getString("product_name");
                        priceString[i] = jsonObject.getString("price");
                        brandString[i] = jsonObject.getString("brand_name");
                        sizeStrings[i] = jsonObject.getString("size");
                        curveStrings[i] = jsonObject.getString("curve");
                        typeStrings[i] = jsonObject.getString("type_name");
                        detailStrings[i] = jsonObject.getString("product_detail");

                    }
                    String temp = jsonArray.getJSONObject(0).getString("product_name");
                    Log.d("temp => ", temp);

                } catch (JSONException e) {
                    Log.d("JSONException e => ", e.toString());
                }

                //Chang Json

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

    }
}
