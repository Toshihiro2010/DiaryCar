package com.stecon.patipan_on.diarycar.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stecon.patipan_on.diarycar.PriceOtherFragment;
import com.stecon.patipan_on.diarycar.ServiceFragment;
import com.stecon.patipan_on.diarycar.custom_fragment.FuelFragment;
import com.stecon.patipan_on.diarycar.custom_fragment.OneFragment;

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
            return fuelFragment.newInstance();
        } else if (position == 1) {
            PriceOtherFragment priceOtherFragment = new PriceOtherFragment();
            return priceOtherFragment.newInstance();
        } else if (position == 2) {
            ServiceFragment serviceFragment = new ServiceFragment();
            return serviceFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
