package com.stecon.patipan_on.diarycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PriceOtherActivity extends AppCompatActivity {

    private Spinner spinnerPriceType;

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
    }
}
