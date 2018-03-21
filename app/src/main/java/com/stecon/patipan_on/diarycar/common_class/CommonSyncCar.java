package com.stecon.patipan_on.diarycar.common_class;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseVehicleMaster;
import com.stecon.patipan_on.diarycar.model.CarModel;
import com.stecon.patipan_on.diarycar.model.Recordset_;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by patipan_on on 3/5/2018.
 */

public class CommonSyncCar extends AsyncTask<Void,Void,String> {

    private Context context;
//    private CustomProgressDialog customProgressDialog;


    public CommonSyncCar(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        customProgressDialog = new CustomProgressDialog(context);
//        customProgressDialog.myDialog();
    }

    @Override
    protected String doInBackground(Void... params) {

        String strUrl = "http://172.20.20.57:888/findLicenseAll";
        Request request = new Request.Builder().get().url(strUrl).build();
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

            MyDbHelper myDbHelper = new MyDbHelper(context);
            SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();

            sqLiteDatabase.delete(DatabaseVehicleMaster.TABLE_NAME, null, null);
            Log.d("s => ", s);
            Gson gson = new Gson();
            CarModel carModel = gson.fromJson(s, CarModel.class);
            int size_car = carModel.getRecordset().size();
            if (size_car != 0) {
                for (int i = 0; i < size_car; i++) {
                    Recordset_ recordset = carModel.getRecordset().get(i);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseVehicleMaster.COL_VEHICLE_ID, recordset.getCarID());
                    contentValues.put(DatabaseVehicleMaster.COL_LICENSE_PLATE, recordset.getLicenseNo());
                    contentValues.put(DatabaseVehicleMaster.COL_STATUS, recordset.getStatus());

                    long id = sqLiteDatabase.insert(DatabaseVehicleMaster.TABLE_NAME, null, contentValues);
                    Log.d("id => ", id + "");
                }
            }
        } else {
            Toast.makeText(context, "No Connect Server", Toast.LENGTH_SHORT).show();
        }
//        customProgressDialog.onDidmiss();

    }
}
