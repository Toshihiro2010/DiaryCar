package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

/**
 * Created by patipan_on on 12/6/2017.
 */

public class MyLocationFirst {

    Context context;
    private Double latitude;
    private Double longitude;
    private CustomProgreessDialog customProgreessDialog;
    private OnNextLocationFunction onNextLocationFunction = null;

    public MyLocationFirst(Context context) {
        this.context = context;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void onLocationStart() {
        if (SmartLocation.with(context).location().state().locationServicesEnabled()) {

            customProgreessDialog = new CustomProgreessDialog(context);
            customProgreessDialog.setTitle("Finding Location GPS");
            customProgreessDialog.myDialog();
            LocationParams locationParams = new LocationParams.Builder()
                    .setAccuracy(LocationAccuracy.HIGH)
                    .build();

            SmartLocation.with(context)
                    .location(new LocationGooglePlayServicesWithFallbackProvider(context))
                    .oneFix()
                    .config(locationParams)
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            customProgreessDialog.onDidmiss();
                            onStopSmartLocation();
                            if (onNextLocationFunction != null) {
                                onNextLocationFunction.onStratNextFunction();
                            }
                        }
                    });

        } else {
            Toast.makeText(context, "You allow GPS Open", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStopSmartLocation() {
        SmartLocation.with(context)
                .location()
                .stop();
    }

    public interface OnNextLocationFunction {
        void onStratNextFunction();
    }

    public void registerOnextLocationFunction(OnNextLocationFunction listener) {
        this.onNextLocationFunction = listener;
    }



}
