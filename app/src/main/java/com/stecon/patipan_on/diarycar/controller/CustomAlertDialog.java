package com.stecon.patipan_on.diarycar.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.LicensePlateActivity;
import com.stecon.patipan_on.diarycar.OilJournalActivity;

/**
 * Created by patipan_on on 11/28/2017.
 */

public class CustomAlertDialog {

    public interface OnMyDialogActivity {
        public void onMyStartActivity();
    }

    public void setOnMyDialogActivity(OnMyDialogActivity listener) {
        onMyDialogActivity = listener;
    }

    Context context;
    AlertDialog.Builder builder;
    OnMyDialogActivity onMyDialogActivity = null;

    public CustomAlertDialog(Context context) {
        this.context = context;
    }


    public void myDialog() {

        builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("No LicensePlate");
        builder.setMessage("You should in put LicensePlate............");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "onOk", Toast.LENGTH_SHORT).show();
                if (onMyDialogActivity != null) {
                    onMyDialogActivity.onMyStartActivity();


                }
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "onCancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void show() {
        builder.show();
    }
}
