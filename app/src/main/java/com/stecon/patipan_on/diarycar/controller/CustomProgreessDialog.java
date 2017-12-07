package com.stecon.patipan_on.diarycar.controller;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by patipan_on on 12/6/2017.
 */

public class CustomProgreessDialog {

    Context context;
    String title;
    String message;
    ProgressDialog progressDialog;

    public CustomProgreessDialog(Context context) {
        this.context = context;
        this.title = "Please Wait";
        this.message = "Loading.............";
    }

    public CustomProgreessDialog(Context context, String title, String message) {
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
        progressDialog = new ProgressDialog(context);
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
