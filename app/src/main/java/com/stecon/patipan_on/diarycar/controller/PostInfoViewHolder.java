package com.stecon.patipan_on.diarycar.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class PostInfoViewHolder extends RecyclerView.ViewHolder{

    public TextView tvDate;
    public TextView tvOdometer;
    public TextView tvDetail;
    public TextView tvFuelAmount;
    public TextView tvTotalMoney;
    public TextView tvLocation;


    public PostInfoViewHolder(View itemView) {
        super(itemView);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvOdometer = (TextView) itemView.findViewById(R.id.tvOdometer);
        tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
        tvFuelAmount = (TextView) itemView.findViewById(R.id.tvFuelAmount);
        tvTotalMoney = (TextView) itemView.findViewById(R.id.tvTotalMoney);
        tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
    }
}
