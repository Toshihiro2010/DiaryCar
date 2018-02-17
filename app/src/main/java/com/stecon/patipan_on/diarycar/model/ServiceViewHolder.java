package com.stecon.patipan_on.diarycar.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;

/**
 * Created by patipan_on on 1/19/2018.
 */

public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvServiceDate;
    public TextView tvServiceOdometer;
    public TextView tvServiceLocationName;
    public TextView tvServiceCost;
    public TextView tvServiceNmae;

    private ItemClickListener itemClickListener = null;

    public ServiceViewHolder(View itemView) {
        super(itemView);

        tvServiceDate = (TextView) itemView.findViewById(R.id.tvListServiceDate);
        tvServiceOdometer = (TextView) itemView.findViewById(R.id.tvListServiceOdometer);
        tvServiceLocationName = (TextView) itemView.findViewById(R.id.tvServiceLocationName);
        tvServiceCost = (TextView) itemView.findViewById(R.id.tvListServiceCost);
        tvServiceNmae = (TextView) itemView.findViewById(R.id.tvListServiceName);
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
