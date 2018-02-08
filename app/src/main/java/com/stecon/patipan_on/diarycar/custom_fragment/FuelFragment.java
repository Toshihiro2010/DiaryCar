package com.stecon.patipan_on.diarycar.custom_fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.OilListActivity;
import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.OilAdapter;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.model.MyDateModify;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by patipan_on on 2/8/2018.
 */

public class FuelFragment extends Fragment {

    private RecyclerView fuelRecycleView;
    private MyDbHelper myDbHelper ;
    private SQLiteDatabase sqLiteDatabase ;


    public FuelFragment() {

    }

    public static FuelFragment newInstance() {
        FuelFragment fuelFragment = new FuelFragment();
        return fuelFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fuel, container, false);
        fuelRecycleView = (RecyclerView) rootView.findViewById(R.id.fuelRecycleViewInFragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fuelRecycleView.setLayoutManager(linearLayoutManager);
        myDbHelper = new MyDbHelper(getActivity());
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onSetQuery();
    }

    private void onSetQuery() {
        ArrayList<OilDataModel> arrayList;
        List<Address> addresses;
        arrayList = new ArrayList<>();

        String strSql = "SELECT * FROM "
                + DatabaseOilJournal.TABLE_NAME
                + " ORDER BY " + DatabaseOilJournal.COL_ID + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0 ; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                double odometer = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_ODOMETER));
                double unit_price = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_UNIT_PRICE));
                double volume = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_VOLUME));
                double total_rpice = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_TOTAL_PRICE));
                String payment_type = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_PAYMENT_TYPE));
                double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOilJournal.COL_LONGITUDE));
                String note = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_NOTE));
                String fueltype = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_FUEL_TYPE));
                String tempDate = cursor.getString(cursor.getColumnIndex(DatabaseOilJournal.COL_TRANSACTION_DATE));
                String[] customDate = MyDateModify.getStrsDateTimeFromSqlite(tempDate);

                OilDataModel oilDataModel = new OilDataModel(id,odometer, unit_price, volume,fueltype, total_rpice, payment_type, latitude, longitude, note, customDate[0]);
                arrayList.add(oilDataModel);


                Geocoder geocoder = new Geocoder(getActivity() , Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if (addresses != null) {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        oilDataModel.setStrLocation(address + " " + city + " " + state + " " + country);

                    } else {

//                        oilDataModel.setStrLocation("No location");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!cursor.isLast()) {
                    cursor.moveToNext();
                }

            }

        }

        OilAdapter oilAdapter = new OilAdapter(getActivity(),arrayList);
        fuelRecycleView.setAdapter(oilAdapter);
    }


}
