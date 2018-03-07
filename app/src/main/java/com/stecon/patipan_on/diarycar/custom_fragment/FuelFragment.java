package com.stecon.patipan_on.diarycar.custom_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.PriceAllActivity;
import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.OilAdapter;
import com.stecon.patipan_on.diarycar.database.query_model.FuelDataQuerySQLIte;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.util.ArrayList;

/**
 * Created by patipan_on on 2/8/2018.
 */

public class FuelFragment extends Fragment {

    private RecyclerView fuelRecycleView;
    private MyDbHelper myDbHelper ;
    private SQLiteDatabase sqLiteDatabase ;
    private FuelDataQuerySQLIte fuelDataQuerySQLIte;
    private ArrayList<OilDataModel> oilDataModelArrayList;
    private String licensePlate;
    private ConstraintLayout constraintLayout;


    public FuelFragment() {

    }

    public static FuelFragment newInstance() {
        FuelFragment fuelFragment = new FuelFragment();
        return fuelFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragment => ", "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_fuel, container, false);
        fuelRecycleView = (RecyclerView) rootView.findViewById(R.id.fuelRecycleViewInFragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        constraintLayout = rootView.findViewById(R.id.containerFuelFragment);
        fuelRecycleView.setLayoutManager(linearLayoutManager);
        myDbHelper = new MyDbHelper(getActivity());
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        fuelDataQuerySQLIte = new FuelDataQuerySQLIte(getActivity());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("fragment => ", "onViewCreated");
        onSetQuery();

    }

    private void onSetQuery() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        licensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        if (licensePlate.equals("")) {
            return;
        }

        oilDataModelArrayList = fuelDataQuerySQLIte.getDataFuelFromSqlite(licensePlate);
        if (oilDataModelArrayList != null) {
            OilAdapter oilAdapter = new OilAdapter(getActivity(), oilDataModelArrayList);
            fuelRecycleView.setAdapter(oilAdapter);
            if (oilDataModelArrayList.size() != 0) {
                setVisibleRecycleView();
                return;
            }
            setInVisibleRecycleView();
        } else {
            setInVisibleRecycleView();
        }

    }

    private void setVisibleRecycleView() {
        constraintLayout.setVisibility(View.INVISIBLE);
        fuelRecycleView.setVisibility(View.VISIBLE);
    }

    private void setInVisibleRecycleView() {
        constraintLayout.setVisibility(View.VISIBLE);
        fuelRecycleView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("fragment => ", "onActivityCreated");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("fragment => ", "onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fragment => ", "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment => ", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment => ", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fragment => ", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("fragment => ", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fragment => ", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("fragment => ", "onDetach");
    }
}
