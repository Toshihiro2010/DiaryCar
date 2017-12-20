package com.stecon.patipan_on.diarycar.controller;



import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.model.OilDataModel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class PostInfoAdapter extends RecyclerView.Adapter<PostInfoViewHolder> {

    private Context context;
    private ArrayList<OilDataModel> dataModels;
    List<Address> addresses;


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

        Double latitude = oilDataModel.getLatitude();
        Double longitude = oilDataModel.getLongitude();
        Geocoder geocoder = new Geocoder(context , Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                Log.d("addresses => ", addresses.toString());
                postInfoViewHolder.tvLocation.setText(address + " " + city + " " + state + " " + country);

            } else {
                postInfoViewHolder.tvLocation.setVisibility(View.INVISIBLE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

}
