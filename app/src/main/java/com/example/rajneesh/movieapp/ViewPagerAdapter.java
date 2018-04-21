package com.example.rajneesh.movieapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAJNEESH on 14-04-2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> fragmentList= new ArrayList<>();
    private  final List<String> fragmentTitleList= new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void AddFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){return fragmentTitleList.get(position);}
}
