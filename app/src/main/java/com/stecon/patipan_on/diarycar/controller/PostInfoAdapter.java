package com.stecon.patipan_on.diarycar.controller;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.util.ArrayList;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class PostInfoAdapter extends RecyclerView.Adapter<PostInfoViewHolder> {

    private ArrayList<OilDataModel> dataModels;

    public PostInfoAdapter(ArrayList<OilDataModel> dataModels) {
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

        OilDataModel oilDataModel = dataModels.get(position);
        String date = oilDataModel.getTransaction_date();
        postInfoViewHolder.tvDate.setText(date.toString());

        Double odometer = oilDataModel.getOdometer();
        postInfoViewHolder.tvOdometer.setText(odometer.toString());

        Double total_price = oilDataModel.getTotal_price();
        postInfoViewHolder.tvTotalMoney.setText(total_price.toString());

    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

}
