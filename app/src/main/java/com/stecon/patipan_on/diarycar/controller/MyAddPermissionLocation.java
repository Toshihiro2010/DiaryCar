package com.stecon.patipan_on.diarycar.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by patipan_on on 12/7/2017.
 */

public class MyAddPermissionLocation {

    Context context;

    public final static int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public MyAddPermissionLocation(Context context) {
        this.context = context;
    }

    public void checkLocation() {
        int locationCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (locationCheck != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("you need to allow accrss to Camera ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);
                    }
                });
                return;
            }
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        Toast.makeText(context, "Start Location", Toast.LENGTH_SHORT).show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
