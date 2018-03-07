package com.stecon.patipan_on.diarycar.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.stecon.patipan_on.diarycar.R;

/**
 * Created by patipan_on on 12/6/2017.
 */

public class CustomProgressDialog {

    Context context;
    String title;
    String message;
    ProgressDialog progressDialog;




    public CustomProgressDialog(Context context) {
        this.context = context;
        this.title = context.getResources().getString(R.string.default_custom_progress_dialog_title);
        this.message = context.getResources().getString(R.string.default_custom_alert_dialog_message);
    }

    public CustomProgressDialog(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void myDialog() {
        progressDialog = new ProgressDialog(context,R.style.AlertDialogCustomTheme);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public void onDidmiss() {
        progressDialog.dismiss();
    }
}
