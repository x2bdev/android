package com.example.hoxyu.team3_weatherapp_final.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hoxyu.team3_weatherapp_final.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ViewPager extends FragmentPagerAdapter {

    private List<RecyclerViewFragment> fragmentArrayList=new ArrayList<>();
    private List<String> fragmentTitleList=new ArrayList<>();

    public Adapter_ViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                Bundle bundleToday = new Bundle();
                bundleToday.putInt("day",0);
                RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
                recyclerViewFragmentToday.setArguments(bundleToday);
                return  recyclerViewFragmentToday;
            }
            case 1: {
                Bundle bundleToday = new Bundle();
                bundleToday.putInt("day",3);
                RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
                recyclerViewFragmentToday.setArguments(bundleToday);
                return  recyclerViewFragmentToday;
            }
            case 2: {
                Bundle bundleToday = new Bundle();
                bundleToday.putInt("day",2);
                RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
                recyclerViewFragmentToday.setArguments(bundleToday);
                return  recyclerViewFragmentToday;
            }
            default:
                 return fragmentArrayList.get(position);
        }
        //return
    }

    @Override
    public int getCount() {
        return fragmentTitleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
    public void addFragment(RecyclerViewFragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitleList.add(title);
    }
    public void delFragment(RecyclerViewFragment fragment, String title){
        fragmentArrayList.remove(fragment);
        fragmentTitleList.remove(title);
    }

}
