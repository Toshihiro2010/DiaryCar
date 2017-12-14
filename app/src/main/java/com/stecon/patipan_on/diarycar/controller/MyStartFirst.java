package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.stecon.patipan_on.diarycar.LicensePlateActivity;

/**
 * Created by patipan_on on 12/11/2017.
 */

public class MyStartFirst {

    Context context;
    private String licensePlate;
    private long tripIdLong;

    private CallbackMyStartFirst callbackMyStartFirst = null;

    public MyStartFirst(Context context) {
        this.context = context;
        licensePlate = "";
        tripIdLong = 0;
    }

    public MyStartFirst(Context context, String licensePlate, long tripIdLong) {
        this.context = context;
        this.licensePlate = licensePlate;
        this.tripIdLong = tripIdLong;
    }

    public void checkLicensePlate() {
        Log.d("checkFirst", " checkLicensePlate");
        if (licensePlate.equals("")) {
            if (callbackMyStartFirst != null) {
                callbackMyStartFirst.onCallbackLicensePlate();
            }
        }
    }

    public void checkTrip() {
        Log.d("checkFirst", " checkTrip");
        if (tripIdLong == 0) {
            if (callbackMyStartFirst != null) {
                callbackMyStartFirst.onCallbackTrip();
            }
        }

    }


    public interface CallbackMyStartFirst {
        void onCallbackLicensePlate();
        void onCallbackTrip();
    }

    public void registerCallback(CallbackMyStartFirst callbackListener) {
        this.callbackMyStartFirst = callbackListener;
    }






}
