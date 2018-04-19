package com.stecon.patipan_on.diarycar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stecon.patipan_on.diarycar.controller.MyDbHelper;
import com.stecon.patipan_on.diarycar.database.DatabaseUser;
import com.stecon.patipan_on.diarycar.common_class.MyAppConfig;

import java.util.ArrayList;

public class PinCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitlePin;
    private TextView tvMessagePin;
    private ImageView imgPinTitle;

    private Button btnPin1,btnPin2,btnPin3,btnPin4,btnPin5,btnPin6,btnPin7,btnPin8,btnPin9,btnPin0;
    private Button btnPinDel;
    private Button btnPinSpace;
    private ImageView imgCode1,imgCode2,imgCode3,imgCode4;

    private int pinSize = 4;
    private int pinCheckCorrect = 0; //4 Correct
    private char pinResult[] = new char[pinSize];

    private String parameter_apply = "";


    //private char pinTempAraay[] = new char[pinSize];


    private ArrayList<Character> pinCheckConfirm = new ArrayList<>();
    private ArrayList<Character> pinInput = new ArrayList<>();
    private ArrayList<Character> pinOld = new ArrayList<>();
    private ArrayList<ImageView> imagePin = new ArrayList<>();

    private int mode = 0; //0 apply , 1 add , 3 change


    public static final String PIN_NUMBER = "pin_number";
    public static final String PIN_MODE = "pin_mode";
    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public static int pin_apply = 0;
    public static int pin_add = 1;
    public static int pin_change = 3;

    public static int REQUEST_CODE = 101;

    public static final String PIN_RESULT = "PIN_RESULT";
    private String strPinInput;
    private String strEmployeeNumber;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        myDbHelper = new MyDbHelper(PinCodeActivity.this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        bindWidGet();

        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Bundle bundle = getIntent().getExtras();
        onSetMode(bundle);
        imgSetList();


        //myTestPinCustomResult();

        buttonOnClick();

        //testXml();


    }

    private void onSetMode(Bundle bundle) {
        if (bundle != null) {
            mode = bundle.getInt(PIN_MODE);
        }
//        mode = pin_apply;
        if (mode == pin_add) {
            if (bundle != null) {
                strEmployeeNumber = bundle.getString(MyAppConfig.employee_id);
            }else{
                Toast.makeText(this, "Program Error No employee number", Toast.LENGTH_SHORT).show();
                finish();
            }
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_add_input));
        } else if (mode == pin_change) {
            //tvMessagePin.setText(getResources().getString(R.string.txt_pin_new));
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_input));
            onIninitPin();
        } else if (mode == pin_apply) {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_input));
            onIninitPin();
        }
    }

    private void onIninitPin() {
        String pin = sharedPreferences.getString(MyAppConfig.pin_code, "");
        Log.d("pin => ", pin);
        if (!pin.equals("")) {
            if (pin.length() == pinSize) {
                for (int i = 0; i < pin.length(); i++) {
                    pinResult[i] = pin.charAt(i);
                }
            } else {
                Log.d("pin => ", " Pin Not From :" + pin);
                //finish();
            }
        } else { // value is PinCodeStatic is Not value

            if (pin.length() == pinSize) {
                for (int i = 0; i < pin.length(); i++) {
                    pinResult[i] = pin.charAt(i);
                }
            }
        }

    }

//    private void myTestPinCustomResult() {//TODO งงในงง เขียนไรวะ
//        PinCodeStatic.setPinNumber("2010");
//        String license_select = PinCodeStatic.getPinNumber();
//        if (license_select != null) {
//            if (license_select.length() == 4) {
//                for (int i = 0 ; i < license_select.length(); i++) {
//                    pinResult[i] = license_select.charAt(i);
//                }
//            }
//            Log.d("pin result test = > ", Arrays.toString(pinResult));
//        } else { // value is PinCodeStatic is Not value
//            if (license_select.length() == 4) {
//                for (int i = 0 ; i < license_select.length(); i++) {
//                    pinResult[i] = license_select.charAt(i);
//                }
//            }
//
//
//            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void imgSetList() {
        imagePin.add(imgCode1);
        imagePin.add(imgCode2);
        imagePin.add(imgCode3);
        imagePin.add(imgCode4);
    }

    private void buttonOnClick() {
        btnPin0.setOnClickListener(this);
        btnPin1.setOnClickListener(this);
        btnPin2.setOnClickListener(this);
        btnPin3.setOnClickListener(this);
        btnPin4.setOnClickListener(this);
        btnPin5.setOnClickListener(this);
        btnPin6.setOnClickListener(this);
        btnPin7.setOnClickListener(this);
        btnPin8.setOnClickListener(this);
        btnPin9.setOnClickListener(this);

        btnPinDel.setOnClickListener(this);
        btnPinSpace.setOnClickListener(this);


    }

    private void bindWidGet() {
        btnPin0 = (Button) findViewById(R.id.btnPin0);
        btnPin1 = (Button) findViewById(R.id.btnPin1);
        btnPin2 = (Button) findViewById(R.id.btnPin2);
        btnPin3 = (Button) findViewById(R.id.btnPin3);
        btnPin4 = (Button) findViewById(R.id.btnPin4);
        btnPin5 = (Button) findViewById(R.id.btnPin5);
        btnPin6 = (Button) findViewById(R.id.btnPin6);
        btnPin7 = (Button) findViewById(R.id.btnPin7);
        btnPin8 = (Button) findViewById(R.id.btnPin8);
        btnPin9 = (Button) findViewById(R.id.btnPin9);

        btnPinDel = (Button) findViewById(R.id.btnPinDel);
        btnPinSpace = (Button) findViewById(R.id.btnPinSpace);
        imgCode1 = (ImageView) findViewById(R.id.imgCode1);
        imgCode2 = (ImageView) findViewById(R.id.imgCode2);
        imgCode3 = (ImageView) findViewById(R.id.imgCode3);
        imgCode4 = (ImageView) findViewById(R.id.imgCode4);

        tvTitlePin = (TextView) findViewById(R.id.txtPinTitle);
        imgPinTitle = (ImageView) findViewById(R.id.imgPinTitle);
        tvMessagePin = (TextView) findViewById(R.id.tvMessagePin);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPin1) {
            processSetPin('1');
        } else if (v == btnPin2) {
            processSetPin('2');
        } else if (v == btnPin3) {
            processSetPin('3');
        } else if (v == btnPin4) {
            processSetPin('4');
        } else if (v == btnPin5) {
            processSetPin('5');
        } else if (v == btnPin6) {
            processSetPin('6');
        } else if (v == btnPin7) {
            processSetPin('7');
        } else if (v == btnPin8) {
            processSetPin('8');
        } else if (v == btnPin9) {
            processSetPin('9');
        } else if (v == btnPin0) {
            processSetPin('0');
        } else if (v == btnPinDel) {
            processDeletePin();
        } else if (v == btnPinSpace) {
            processClearPin();
        }

        if (pinInput.size() == pinSize) {
            processCheckPinCode();
        }
    }

    private void processCheckPinCode() {

        if (mode == pin_add) { // 1 add
            if (pinCheckConfirm.size() == 0) {
                tvMessagePin.setText(getResources().getString(R.string.txt_pin_confirm));
                setPinConfirm();
            }else{
                pinConfirmMode();
            }

        } else if (mode == pin_change) { // 3 change

            if (parameter_apply.equals("")) {
                pinApply();
            } else {
                if (pinCheckConfirm.size() == 0) {
                    setPinConfirm();
                    tvMessagePin.setText(getResources().getString(R.string.txt_pin_confirm));
                }else{
                    pinConfirmMode();
                }
            }

        } else {
            if (mode == pin_apply) { // 0 apply
                pinApply();
            }
        }

        processClearPin();
        pinCheckCorrect = 0;

    }

    private void setPinConfirm() {
        pinCheckConfirm = new ArrayList<>(pinInput);
    }

    private void pinApply() {
        strPinInput = "";
        for(int i = 0 ; i < pinInput.size() ; i++) {
            if (pinInput.get(i).equals(pinResult[i])) {
                pinCheckCorrect++;
            }
            strPinInput = strPinInput + pinInput.get(i);
        }
        pinCheckResult();
    }

    private void pinCheckResult() {

        if (pinCheckCorrect == pinSize) {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_correct));
            if (mode == pin_add) {
                onInsertPinData();
            } else if (mode == pin_change) {
                onEditPinData();
            } else if (mode == pin_apply) {
                onPinApply();
            }

        } else {
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_error));
            //Toast.makeText(this, "Not Correct", Toast.LENGTH_SHORT).show();
        }

    }

    private void onEditPinData() {

        if (parameter_apply.equals("")) {
            parameter_apply = strPinInput;
            tvMessagePin.setText(getResources().getString(R.string.txt_pin_new));
        } else {
            String strIdEmployee = sharedPreferences.getString(MyAppConfig.employee_id, "");
            if (!strIdEmployee.equals("")) {
                editor.putString(MyAppConfig.pin_code, strPinInput);
                editor.commit();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseUser.COL_PIN_CODE, strPinInput);
                String[] whereValue = {strIdEmployee};
                sqLiteDatabase.update(DatabaseUser.TABLE_NAME, contentValues, DatabaseUser.COL_EMPLOYEE_ID + " = ? ",whereValue);
                Toast.makeText(this, "Pin New is " + strPinInput, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d("Program => ", "Error");
                Toast.makeText(this, "Program Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onPinApply() {
        //Toast.makeText(this, "Process Insert ", Toast.LENGTH_SHORT).show();
        //Insert Data
        Intent intent = new Intent();
        intent.putExtra(PIN_RESULT, strPinInput);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void onInsertPinData() {
        //Toast.makeText(this, "Process Insert ", Toast.LENGTH_SHORT).show();
        //Insert Data
        editor.putString(MyAppConfig.pin_code, strPinInput);
        editor.commit();
        Intent intent = new Intent();
        intent.putExtra(PIN_RESULT, strPinInput);
        setResult(RESULT_OK, intent);
        finish();

    }


    private void pinConfirmMode() {
        strPinInput = "";
        for(int i = 0 ; i < pinInput.size() ; i++) {
            if (pinInput.get(i).equals(pinCheckConfirm.get(i))) {
                strPinInput = strPinInput + pinInput.get(i);
                pinCheckCorrect++;
//                Log.d("pinInput => ", pinInput.get(i) + " ");
//                Log.d("pinCheckConfirm => ", pinCheckConfirm.get(i) + "");
            }
        }
        //Toast.makeText(this, "pinCheckCorrect : " + pinCheckCorrect, Toast.LENGTH_SHORT).show();
        pinCheckResult();
    }

    private void cusGetPinFromDatabase() {
//        String strSql = "SELECT * FROM  " + DatabaseUser.TABLE_NAME + " WHERE" ;
//        Cursor cursor = sqLiteDatabase.rawQuery(strSql, null);
        SharedPreferences sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(MyAppConfig.pin_code, "");

    }

    private void processDeletePin() {

        if (pinInput.size() > 0) {
            pinInput.remove(pinInput.size() - 1);
            imagePin.get(pinInput.size()).setImageResource(R.drawable.custom_circle_pin);
//            Log.d("pinInput => ", pinInput + " ");
//            Log.d("pinSize => ", pinInput.size() + "");
        }
    }

    private void processSetPin(Character pinCode) {

        if (pinInput.size() < pinSize) {
            pinInput.add(pinCode);
            imagePin.get(pinInput.size() - 1).setImageResource(R.drawable.custom_circle_pin2);
//            Log.d("pinInput => ", pinInput + " ");
//            Log.d("pinSize => ", pinInput.size() + "");
        }
    }

    private void processClearPin() {

        if (pinInput.size() != 0) {
            for (int i = 0; i < pinInput.size(); i++) {
                imagePin.get(i).setImageResource(R.drawable.custom_circle_pin);
            }
        }
        pinInput.clear();
//        Log.d("pinInput => ", pinInput + " ");
//        Log.d("pinSize => ", pinInput.size() + "");
    }

    @Override
    public void onBackPressed() {
        if (mode == pin_apply) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
        } else {
            //super.onBackPressed();
        }
        super.onBackPressed();
    }
}
