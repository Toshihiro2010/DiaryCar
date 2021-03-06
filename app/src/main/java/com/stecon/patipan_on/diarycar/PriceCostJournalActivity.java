package com.stecon.patipan_on.diarycar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.StatusCheckServer;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.DatabasePriceCost;
import com.stecon.patipan_on.diarycar.common_class.MyAppConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PriceCostJournalActivity extends AppCompatActivity implements View.OnClickListener, CustomAlertDialog.OnMyDialogActivity {

    private Spinner spinnerPriceType;
    private EditText edtTitle;
    private EditText edtPrice;
    private EditText edtNoteDetail;
    private Button btnSavePriceOther;

    private String strLicensePlate;
    private String strPriceType;
    private String strTitle;
    private String strPrice;
    private String strNote;



    private Double priceADouble;
    ArrayAdapter<CharSequence> priceTypeArrayAdapter;

    MyDbHelper myDbHelper;
    SQLiteDatabase sqLiteDatabase;

    private ProgressDialog progressDialog;
    private CustomAlertDialog customAlertDialog;

    private int data_id;
    private int mode = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_cost_journal);

        bindWidget();
        Bundle bundle = getIntent().getExtras();
        myDbHelper = new MyDbHelper(PriceCostJournalActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        if (bundle != null) {
            data_id = bundle.getInt("data_id");
        }

        if (data_id != 0) {
            Log.d("id = > ", data_id + " ");
            mode = 2;
            mySetQueryText();
        } else {
            Log.d("id = > ", "No id");
            mySetSpinner();
        }
        btnSavePriceOther.setOnClickListener(this);
    }

    private void mySetQueryText() {
        String strSql = "SELECT * FROM " + DatabasePriceCost.TABLE_NAME + " WHERE " + DatabaseOilJournal.COL_ID + " = " + data_id;
        Log.d("STRSQL = > ", strSql);
        Cursor cursor = sqLiteDatabase.rawQuery(strSql , null);
        Log.d("cursor = > ", cursor.getCount() + " ");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            strPriceType = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_PRICE_TYPE));
            strTitle = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_PRICE_TITLE));
            priceADouble = cursor.getDouble(cursor.getColumnIndex(DatabasePriceCost.COL_PRICE_MONEY));
            strNote = cursor.getString(cursor.getColumnIndex(DatabasePriceCost.COL_NOTE));


            if (priceADouble != null) {
                strPrice = String.valueOf(priceADouble);
            }


            edtTitle.setText(strTitle);
            edtPrice.setText(strPrice);
            edtNoteDetail.setText(strNote);

            priceTypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.price_other_array, R.layout.custom_spinner_tv2);
            spinnerPriceType.setAdapter(priceTypeArrayAdapter);

            if (!strPriceType.equals(null)) {
                int spinnerPosition = priceTypeArrayAdapter.getPosition(strPriceType);
                spinnerPriceType.setSelection(spinnerPosition);
            } else {
                spinnerPriceType.setAdapter(priceTypeArrayAdapter);
            }

        }
    }

    private void mySetSpinner() {

        priceTypeArrayAdapter = ArrayAdapter.createFromResource(PriceCostJournalActivity.this, R.array.price_other_array, R.layout.custom_spinner_tv2);
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
            strPriceType = spinnerPriceType.getSelectedItem().toString();
            strTitle = edtTitle.getText().toString().trim();
            strNote = edtNoteDetail.getText().toString().trim();

            if (strPrice.equals("") || strNote.equals("")) {
                Toast.makeText(this, getResources().getString(R.string.message_please_input_data), Toast.LENGTH_SHORT).show();
            } else {
                progressDialog = new ProgressDialog(PriceCostJournalActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle(getResources().getString(R.string.message_save_data_title));
                progressDialog.setMessage(getResources().getString(R.string.loading));
                progressDialog.show();

                if (mode == 0) {
                    onSaveData();
                } else if (mode == 2) {
                    onEditData();
                }
            }
        }
    }

    private void onEditData() {
        priceADouble = Double.valueOf(strPrice);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabasePriceCost.COL_LICENSE_PLATE, strLicensePlate);
        contentValues.put(DatabasePriceCost.COL_PRICE_TYPE, strPriceType);
        contentValues.put(DatabasePriceCost.COL_PRICE_TITLE, strTitle);
        contentValues.put(DatabasePriceCost.COL_PRICE_MONEY, priceADouble);
        contentValues.put(DatabasePriceCost.COL_NOTE, strNote);
        contentValues.put(DatabasePriceCost.COL_DATE_UPDATE, dateFormat.format(date));

        sqLiteDatabase.update(DatabasePriceCost.TABLE_NAME, contentValues, DatabasePriceCost.COL_ID + " = ? ", new String[]{String.valueOf(data_id)});
        mySetEmptyText();
        progressDialog.dismiss();
        finish();

    }

    private void onSaveData() {
        priceADouble = Double.valueOf(strPrice);

        StatusCheckServer statusCheckServer = new StatusCheckServer(PriceCostJournalActivity.this);
        statusCheckServer.setOnMyListener(new StatusCheckServer.MyOnListener() {
            @Override
            public void onInsertListener() {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabasePriceCost.COL_LICENSE_PLATE, strLicensePlate);
                contentValues.put(DatabasePriceCost.COL_PRICE_TYPE, strPriceType);
                contentValues.put(DatabasePriceCost.COL_PRICE_TITLE, strTitle);
                contentValues.put(DatabasePriceCost.COL_PRICE_MONEY, priceADouble);
                contentValues.put(DatabasePriceCost.COL_NOTE, strNote);

                sqLiteDatabase.insert(DatabasePriceCost.TABLE_NAME, null, contentValues);
                mySetEmptyText();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onUpdateListener() {

            }
        });
        statusCheckServer.checkInsert();



    }

    private void mySetEmptyText() {
        edtTitle.setText("");
        edtPrice.setText("");
        edtNoteDetail.setText("");
        mySetSpinner();
    }

    @Override
    public void onMyDialogPositive() {
        //Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PriceCostJournalActivity.this, TripStartActivity.class);
        intent.putExtra(MyAppConfig.activity_code, 99);
        startActivity(intent);

    }

    @Override
    public void onMyDialogNegative() {
        //Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OnActivity => ", "OnStart");
        onMyCheckLicensePlate();

    }

    private void onMyCheckLicensePlate() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        strLicensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        if (strLicensePlate.equals("")) {
            customAlertDialog = new CustomAlertDialog(PriceCostJournalActivity.this, getResources().getString(R.string.message_no_license_plate), getResources().getString(R.string.message_add_trip));
            customAlertDialog.myDefaultDialog();
            customAlertDialog.setOnMyDialogActivity(PriceCostJournalActivity.this);
            customAlertDialog.show();
        }
    }
}
