package com.stecon.patipan_on.diarycar.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class OilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvDate;
    public TextView tvOdometer;
    public TextView tvDetail;
    public TextView tvFuelAmount;
    public TextView tvTotalMoney;
    public TextView tvLocation;

    private ItemClickListener itemClickListener = null;


    public OilViewHolder(View itemView) {
        super(itemView);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvOdometer = (TextView) itemView.findViewById(R.id.tvOdometer);
        tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
        tvFuelAmount = (TextView) itemView.findViewById(R.id.tvFuelAmount);
        tvTotalMoney = (TextView) itemView.findViewById(R.id.tvTotalMoney);
        tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);

        itemView.setOnClickListener(this);
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
