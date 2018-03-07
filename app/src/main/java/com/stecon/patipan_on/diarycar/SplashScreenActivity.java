package com.stecon.patipan_on.diarycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.model.MyAppConfig;
import com.stecon.patipan_on.diarycar.model.PinCodeStatic;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    private long delay_time;
    private long time = 3000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String employee_id;
    private String license_plate;
    private String pin_code;
    private String pin_code_static;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        employee_id = sharedPreferences.getString(MyAppConfig.employee_id, "");
        license_plate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        pin_code = sharedPreferences.getString(MyAppConfig.pin_code, "");
        pin_code_static = PinCodeStatic.getPinNumber();


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (employee_id.equals("")) {
                    Intent intent = new Intent(SplashScreenActivity.this, VerifiedActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!pin_code.equals("") && pin_code_static == null) {
                        Intent intent = new Intent(SplashScreenActivity.this, PinCodeActivity.class);
                        intent.putExtra(PinCodeActivity.PIN_MODE, PinCodeActivity.pin_apply);
                        startActivityForResult(intent, PinCodeActivity.REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PinCodeActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String pinForResult = data.getStringExtra(PinCodeActivity.PIN_RESULT);
                PinCodeStatic.setPinNumber(pinForResult);
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();

        }
    }
}
