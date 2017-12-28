package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.content.Intent;

import com.stecon.patipan_on.diarycar.TripStartActivity;

/**
 * Created by patipan_on on 12/21/2017.
 */

public class MyTestGoActivity {

    Context context;
    private long trip_id;
    private String LicensePlate;

    public MyTestGoActivity(Context context) {
        this.context = context;
    }

    public void checkPriceOther() {
        Intent intent = new Intent(context, TripStartActivity.class);
        context.startActivity(intent);



    }
}
