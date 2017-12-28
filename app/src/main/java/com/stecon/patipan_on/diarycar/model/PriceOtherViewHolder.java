package com.stecon.patipan_on.diarycar.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stecon.patipan_on.diarycar.R;

/**
 * Created by patipan_on on 12/21/2017.
 */

public class PriceOtherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvPriceDate;
    public TextView tvPriceType;
    public TextView tvPriceTitle;
    public TextView tvPriceMoney;

    public ItemClickListener itemClickListener = null;


    public PriceOtherViewHolder(View itemView) {
        super(itemView);
        tvPriceDate = (TextView) itemView.findViewById(R.id.tvListPriceDate);
        tvPriceType = (TextView) itemView.findViewById(R.id.tvListPriceType);
        tvPriceTitle = (TextView) itemView.findViewById(R.id.tvListPriceTitle);
        tvPriceMoney = (TextView) itemView.findViewById(R.id.tvListPriceMoney);

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
