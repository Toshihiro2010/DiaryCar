package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.OilJournalActivity;
import com.stecon.patipan_on.diarycar.PriceOtherActivity;
import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.model.ItemClickListener;
import com.stecon.patipan_on.diarycar.model.PostInfoViewHolder;
import com.stecon.patipan_on.diarycar.model.PriceOtherViewHolder;
import com.stecon.patipan_on.diarycar.model.TripCostModel;

import java.util.ArrayList;

/**
 * Created by patipan_on on 12/21/2017.
 */

public class PriceOtherAdapter extends RecyclerView.Adapter<PriceOtherViewHolder> implements ItemClickListener {

    Context context;
    ArrayList<TripCostModel> tripCostModelArrayList;


    public PriceOtherAdapter(Context context, ArrayList<TripCostModel> tripCostModelArrayList) {
        this.context = context;
        this.tripCostModelArrayList = tripCostModelArrayList;
    }

    @Override
    public PriceOtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_price_other, parent, false);
        PriceOtherViewHolder priceOtherViewHolder = new PriceOtherViewHolder(view);
        return priceOtherViewHolder;

    }

    @Override
    public void onBindViewHolder(PriceOtherViewHolder holder, int position) {

        PriceOtherViewHolder priceOtherViewHolder = holder;
        String strDate = tripCostModelArrayList.get(position).getTransaction_date();
        holder.tvPriceDate.setText(strDate);

        String strPriceType = tripCostModelArrayList.get(position).getPrice_type();
        holder.tvPriceType.setText(strPriceType);

        String strPriceTitle = tripCostModelArrayList.get(position).getTitle();
        holder.tvPriceTitle.setText(strPriceTitle);

        Double priceMoneyDouble = tripCostModelArrayList.get(position).getMoney();
        holder.tvPriceMoney.setText(priceMoneyDouble + " " + context.getResources().getString(R.string.bath));

        priceOtherViewHolder.setOnClickListener(this);

    }


    @Override
    public int getItemCount() {
        return tripCostModelArrayList.size();
    }

    @Override
    public void onClick(View view, int i) {
        Intent intent = new Intent(context, PriceOtherActivity.class);
        intent.putExtra("data_id", tripCostModelArrayList.get(i).getId());
        context.startActivity(intent);
    }
}
