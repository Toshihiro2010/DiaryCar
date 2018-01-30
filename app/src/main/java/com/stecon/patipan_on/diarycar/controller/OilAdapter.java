package com.stecon.patipan_on.diarycar.controller;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.OilJournalActivity;
import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.model.ItemClickListener;
import com.stecon.patipan_on.diarycar.model.OilDataModel;
import com.stecon.patipan_on.diarycar.model.OilViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class OilAdapter extends RecyclerView.Adapter<OilViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<OilDataModel> dataModels;


    public OilAdapter(Context context, ArrayList<OilDataModel> dataModels) {
        this.context = context;
        this.dataModels = dataModels;
    }


    @Override
    public OilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_oil, parent, false);

        return new OilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OilViewHolder holder, int position) {
        OilViewHolder oilViewHolder = holder;

        DecimalFormat df = new DecimalFormat("#.00");
        OilDataModel oilDataModel = dataModels.get(position);
        String date = oilDataModel.getTransaction_date();
        oilViewHolder.tvDate.setText(date.toString());

        Double odometer = oilDataModel.getOdometer();
        oilViewHolder.tvOdometer.setText(odometer.toString() + " " + context.getResources().getString(R.string.km));

        Double total_price = oilDataModel.getTotal_price();
        oilViewHolder.tvTotalMoney.setText(total_price.toString() + " " + context.getResources().getString(R.string.bath));

        String fueltype_amount = oilDataModel.getFuel_type() + " : " + df.format(oilDataModel.getVolume()) + " " + context.getResources().getString(R.string.lit);
        oilViewHolder.tvFuelAmount.setText(fueltype_amount);

        String tvDetail = context.getResources().getString(R.string.bath)
                + df.format(oilDataModel.getUnit_price()) + "/"
                + context.getResources().getString(R.string.lit) + " "
                + oilDataModel.getPayment_type();
        oilViewHolder.tvDetail.setText(tvDetail);


        String strLocation = oilDataModel.getStrLocation();
        oilViewHolder.tvLocation.setText(strLocation);

        if (strLocation != null) {
            oilViewHolder.tvLocation.setText(strLocation);
        } else {
            oilViewHolder.tvLocation.setVisibility(View.INVISIBLE);
        }

        oilViewHolder.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    @Override
    public void onClick(View view, int i) {
        Intent intent = new Intent(context, OilJournalActivity.class);
        intent.putExtra("data_id", dataModels.get(i).getId());
        context.startActivity(intent);
    }
}
