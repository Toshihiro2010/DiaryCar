package com.stecon.patipan_on.diarycar.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.TripStartActivity;

/**
 * Created by patipan_on on 12/7/2017.
 */

public class MyAddPermissionLocation {

    Context context;
    public final static int REQUEST_CODE_ASK_PERMISSIONS = 123;
    Boolean statusLocation;

    OnCustomClickDialog onCustomClickDialog = null;
    OnNextFunction onNextFunction = null;


    public MyAddPermissionLocation(Context context) {
        this.context = context;
        statusLocation = false;
    }

    public Boolean getStatusLocation() {
        return statusLocation;
    }

    public void setStatusLocation(Boolean statusLocation) {
        this.statusLocation = statusLocation;
    }

    public void checkLocation() {
        int locationCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (locationCheck != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("you need to allow accrss to GPS ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);
                        dialog.dismiss();
                        statusLocation = true;
                        if (onCustomClickDialog != null) {
                            onCustomClickDialog.onPositiveMyDialog();
                        }

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onCustomClickDialog != null) {
                            onCustomClickDialog.onNegativeMyDialog();
                        }
                        dialog.dismiss();
                    }
                });

                return;
            }
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        if (onNextFunction != null) {
            onNextFunction.onNewNextFuntion();
        }


        Log.d("Location = > ", "Start Location");

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener) {

        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", noListener)
                .create()
                .show();
    }

    public interface OnCustomClickDialog {
        public void onPositiveMyDialog();
        public void onNegativeMyDialog();
    }
    public void setOnCustomClickDialog(OnCustomClickDialog listener) {
        onCustomClickDialog = listener;
    }

    public interface OnNextFunction {
        void onNewNextFuntion();
    }

    public void setOnNextFunction(OnNextFunction onNextFunctionListener) {
        this.onNextFunction = onNextFunctionListener;
    }

}
