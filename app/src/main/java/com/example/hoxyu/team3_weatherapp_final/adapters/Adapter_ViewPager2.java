package com.example.hoxyu.team3_weatherapp_final.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;

import com.example.hoxyu.team3_weatherapp_final.fragments.ListViewFragment;
import com.example.hoxyu.team3_weatherapp_final.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ViewPager2 extends FragmentPagerAdapter {
    private List<ListViewFragment> fragmentArrayList=new ArrayList<>();
    private List<String> fragmentTitleList=new ArrayList<>();

    public Adapter_ViewPager2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
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

    public void addFragment(ListViewFragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitleList.add(title);
    }
}
