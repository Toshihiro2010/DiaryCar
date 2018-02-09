package com.stecon.patipan_on.diarycar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.PriceOtherAdapter;
import com.stecon.patipan_on.diarycar.database.DatabaseOilJournal;
import com.stecon.patipan_on.diarycar.database.DatabaseTripCost;
import com.stecon.patipan_on.diarycar.model.MyDateModify;
import com.stecon.patipan_on.diarycar.model.TripCostModel;

import java.util.ArrayList;

/**
 * Created by patipan_on on 2/8/2018.
 */

public class PriceOtherFragment extends Fragment {

    private RecyclerView priceOtherRecycleView;
    private MyDbHelper myDbHelper ;
    private SQLiteDatabase sqLiteDatabase ;

    public PriceOtherFragment() {

    }

    public static PriceOtherFragment newInstance() {
        PriceOtherFragment priceOtherFragment = new PriceOtherFragment();
        return priceOtherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_price_other, container, false);
        priceOtherRecycleView = (RecyclerView) rootView.findViewById(R.id.priceOtherRecycleInFragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        priceOtherRecycleView.setLayoutManager(linearLayoutManager);
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

        ArrayList<TripCostModel> costModelArrayList = new ArrayList<>();
        String strSql = "SELECT * FROM " + DatabaseTripCost.TABLE_NAME ;
        //String strSql = "SELECT * FROM " + DatabaseTripCost.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(strSql , null);

        int i = 0 ;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                i++;
                Log.d("I => ", i + "");
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseOilJournal.COL_ID));
                int trip_id = cursor.getInt(cursor.getColumnIndex(DatabaseTripCost.COL_TRIP_ID));
                String price_type = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_PRICE_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_PRICE_TITLE));
                double money = cursor.getDouble(cursor.getColumnIndex(DatabaseTripCost.COL_PRICE_MONEY));
                String note = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_NOTE));
                String temp_date = cursor.getString(cursor.getColumnIndex(DatabaseTripCost.COL_TRANSACTION_DATE));

                String[] date = MyDateModify.getStrsDateTimeFromSqlite(temp_date);

                TripCostModel tripCostModel = new TripCostModel(id,trip_id, price_type, title, money, note, date[0]);
                costModelArrayList.add(tripCostModel);
                cursor.moveToNext();
            }
        }

        PriceOtherAdapter priceOtherAdapter = new PriceOtherAdapter(getActivity(), costModelArrayList);
        priceOtherRecycleView.setAdapter(priceOtherAdapter);

    }


}
