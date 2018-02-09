package com.stecon.patipan_on.diarycar.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.stecon.patipan_on.diarycar.R;

/**
 * Created by patipan_on on 11/28/2017.
 */

public class CustomAlertDialog {

    Context context;
    String title ;
    String message ;
    String positiveMessage;
    String negativeMessage;

    AlertDialog.Builder builder;
    OnMyDialogActivity onMyDialogActivity = null;

    public CustomAlertDialog(Context context) {
        this.context = context;
        this.title = context.getResources().getString(R.string.default_custom_alert_dialog_title);
        this.message = context.getResources().getString(R.string.default_custom_alert_dialog_message);
        this.positiveMessage = context.getResources().getString(R.string.ok);
        this.negativeMessage = context.getResources().getString(R.string.cancel);
    }

    public CustomAlertDialog(Context context, String title, String message) {
        this.context = context;
        this.message = message;
        this.title = title;
        this.positiveMessage = context.getResources().getString(R.string.ok);
        this.negativeMessage = context.getResources().getString(R.string.cancel);
    }

    public CustomAlertDialog(Context context, String title, String message, String positiveMessage, String negativeMessage) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.positiveMessage = positiveMessage;
        this.negativeMessage = negativeMessage;
    }

    public String getPositiveMessage() {
        return positiveMessage;
    }

    public void setPositiveMessage(String positiveMessage) {
        this.positiveMessage = positiveMessage;
    }

    public String getNegativeMessage() {
        return negativeMessage;
    }

    public void setNegativeMessage(String negativeMessage) {
        this.negativeMessage = negativeMessage;
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


        builder.setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onMyDialogActivity != null) {
                    onMyDialogActivity.onMyDialogPosititve();


                }
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(negativeMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        public void onMyDialogPosititve();
        public void onMyDialogNegative();
    }

    public void setOnMyDialogActivity(OnMyDialogActivity listener) {
        onMyDialogActivity = listener;
    }
}
