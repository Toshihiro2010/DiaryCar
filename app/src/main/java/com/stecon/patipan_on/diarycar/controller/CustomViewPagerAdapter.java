package com.stecon.patipan_on.diarycar.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.stecon.patipan_on.diarycar.custom_fragment.PriceOtherFragment;
import com.stecon.patipan_on.diarycar.custom_fragment.ServiceFragment;
import com.stecon.patipan_on.diarycar.custom_fragment.FuelFragment;

/**
 * Created by patipan_on on 2/8/2018.
 */

public class CustomViewPagerAdapter extends FragmentPagerAdapter{

    private final int PAGE_NUM = 3;


    public CustomViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            FuelFragment fuelFragment = new FuelFragment();
            Log.d("fuel => ", fuelFragment.newInstance() + " ");
            return fuelFragment.newInstance();
        } else if (position == 1) {
            PriceOtherFragment priceOtherFragment = new PriceOtherFragment();

            Log.d("priceother => ", priceOtherFragment.newInstance() + " ");
            return priceOtherFragment.newInstance();
        } else if (position == 2) {
            ServiceFragment serviceFragment = new ServiceFragment();

            Log.d("service => ", serviceFragment.newInstance() + " ");
            return serviceFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }


}
