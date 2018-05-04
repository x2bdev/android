package com.example.hoxyu.team3_weatherapp_final;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_RecyclerView;
import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_ViewPager;
import com.example.hoxyu.team3_weatherapp_final.fragments.RecyclerViewFragment;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.list;
import com.example.hoxyu.team3_weatherapp_final.utils.OpenWeatherMapAPI;
import com.example.hoxyu.team3_weatherapp_final.utils.TypePrediction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Weather_FutureActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Adapter_ViewPager adapter_viewPager= new Adapter_ViewPager(getSupportFragmentManager());



     List<list> longTermWeather ;
     List<list> longTermTodayWeather ;
     List<list> longTermTomorrowWeather ;

    double Latitude,Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather__future);

        tabLayout=findViewById(R.id.id_tablayout);
        viewPager=findViewById(R.id.id_viewpager);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
         Latitude = (double) bundle.get("Latitude");
         Longitude = (double) bundle.get("Longitude");


        parseURL();
        updateUI();
        //updateUI();
    }

    private void updateUI() {

       // Toast.makeText(this,"Tonay: "+ longTermTodayWeather.size()+"\n Tomorrow: "+longTermTomorrowWeather.size()+"\n Tomorrow: "+longTermWeather.size(), Toast.LENGTH_SHORT).show();

        Bundle bundleToday = new Bundle();
        bundleToday.putInt("day",0);
        RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
        recyclerViewFragmentToday.setArguments(bundleToday);
        adapter_viewPager.addFragment(recyclerViewFragmentToday, "Today");

        Bundle bundle_Tomorrow = new Bundle();
        bundle_Tomorrow.putInt("day",1);
        RecyclerViewFragment recyclerViewFragmentTomorrow = new RecyclerViewFragment();
        recyclerViewFragmentTomorrow.setArguments(bundle_Tomorrow);
        adapter_viewPager.addFragment(recyclerViewFragmentTomorrow, "Tomorrow");

        Bundle bundle = new Bundle();
        bundle.putInt("day",2);
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        recyclerViewFragment.setArguments(bundle);
        adapter_viewPager.addFragment(recyclerViewFragment, "Later");

        Bundle bundleToday1 = new Bundle();
        bundleToday1.putInt("day",1);
        RecyclerViewFragment recyclerViewFragmentToday1 = new RecyclerViewFragment();
        recyclerViewFragmentToday1.setArguments(bundleToday1);
        adapter_viewPager.addFragment(recyclerViewFragmentToday1, "More");

        adapter_viewPager.notifyDataSetChanged();
        viewPager.setAdapter(adapter_viewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0, false);
        adapter_viewPager.notifyDataSetChanged();

        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //onTabSelectedListener(viewPager);



    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener( final ViewPager viewPager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }


    private void parseURL() {
        WeatherFuture_AsyncTask weatherFuture_asyncTask=new WeatherFuture_AsyncTask(this,Latitude,Longitude);
        weatherFuture_asyncTask.execute();
    }
    void getOpenWeatherJson(OpenWeatherJSon openWeatherJSon){

        longTermWeather = new ArrayList<>();
        longTermTodayWeather = new ArrayList<>();
        longTermTomorrowWeather = new ArrayList<>();


        List<list> list =new ArrayList<>();
        for(int i=0;i<openWeatherJSon.getList().size();i++){
            list.add(openWeatherJSon.getList().get(i));
        }
        for (com.example.hoxyu.team3_weatherapp_final.model.list temp:list) {
            final String dateMsString = temp.getDt() + "000";
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(dateMsString));

            Calendar today = Calendar.getInstance();
            if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                longTermTodayWeather.add(temp);
            } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
                longTermTomorrowWeather.add(temp);
            } else {
                longTermWeather.add(temp);
            }
        }
        //Toast.makeText(this,"Tonay: "+ longTermTodayWeather.size()+"\n Tomorrow: "+longTermTomorrowWeather.size()+"\n Tomorrow: "+longTermWeather.size(), Toast.LENGTH_SHORT).show();
    }

    public Adapter_RecyclerView getAdapter(int id) {
        Adapter_RecyclerView weatherRecyclerAdapter = null;
        //Toast.makeText(this,"Tonay: "+ longTermTodayWeather.size()+"\n Tomorrow: "+longTermTomorrowWeather.size()+"\n Tomorrow: "+longTermWeather.size(), Toast.LENGTH_SHORT).show();
        if (id == 0) {
            weatherRecyclerAdapter = new Adapter_RecyclerView(this, longTermTodayWeather);
        }
        if (id == 1) {
            weatherRecyclerAdapter = new Adapter_RecyclerView(this, longTermTomorrowWeather);
        } 
        if(id == 2){
            weatherRecyclerAdapter = new Adapter_RecyclerView(this, longTermWeather);
        }
        if(id == 3){
            weatherRecyclerAdapter = new Adapter_RecyclerView(this, longTermTomorrowWeather);
        }
        return weatherRecyclerAdapter;
    }
     public class WeatherFuture_AsyncTask extends AsyncTask<String,Void,OpenWeatherJSon>{
        double a,b;
        Activity activity;
        TypePrediction typePrediction;


        public WeatherFuture_AsyncTask(Activity activity,double a,double b) {
            this.typePrediction= TypePrediction.LATITUDE_LONGITUDE;
            this.a=a;
            this.b=b;
            this.activity=activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(OpenWeatherJSon openWeatherJSon) {
            super.onPostExecute(openWeatherJSon);

            getOpenWeatherJson(openWeatherJSon);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected OpenWeatherJSon doInBackground(String... strings) {

            OpenWeatherJSon openWeatherJSon=null;
            if(typePrediction== TypePrediction.LATITUDE_LONGITUDE)
                openWeatherJSon= OpenWeatherMapAPI.prediction(a,b);
            return openWeatherJSon;
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragmentArrayList=new ArrayList<>();
        private List<String> fragmentTitleList=new ArrayList<>();

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragmentArrayList.get(position);
                case 1:
                    return fragmentArrayList.get(position);
                case 2:
                    return fragmentArrayList.get(position);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragmentTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return fragmentTitleList.get(position);
                case 1:
                    return fragmentTitleList.get(position);
                case 2:
                    return fragmentTitleList.get(position);
                default:
                    return null;
            }
        }
        public void addFragment(Fragment fragment, String title){
            fragmentArrayList.add(fragment);
            fragmentTitleList.add(title);
        }
}}
