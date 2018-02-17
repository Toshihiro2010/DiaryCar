package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.ServiceJournalActivity;
import com.stecon.patipan_on.diarycar.model.ItemClickListener;
import com.stecon.patipan_on.diarycar.model.ServiceRecordModel;
import com.stecon.patipan_on.diarycar.model.ServiceViewHolder;

import java.util.ArrayList;

/**
 * Created by patipan_on on 1/19/2018.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> implements ItemClickListener{


    Context context;
    ArrayList<ServiceRecordModel> serviceRecordModelArrayList;

    public ServiceAdapter(Context context, ArrayList<ServiceRecordModel> serviceRecordModelArrayList) {
        this.context = context;
        this.serviceRecordModelArrayList = serviceRecordModelArrayList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_service, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {

        ServiceViewHolder serviceViewHolder = holder;

//        DecimalFormat df = new DecimalFormat("#.00");
        ServiceRecordModel serviceRecordModel = serviceRecordModelArrayList.get(position);

        String date = serviceRecordModel.getTransaction_date();
        serviceViewHolder.tvServiceDate.setText(date.toString());

        Double odometer = serviceRecordModel.getOdometer();
        serviceViewHolder.tvServiceOdometer.setText(odometer.toString() + " " + context.getResources().getString(R.string.km));

//        String license_plate = serviceRecordModel.getLicense_plate();
//        serviceViewHolder.tvLicensePlate.setText(license_plate.toString());

        String location_name = serviceRecordModel.getLocation_name();
        if (location_name.equals("")) {
            serviceViewHolder.tvServiceLocationName.setText("No Location find");
        } else {
            serviceViewHolder.tvServiceLocationName.setText(location_name);
        }

        Double service_cost = serviceRecordModel.getService_cost();
        serviceViewHolder.tvServiceCost.setText(service_cost + " " + context.getResources().getString(R.string.bath));

        int serviceName = serviceRecordModel.getService_id();
        serviceViewHolder.tvServiceNmae.setText(serviceName + " ");

        serviceViewHolder.setOnClickListener(this);


    }

    @Override
    public int getItemCount() {
        return serviceRecordModelArrayList.size();
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(context, ServiceJournalActivity.class);
        intent.putExtra("data_id", serviceRecordModelArrayList.get(position).getId());
        context.startActivity(intent);
        //Toast.makeText(context, "ยังไม่พร้อมใช้งาน", Toast.LENGTH_SHORT).show();
    }
}
