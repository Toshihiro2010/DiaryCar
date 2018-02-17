package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.controller.OilAdapter;
import com.stecon.patipan_on.diarycar.controller.ServiceAdapter;
import com.stecon.patipan_on.diarycar.database.query_model.FuelDataQuerySQLIte;
import com.stecon.patipan_on.diarycar.database.query_model.ServiceQuerySQLite;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.ServiceRecordModel;

import java.util.ArrayList;


public class ServiceFragment extends Fragment {

    private RecyclerView serviceRecyclerView;
    private MyDbHelper myDbHelper ;
    private SQLiteDatabase sqLiteDatabase ;
    private ServiceQuerySQLite serviceQuerySQLite;
    private ArrayList<ServiceRecordModel> serviceRecordModelArrayList;


    public ServiceFragment() {
    }


    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service_custom, container, false);
        serviceRecyclerView = (RecyclerView) rootView.findViewById(R.id.serviceRecycleViewInFragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        serviceRecyclerView.setLayoutManager(linearLayoutManager);
        myDbHelper = new MyDbHelper(getActivity());
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        serviceQuerySQLite = new ServiceQuerySQLite(getActivity());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onSetQuery();
    }

    private void onSetQuery() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        String licensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        if (licensePlate.equals("")) {
            return;
        }
        serviceRecordModelArrayList = serviceQuerySQLite.getDataServiceFromSqlite(licensePlate);
        if (serviceRecordModelArrayList != null) {
            ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(),serviceRecordModelArrayList);
            serviceRecyclerView.setAdapter(serviceAdapter);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
