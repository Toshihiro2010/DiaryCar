package com.stecon.patipan_on.diarycar.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;

import org.w3c.dom.Text;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class PostInfoViewHolder extends RecyclerView.ViewHolder{

    public TextView tvDate;
    public TextView tvOdometer;
    public TextView tvMoney;
    public TextView tvFuelAmount;
    public TextView tvTotalMoney;
    public TextView tvLocation;


    public PostInfoViewHolder(View itemView) {
        super(itemView);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvOdometer = (TextView) itemView.findViewById(R.id.tvOdometer);
        tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
        tvFuelAmount = (TextView) itemView.findViewById(R.id.tvFuelAmount);
        tvTotalMoney = (TextView) itemView.findViewById(R.id.tvTotalMoney);
        tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
    }
}
