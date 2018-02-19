package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.stecon.patipan_on.diarycar.controller.CustomViewPagerAdapter;
import com.stecon.patipan_on.diarycar.model.MyAppConfig;

public class PriceAllActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionMenu floatingActionMenu;
    private com.github.clans.fab.FloatingActionButton floatFuelActionButton;

    private com.github.clans.fab.FloatingActionButton floatCostActionButton;
    private com.github.clans.fab.FloatingActionButton floatServiceActionButton;

    private Toolbar toolbar;
    private ViewPager viewPager;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String licensePlate;

    private int current_position = -1;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_all);
        bindWidGet();
        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        licensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        editor = sharedPreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Price All");
        toolbar.setSubtitle(licensePlate);
        setSupportActionBar(toolbar);



        onSetUpFloatAction();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customViewPagerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (current_position != -1) {
//            viewPager.setCurrentItem(current_position);
//        }
    }

    private void onSetUpFloatAction() {
        floatingActionMenu.setClosedOnTouchOutside(true);
        floatFuelActionButton.setOnClickListener(this);
        floatCostActionButton.setOnClickListener(this);
        floatServiceActionButton.setOnClickListener(this);

    }

    private void bindWidGet() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.listPager);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        floatFuelActionButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.float_button_menu_fuel);
        floatCostActionButton = (FloatingActionButton) findViewById(R.id.float_button_menu_cost);
        floatServiceActionButton = (FloatingActionButton) findViewById(R.id.float_button_menu_service);
    }

    public Fragment getActiveFragment(ViewPager viewPager, int position) {

        String name = "android:switcher:" + viewPager.getId() + ":" + position;
        return getSupportFragmentManager().findFragmentByTag(name);
    }

    @Override
    public void onClick(View v) {
        if (v == floatFuelActionButton) {
            Intent intent = new Intent(PriceAllActivity.this, OilJournalActivity.class);
            startActivity(intent);

        } else if (v == floatCostActionButton) {
            Intent intent = new Intent(PriceAllActivity.this, PriceCostJournalActivity.class);
            startActivity(intent);

        } else if (v == floatServiceActionButton) {
            Intent intent = new Intent(PriceAllActivity.this, ServiceJournalActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        current_position = viewPager.getCurrentItem();
    }
}
