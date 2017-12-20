package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseTripCost;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

public class PriceOtherActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerPriceType;
    private EditText edtTitle;
    private EditText edtPrice;
    private EditText edtNoteDetail;
    private Button btnSavePriceOther;


    private long trip_id;
    private String strPriceType;
    private String strTitle;
    private String strPrice;
    private String strNote;

    private Double priceADouble;
    ArrayAdapter<CharSequence> priceTypeArrayAdapter;

    MyDbHelper myDbHelper;
    SQLiteDatabase sqLiteDatabase;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_other);

        SharedPreferences sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        trip_id = sharedPreferences.getLong(MyAppConfig.trip_id, 0);
        Log.d("trip_id => ", trip_id + "");
        myDbHelper = new MyDbHelper(PriceOtherActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();


        bindWidget();
        mySetSpinner();
        btnSavePriceOther.setOnClickListener(this);
    }

    private void mySetSpinner() {

        priceTypeArrayAdapter = ArrayAdapter.createFromResource(PriceOtherActivity.this, R.array.price_other_array, R.layout.support_simple_spinner_dropdown_item);
        spinnerPriceType.setAdapter(priceTypeArrayAdapter);

    }

    private void bindWidget() {
        spinnerPriceType = (Spinner) findViewById(R.id.spinnerPriceType);
        edtTitle = (EditText) findViewById(R.id.edtTitlePrice);
        edtPrice = (EditText) findViewById(R.id.edtPriceOther);
        edtNoteDetail = (EditText) findViewById(R.id.edtOtherNote);
        btnSavePriceOther = (Button) findViewById(R.id.btnSavePriceOther);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSavePriceOther) {
            strPrice = edtPrice.getText().toString().trim();
            if (strPrice.equals("")) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
                mySetEmptyText();
            }else{
                progressDialog = new ProgressDialog(PriceOtherActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Save Data");
                progressDialog.setMessage("Loading......");
                progressDialog.show();
                onSaveData();
            }
        }
    }

    private void onSaveData() {
        strPriceType = spinnerPriceType.getSelectedItem().toString();
        strTitle = edtTitle.getText().toString().trim();
        priceADouble = Double.valueOf(strPrice);
        strNote = edtNoteDetail.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseTripCost.COL_TRIP_ID, trip_id);
        contentValues.put(DatabaseTripCost.COL_PRICE_TYPE, strPriceType);
        contentValues.put(DatabaseTripCost.COL_PRICE_TITLE, strTitle);
        contentValues.put(DatabaseTripCost.COL_PRICE_MONEY, priceADouble);
        contentValues.put(DatabaseTripCost.COL_NOTE, strNote);

        sqLiteDatabase.insert(DatabaseTripCost.TABLE_NAME, null, contentValues);
        Toast.makeText(this, "insert Sucess", Toast.LENGTH_SHORT).show();
        mySetEmptyText();
        progressDialog.dismiss();



    }

    private void mySetEmptyText() {
        edtTitle.setText("");
        edtPrice.setText("");
        edtNoteDetail.setText("");
        mySetSpinner();

    }
}
