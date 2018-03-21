package com.stecon.patipan_on.diarycar.common_class;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.stecon.patipan_on.diarycar.R;
import com.stecon.patipan_on.diarycar.controller.CustomAlertDialog;

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
//                showMessageOKCancel("you need to allow accrss to GPS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCompat.requestPermissions((Activity) context,
//                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);
//                        dialog.dismiss();
//                        statusLocation = true;
//                        if (onCustomClickDialog != null) {
//                            onCustomClickDialog.onPositiveMyDialog();
//                        }
//
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (onCustomClickDialog != null) {
//                            onCustomClickDialog.onNegativeMyDialog();
//                        }
//                        dialog.dismiss();
//                    }
//                });
                CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, context.getResources().getString(R.string.message_title_add_permission), "you need to allow accrss to GPS");
                customAlertDialog.myDefaultDialog();
                customAlertDialog.show();
                customAlertDialog.setOnMyDialogActivity(new CustomAlertDialog.OnMyDialogActivity() {
                    @Override
                    public void onMyDialogPositive() {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);

                        statusLocation = true;
                        if (onCustomClickDialog != null) {
                            onCustomClickDialog.onPositiveMyDialog();
                        }
                    }

                    @Override
                    public void onMyDialogNegative() {
                        if (onCustomClickDialog != null) {
                            onCustomClickDialog.onNegativeMyDialog();
                        }
                    }
                });

                return;
            }
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyAddPermissionLocation.REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        if (onNextFunction != null) {
            onNextFunction.onNewNextFunction();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener) {

        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.message_title_add_permission))
                .setMessage(message)
                .setPositiveButton(context.getResources().getString(R.string.ok), okListener)
                .setNegativeButton(context.getResources().getString(R.string.cancel), noListener)
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
        void onNewNextFunction();
    }

    public void setOnNextFunction(OnNextFunction onNextFunctionListener) {
        this.onNextFunction = onNextFunctionListener;
    }



}
