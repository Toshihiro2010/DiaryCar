package com.stecon.patipan_on.diarycar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;

public class OilDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtMoneyAmount;
    private EditText edtLitAmount;
    private Button btnSaveOil;
    private Button btnListOil;

    private String strMoney;
    private String strLit;


    MyDbHelper myDbHelper;
    DatabaseOilJournal mDbOilDiary;
    SQLiteDatabase sqLiteDatabase;
    private Boolean status = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_diary);

        bindWidGet();
        myConnectDatabase();
        myBtnClick();
    }

    private void myBtnClick() {
        btnSaveOil.setOnClickListener(this);
        btnListOil.setOnClickListener(this);
    }

    private void mySave() {
        String strSQL = "INSERT INTO " + mDbOilDiary.TABLE_NAME + " ("
                + mDbOilDiary.COL_TRANSACTION_DATE + ","
                + mDbOilDiary.COL_VOLUME + ","
                + mDbOilDiary.COL_TOTAL_PRICE
                + ") VALUES (datetime(),'"
                + strLit + "','" +
                strMoney + "');";
        sqLiteDatabase.execSQL(strSQL);
        mySetText();
    }

    private void mySetText() {
        edtLitAmount.setText("");
        edtMoneyAmount.setText("");
    }

    private void myConnectDatabase() {

        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
    }

    private void myGetText() {
        strLit = edtLitAmount.getText().toString().trim();
        strMoney = edtMoneyAmount.getText().toString().trim();

    }

    private void bindWidGet() {
        edtLitAmount = (EditText) findViewById(R.id.edtLitAmount);
        edtMoneyAmount = (EditText) findViewById(R.id.edtMoneyAmount);
        btnSaveOil = (Button) findViewById(R.id.btnSaveOil);
        btnListOil = (Button) findViewById(R.id.btnListOil);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSaveOil) {
            myGetText();
            //myPlay();
            if(strLit.equals("") || strMoney.equals("")){
                Toast.makeText(OilDiaryActivity.this, "กรอกข้อมูลให้ครบสิไอ้โง่", Toast.LENGTH_SHORT).show();
            }else{
                mySave();
            }
        } else if (v == btnListOil) {
            Intent intent = new Intent(OilDiaryActivity.this, OilListActivity.class);
            startActivity(intent);
        }
    }

    private void myPlay() {
        if (!status) {
            btnListOil.setVisibility(View.INVISIBLE);
            status = true;
        } else {
            btnListOil.setVisibility(View.VISIBLE);
            status = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myDbHelper.close();
        sqLiteDatabase.close();
    }
}
