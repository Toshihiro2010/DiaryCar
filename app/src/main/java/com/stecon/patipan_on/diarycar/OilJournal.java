package com.stecon.patipan_on.diarycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class OilJournal extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText edtDistanceOil;
    private EditText edtLitMoney;
    private EditText edtMoneyTotal;

    private Button btnOilSave;

    private Spinner spinnerOil;
    private Spinner spinnerMoneyPay;

    private CheckBox chkOilFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_journal);

        bindWidget();

        mySetSpinner();
        mySpinnerSelect();


        chkOilFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(OilJournal.this, "True", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOilSave.setOnClickListener(this);


    }

    private void mySpinnerSelect() {
        spinnerOil.setOnItemSelectedListener(this);
        spinnerMoneyPay.setOnItemSelectedListener(this);
    }


    private void mySetSpinner() {
        ArrayAdapter<CharSequence> myList = ArrayAdapter.createFromResource(this, R.array.oil_array, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> monneyPayType = ArrayAdapter.createFromResource(this, R.array.money_pay_array, R.layout.support_simple_spinner_dropdown_item);

        spinnerOil.setAdapter(myList);
        spinnerMoneyPay.setAdapter(monneyPayType);
    }

    private void bindWidget() {
        edtDistanceOil = (EditText) findViewById(R.id.edtDistanceOil);
        edtLitMoney = (EditText) findViewById(R.id.edtLitMoney);
        edtMoneyTotal = (EditText) findViewById(R.id.edtMoneyTotal);
        spinnerOil = (Spinner) findViewById(R.id.spinnerOil);
        btnOilSave = (Button) findViewById(R.id.btnOilSave);
        chkOilFull = (CheckBox) findViewById(R.id.chkOilFull);
        spinnerMoneyPay = (Spinner) findViewById(R.id.spinnerMoneyPay);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String str = spinnerOil.getSelectedItem().toString();
        //String str = parent.getSelectedItem().toString();
//        if (parent == spinnerMoneyPay) {
//            Toast.makeText(this, "Money", Toast.LENGTH_SHORT).show();
//        } else if (parent == spinnerOil) {
//            Toast.makeText(this, "Oil", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == btnOilSave) {
            if (chkOilFull.isChecked()) {
                Toast.makeText(OilJournal.this, "Full Check", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
