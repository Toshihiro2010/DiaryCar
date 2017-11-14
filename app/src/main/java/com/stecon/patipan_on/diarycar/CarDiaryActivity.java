package com.stecon.patipan_on.diarycar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.DatabaseCarDiary;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;

public class CarDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layKmOut;

    private EditText edtCarTitle;
    private EditText edtCarDetail;
    private EditText edtKm;

    private Button btnSaveCar;
    private Button btnListCar;

    private String strCarTitle;
    private String strCarDetail;
    private String strKm;


    MyDbHelper myDbHelper;
    DatabaseCarDiary mdbCarDiary;
    SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_diary);
        binWidGet();
        myConnect();
        myBtnClick();




    }

    private void myConnect() {
        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    private void myBtnClick() {
        btnSaveCar.setOnClickListener(this);
        btnListCar.setOnClickListener(this);
    }

    private void binWidGet() {
        edtCarTitle = (EditText) findViewById(R.id.edtCarTitle);
        edtCarDetail = (EditText) findViewById(R.id.edtCarDetail);
        edtKm = (EditText) findViewById(R.id.edtKmIn);
        btnSaveCar = (Button) findViewById(R.id.btnSaveCar);
        btnListCar = (Button) findViewById(R.id.btnListCar);
        layKmOut = (LinearLayout) findViewById(R.id.layKmOut);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSaveCar) {
            myGetText();
            mySave();

        } else if (v == btnListCar) {
            Intent intent = new Intent(this, CarListActivity.class);
            startActivity(intent);

        }
    }

    private void myGetText() {
        strCarTitle = edtCarTitle.getText().toString().trim();
        strCarDetail = edtCarDetail.getText().toString().trim();
        strKm = edtKm.getText().toString().trim();
    }

    private void mySave() {
        if (strCarTitle.equals("") || strKm.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลที่จำเป็น *", Toast.LENGTH_SHORT).show();
        } else {
            String strSQL = "INSERT INTO " + mdbCarDiary.TABLE_NAME + " ("
                    + mdbCarDiary.COL_DATE + ","
                    + mdbCarDiary.COL_TITLE + ","
                    + mdbCarDiary.COL_DETAIL + ","
                    + mdbCarDiary.COL_KILOMETER + ") VALUES (datetime(),'"
                    + strCarTitle + "',' "
                    + strCarDetail + "','"
                    + strKm + "');";
            Log.d("test => ", strSQL);
            sqLiteDatabase.execSQL(strSQL);
            mySettext();


        }
    }

    private void mySettext() {
        edtCarTitle.setText("");
        edtCarDetail.setText("");
        edtKm.setText("");
    }
}
