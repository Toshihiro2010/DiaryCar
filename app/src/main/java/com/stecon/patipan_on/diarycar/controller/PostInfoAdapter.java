package com.stecon.patipan_on.diarycar.controller;



import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.CarDiaryActivity;
import com.stecon.patipan_on.diarycar.OilJournalActivity;
import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.model.ItemClickListener;
import com.stecon.patipan_on.diarycar.model.MyData;
import com.stecon.patipan_on.diarycar.model.OilDataModel;
import com.stecon.patipan_on.diarycar.model.PostInfoViewHolder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class PostInfoAdapter extends RecyclerView.Adapter<PostInfoViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<OilDataModel> dataModels;


    public PostInfoAdapter(Context context, ArrayList<OilDataModel> dataModels) {
        this.context = context;
        this.dataModels = dataModels;
    }


    @Override
    public PostInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_oil, parent, false);

        return new PostInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostInfoViewHolder holder, int position) {
        PostInfoViewHolder postInfoViewHolder = holder;

        DecimalFormat df = new DecimalFormat("#.00");
        OilDataModel oilDataModel = dataModels.get(position);
        String date = oilDataModel.getTransaction_date();
        postInfoViewHolder.tvDate.setText(date.toString());

        Double odometer = oilDataModel.getOdometer();
        postInfoViewHolder.tvOdometer.setText(odometer.toString() + " " + context.getResources().getString(R.string.km));

        Double total_price = oilDataModel.getTotal_price();
        postInfoViewHolder.tvTotalMoney.setText(total_price.toString() + " " + context.getResources().getString(R.string.bath));

        String fueltype_amount = oilDataModel.getFuel_type() + " : " + df.format(oilDataModel.getVolume()) + " " + context.getResources().getString(R.string.litemeter);
        postInfoViewHolder.tvFuelAmount.setText(fueltype_amount);

        String tvDetail = context.getResources().getString(R.string.bath)
                + df.format(oilDataModel.getUnit_price()) + "/"
                + context.getResources().getString(R.string.litemeter) + " "
                + oilDataModel.getPayment_type();
        postInfoViewHolder.tvDetail.setText(tvDetail);


        String strLocation = oilDataModel.getStrLocation();
        postInfoViewHolder.tvLocation.setText(strLocation);

        if (strLocation != null) {
            postInfoViewHolder.tvLocation.setText(strLocation);
        } else {
            postInfoViewHolder.tvLocation.setVisibility(View.INVISIBLE);
        }

        postInfoViewHolder.setOnClickListener(this);
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
