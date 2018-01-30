package com.stecon.patipan_on.diarycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class PinCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTestPinCode;
    private EditText editText;

    private Button btnPin1,btnPin2,btnPin3,btnPin4,btnPin5,btnPin6,btnPin7,btnPin8,btnPin9,btnPin0;
    private Button btnPinDel;
    private Button btnPinSpace;
    private ImageView imgCode1,imgCode2,imgCode3,imgCode4;

    private int pinSize = 4;
    private int pinCheckCorrect = 0; //4 Correct
    private char pinResult[] =  {'2','0','1','0'};

    private ArrayList<Character> pinTemp = new ArrayList<>();
    private ArrayList<Character> pinInput = new ArrayList<>();
    private ArrayList<ImageView> imagePin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        bindWidGet();
        imgSetList();
        buttonOnClick();

//        String testResult = "1234";
//        String[] temp = testResult.split("");
//        Log.d("temp => ", Arrays.toString(temp));


    }

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

        for(int i = 0 ; i < pinInput.size() ; i++) {
            if (pinInput.get(i).equals(pinResult[i])) {
                pinCheckCorrect++;
            }
        }
        Log.d("pin check = > ", pinCheckCorrect + " ");

        if (pinCheckCorrect == pinSize) {
            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Correct", Toast.LENGTH_SHORT).show();
        }
        processClearPin();
        pinCheckCorrect = 0;

    }

    private void processDeletePin() {

        if (pinInput.size() > 0) {
            pinInput.remove(pinInput.size() - 1);
            imagePin.get(pinInput.size()).setImageResource(R.drawable.custom_circle_pin);
            Log.d("pinInput => ", pinInput + " ");
            Log.d("pinSize => ", pinInput.size() + "");
        }
    }

    private void processSetPin(Character pinCode) {

        if (pinInput.size() < pinSize) {
            pinInput.add(pinCode);
            imagePin.get(pinInput.size() - 1).setImageResource(R.drawable.custom_circle_pin2);
            Log.d("pinInput => ", pinInput + " ");
            Log.d("pinSize => ", pinInput.size() + "");
        }
    }

    private void processClearPin() {

        if (pinInput.size() != 0) {
            for (int i = 0; i < pinInput.size(); i++) {
                imagePin.get(i).setImageResource(R.drawable.custom_circle_pin);
            }
            pinInput.clear();

            Log.d("pinInput => ", pinInput + " ");
            Log.d("pinSize => ", pinInput.size() + "");
        }
    }


}
