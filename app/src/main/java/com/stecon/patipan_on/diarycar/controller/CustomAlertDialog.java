package com.stecon.patipan_on.diarycar.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by patipan_on on 11/28/2017.
 */

public class CustomAlertDialog {

    Context context;
    String title ;
    String message ;

    AlertDialog.Builder builder;
    OnMyDialogActivity onMyDialogActivity = null;

    public CustomAlertDialog(Context context) {
        this.context = context;
        this.title = "You should in put title";
        this.message = "You should in put message............";
    }

    public CustomAlertDialog(Context context, String title, String message) {
        this.context = context;
        this.message = message;
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void myDefaultDialog() {

        builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("CustomDialog =>", "Ok");
                if (onMyDialogActivity != null) {
                    onMyDialogActivity.onMyDialogPostitve();


                }
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("CustomDialog =>", "No");
                if (onMyDialogActivity != null) {
                    onMyDialogActivity.onMyDialogNegative();
                }
                dialog.dismiss();
            }
        });
    }

    public void show() {
        builder.show();
    }



    public interface OnMyDialogActivity {
        public void onMyDialogPostitve();
        public void onMyDialogNegative();
    }

    public void setOnMyDialogActivity(OnMyDialogActivity listener) {
        onMyDialogActivity = listener;
    }
}
