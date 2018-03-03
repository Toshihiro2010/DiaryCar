package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseUser;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.MyDateModify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;
import com.stecon.patipan_on.diarycar.model.MyDateTimeModify;
import com.stecon.patipan_on.diarycar.model.PinCodeStatic;
import com.stecon.patipan_on.diarycar.model.UserByEmpNoModel;


public class VerifiedActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText edtMemberNumber;
    private EditText edtIdCard;
    private TextView txtDate;

    private Button btnVerified;
    private ImageButton imgBtnSelectDate;

    private int mDay;
    private int mMonth;
    private int mYear;

    private int tvDay , tvMonth , tvYear;

    private String strMemberID;
    private String strIdCard;
    private String strDateShow;

    //private String strUrl = "http://csclub.ssru.ac.th/s56122201044/csc4202/monitor/php_get_monitor.php";
    //private String strUrl = "http://128.1.10.62:8081/BenzApi/Data?Date=0311201703112017";
    //private String strUrl = "http://203.146.239.40:8081/BenzApi/Data?Date=0311201703112017";
    private String strNodejs = "http://172.20.20.57:7777/checkuser";
    private String strUrl = "http://sthq50.stecon.co.th/SinoWS/SinoWebService.asmx/GetEmployee";


    private MyDateModify myDateModify;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String strEmployeeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        bindWidget();
        Date date = new Date();
        //myTestHttp();

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
        myDateModify = new MyDateModify(timeStamp);
        strDateShow = myDateModify.getStrDate();
        txtDate.setText(strDateShow);
        mySetOnclick();
        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    private void mySetOnclick() {
        imgBtnSelectDate.setOnClickListener(this);
        btnVerified.setOnClickListener(this);
    }


    private void bindWidget() {
        edtMemberNumber =  findViewById(R.id.edtMember);
        edtIdCard =  findViewById(R.id.edtIdCard);
        txtDate =  findViewById(R.id.txtDate);
        btnVerified =  findViewById(R.id.btnVerified);
        imgBtnSelectDate = findViewById(R.id.imgBtnVerifiedDate);

    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnSelectDate) {
            mySetDate();
        } else if (v == btnVerified) {
            //Toast.makeText(this, "click => Verified", Toast.LENGTH_SHORT).show();
            myVertified();
        }

    }


    private void myVertified() {
        strDateShow = txtDate.getText().toString();
        strIdCard = edtIdCard.getText().toString().trim();
        strMemberID = edtMemberNumber.getText().toString().trim().toUpperCase();
        if (strDateShow.equals("") || strIdCard.equals("") || strMemberID.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
        }else{
            //พรุ่งนี้ทดสอบ Doin background นะ ไอ้เบนซ์ มิงทำ error ให้มันแสดง UI ม่ได้ จะลองใช้ Background ดุ Ok
            Boolean checkId = myCheckdate();
            if (checkId == true) {
                //myTestHttp();
                SynUser synUser = new SynUser();
                synUser.execute();
            } else {
                Toast.makeText(this, getResources().getString(R.string.message_id_card_wrong), Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void checkDataToServer(String strDate) {
//        String myUrl = strNodejs + "/" + strMemberID + "/" + strIdCard + "/" + strDate;
//        Request request = new Request.Builder()
//                .get()
//                .url(myUrl)
//                .build();
//
//        OkHttpClient client = new OkHttpClient.Builder().build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("Check =>", "onFailed");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                Log.d("Check => ", "onResponse");
//                int code = response.code();
//                String dataResponse = response.body().string();
//                Log.d("code => ", code +"");
//
//
//                if(code == 200){
//                    //CheckStatus checkStatus = new Gson().fromJson(dataResponse, CheckStatus.class);
//                    Log.d("status => ", "ok");
//                    //Toast.makeText(VerifiedActivity.this, "สำเร็จ", Toast.LENGTH_SHORT).show();
//                    //myIntent();
//                }else{
//                    Log.d("content => " , "No data");
//
//                }
//            }
//
//        });
//    }


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
    }

    private class SynUser extends AsyncTask<String, Object, String> {
        @Override
        protected String doInBackground(String... params) {

            String strDate = myDateModify.getStrDateToServer(strDateShow);
            //String myUrl = strNodejs + "/" + strMemberID + "/" + strIdCard + "/" + strDate;
            String myUrl = strUrl + "?empno=" + strMemberID;

            Request request = new Request.Builder()
                    .get()
                    .url(myUrl)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder().build();


            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return response.body().string();
                }else{
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("IOExeption => ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Gson gson = new Gson();
                XmlToJson xmlToJson = new XmlToJson.Builder(s).build();
                UserByEmpNoModel userByEmpNoModel = gson.fromJson(xmlToJson.toString(), UserByEmpNoModel.class);

                String idCardFromWebService = userByEmpNoModel.getUserFromWebServiceModel().getiDCard();
                String birthDateFromWebService = userByEmpNoModel.getUserFromWebServiceModel().getBirthDate();
                String positionCode = userByEmpNoModel.getUserFromWebServiceModel().getPositionCode();

                String birthDate[] = MyDateTimeModify.getStrDateTimeFromSqlite(birthDateFromWebService);
                String dateCustomModify = MyDateTimeModify.getCustomMonthDateYear(birthDate[0]);
                //Log.d("strDateShow => ", strDateShow);


                if (strIdCard.equals(idCardFromWebService) && strDateShow.equals(dateCustomModify)) {
                    onStartPinActivity();
                } else {
                    Toast.makeText(VerifiedActivity.this, "Data ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(VerifiedActivity.this, "ไม่สามารถติดต่อ Server ได้", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void onUserSave(UserByEmpNoModel userByEmpNoModel) {
        MyDbHelper myDbHelper = new MyDbHelper(VerifiedActivity.this);
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUser.COL_EMPLOYEE_ID, userByEmpNoModel.getUserFromWebServiceModel().getEmpNo());
        contentValues.put(DatabaseUser.COL_EMPLOYEE_NAME, userByEmpNoModel.getUserFromWebServiceModel().getDisplayName());

        sqLiteDatabase.insert(DatabaseUser.TABLE_NAME, null, contentValues);

        editor.putString(MyAppConfig.employee_id, userByEmpNoModel.getUserFromWebServiceModel().getEmpNo());
        editor.commit();
        onStartPinActivity();
    }

    private void onStartPinActivity() {
        Intent intent = new Intent(VerifiedActivity.this, PinCodeActivity.class);
        intent.putExtra(PinCodeActivity.PIN_MODE, PinCodeActivity.pin_add);
        intent.putExtra(MyAppConfig.employee_id, strMemberID);
        startActivityForResult(intent, PinCodeActivity.REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PinCodeActivity.REQUEST_CODE || resultCode == RESULT_OK) {
            String pinForResult = data.getStringExtra(PinCodeActivity.PIN_RESULT);
            PinCodeStatic.setPinNumber(pinForResult);

            MyDbHelper myDbHelper = new MyDbHelper(VerifiedActivity.this);
            SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseUser.COL_EMPLOYEE_ID, strMemberID);
            contentValues.put(DatabaseUser.COL_EMPLOYEE_NAME, strEmployeeName);
            contentValues.put(DatabaseUser.COL_PIN_CODE, pinForResult);
            contentValues.put(DatabaseUser.COL_STATUS, 1);
            sqLiteDatabase.insert(DatabaseUser.TABLE_NAME, null, contentValues);
            editor.putString(MyAppConfig.employee_id, strMemberID);
            editor.commit();
            Intent intent = new Intent(VerifiedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }


        //sqLiteDatabase.update(DatabaseUser.TABLE_NAME, contentValues, DatabaseUser.COL_EMPLOYEE_ID + " = ? ", new String[]{strEmployeeNumber});
    }
}
