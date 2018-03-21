package com.stecon.patipan_on.diarycar.common_class;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.R;

import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
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
    private CustomProgressDialog customProgressDialog;
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

            customProgressDialog = new CustomProgressDialog(context);
            customProgressDialog.setTitle(context.getResources().getString(R.string.default_my_location_fist_title));
            customProgressDialog.myDialog();
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
                            customProgressDialog.onDidmiss();
                            onStopSmartLocation();
                            if (onNextLocationFunction != null) {
                                onNextLocationFunction.onStartNextFunction();
                            }
                        }
                    });

        } else {
            if (onNextLocationFunction != null) {
                onNextLocationFunction.onErrorNotFindGps();
            }
            Log.d("GPS=>", "you should allow GPS");
        }
    }

    public void onGeocodingGpsStaty() {
        if (SmartLocation.with(context).location().state().locationServicesEnabled()) {

            customProgressDialog = new CustomProgressDialog(context);
            customProgressDialog.setTitle("Finding Location Address");
            customProgressDialog.myDialog();
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
                            SmartLocation.with(context).geocoding().reverse(location, new OnReverseGeocodingListener() {
                                @Override
                                public void onAddressResolved(Location location, List<Address> list) {
                                    Log.d("location => ", location.toString());
                                    Log.d("size => ", list.size() + "");
                                    Log.d("list => ", list.toString());
                                    customProgressDialog.onDidmiss();
                                }
                            });
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
        void onStartNextFunction();
        void onErrorNotFindGps();


    }

    public void setListennerNextLocationFunction(OnNextLocationFunction listener) {
        this.onNextLocationFunction = listener;
    }



}
