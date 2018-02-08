package com.stecon.patipan_on.diarycar.controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stecon.patipan_on.diarycar.PriceAllActivity2;
import com.stecon.patipan_on.diarycar.custom_fragment.FuelFragment;
import com.stecon.patipan_on.diarycar.custom_fragment.OneFragment;

/**
 * Created by patipan_on on 2/8/2018.
 */

public class CustomViewPagerAdapter extends FragmentPagerAdapter{

    private final int PAGE_NUM = 2;
    private Context context;


    public CustomViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            FuelFragment fuelFragment = new FuelFragment();
            return fuelFragment.newInstance();
        } else if (position == 1) {
            OneFragment oneFragment = new OneFragment();
            return oneFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
