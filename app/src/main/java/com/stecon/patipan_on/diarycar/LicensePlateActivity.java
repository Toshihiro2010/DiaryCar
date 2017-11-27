package com.stecon.patipan_on.diarycar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseVehicleApply;

public class LicensePlateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLicensePlate;
    private Button btnGoMain;

    private String strLicensePlate;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licen_plate);

        myDbHelper = new MyDbHelper(this);
        database = myDbHelper.getWritableDatabase();
        binWidGet();
        btnGoMain.setOnClickListener(this);

    }

    private void binWidGet() {
        edtLicensePlate = (EditText) findViewById(R.id.edtLicensePlate);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGoMain){
            strLicensePlate = edtLicensePlate.getText().toString().trim();
            if (strLicensePlate.equals("")) {
                Toast.makeText(this, "Please in put data", Toast.LENGTH_SHORT).show();
                edtLicensePlate.setText("");
            } else {
                mySaveLicensePlate();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void mySaveLicensePlate() {
        int recCount = database.query(
                DatabaseVehicleApply.TABLE_NAME
                , null
                , DatabaseVehicleApply.COL_LICENSE_PLATE + " = ? "
                , new String[]{strLicensePlate}
                , null
                , null
                , null)
                .getCount();
        if (recCount > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseVehicleApply.COL_STATUS, 1);
            database.update(DatabaseVehicleApply.TABLE_NAME, contentValues, DatabaseVehicleApply.COL_LICENSE_PLATE + " = ?", new String[]{strLicensePlate});
            Log.d("RecCount => ", "Update => " + recCount);

        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseVehicleApply.COL_LICENSE_PLATE, strLicensePlate);
            contentValues.put(DatabaseVehicleApply.COL_STATUS, 1);

            database.insert(DatabaseVehicleApply.TABLE_NAME, null, contentValues);
            Log.d("RecCount => ", "INSERT => " + recCount);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
        myDbHelper.close();

    }
}
