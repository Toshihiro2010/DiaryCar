package com.stecon.patipan_on.diarycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    private CustomViewPagerAdapter customViewPagerAdapter;

    private int current_position = -1;
    private BottomNavigationView navigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == R.id.navigation_fuel) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (item.getItemId() == R.id.navigation_price_cost) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (item.getItemId() == R.id.navigation_service) {
                viewPager.setCurrentItem(2);
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_all);
        bindWidGet();
        sharedPreferences = getSharedPreferences(MyAppConfig.P_NAME, Context.MODE_PRIVATE);
        licensePlate = sharedPreferences.getString(MyAppConfig.licensePlate, "");
        editor = sharedPreferences.edit();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Price All");
        toolbar.setSubtitle(licensePlate);
        setSupportActionBar(toolbar);
        onSetUpFloatAction();

        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());




    }

    public void onCustomSetNavigationView(int position) {
        navigationView.setSelectedItemId(position);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.close(true);
        }

        viewPager.setAdapter(customViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    navigationView.setSelectedItemId(R.id.navigation_fuel);
                } else if (position == 1) {
                    navigationView.setSelectedItemId(R.id.navigation_price_cost);
                } else if (position == 2) {
                    navigationView.setSelectedItemId(R.id.navigation_service);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Toast.makeText(PriceAllActivity.this, "position => " + position, Toast.LENGTH_SHORT).show();
            }
        });


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
        navigationView = findViewById(R.id.navigation);
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
