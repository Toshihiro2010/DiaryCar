package com.stecon.patipan_on.diarycar;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PriceOtherActivity extends AppCompatActivity {

    private Spinner spinnerPriceType;
    private EditText edtTitle;
    private EditText edtPrice;
    private EditText edtNoteDetail;
    private Button btnSavePriceOther;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_other);



        bindWidget();

        mySetSpinner();
    }

    private void mySetSpinner() {
        ArrayAdapter<CharSequence> priceTypeArrayAdapter = ArrayAdapter.createFromResource(PriceOtherActivity.this, R.array.price_other_array, R.layout.support_simple_spinner_dropdown_item);
        spinnerPriceType.setAdapter(priceTypeArrayAdapter);

    }

    private void bindWidget() {
        spinnerPriceType = (Spinner) findViewById(R.id.spinnerPriceType);
        edtTitle = (EditText) findViewById(R.id.edtTitlePrice);
        edtPrice = (EditText) findViewById(R.id.edtPriceOther);
        edtNoteDetail = (EditText) findViewById(R.id.edtOtherNote);
        btnSavePriceOther = (Button) findViewById(R.id.btnSavePriceOther);
    }
}
