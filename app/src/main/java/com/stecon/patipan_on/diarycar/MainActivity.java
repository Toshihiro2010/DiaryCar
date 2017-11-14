package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGoDiary;
    private Button btnGoOil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bindWidGet();

        myOnClick();

        if (isNetworkAvailable()) {
            Toast.makeText(this, "Connect", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Connect", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void bindWidGet() {
        btnGoDiary = (Button) findViewById(R.id.btnGoDiary);
        btnGoOil = (Button) findViewById(R.id.btnGoOil);
    }

    private void myOnClick() {
        btnGoDiary.setOnClickListener(this);
        btnGoOil.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnGoDiary) {
            Log.d("test => ", "button go diary");
            Intent intent = new Intent(this, CarDiaryActivity.class);
            startActivity(intent);

        } else if (v == btnGoOil) {
            Log.d("test => ", "button btnGoOil");
            Intent intent = new Intent(getApplicationContext(), OilDiaryActivity.class);
            startActivity(intent);
            //Log.d("click = > ", getApplicationContext().toString());

        }
    }
}
