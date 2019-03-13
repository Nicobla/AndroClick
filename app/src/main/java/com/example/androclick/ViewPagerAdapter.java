package com.example.androclick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Bundle data;

    public ViewPagerAdapter(FragmentManager fm, Bundle data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new MakeTacos_1();
                break;
            case 1:
                fragment = new MakeTacos_2();
                break;
            case 2:
                fragment = new MakeTacos_3();
                break;
            case 3:
                fragment = new MakeTacos_4();
                break;
            case 4:
                fragment = new MakeTacos_5();
                break;
        }

        fragment.setArguments(data);

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
