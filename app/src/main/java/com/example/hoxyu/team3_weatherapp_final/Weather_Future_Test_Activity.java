package com.example.hoxyu.team3_weatherapp_final;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_ListView;
import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_RecyclerView;
import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_ViewPager;
import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_ViewPager2;
import com.example.hoxyu.team3_weatherapp_final.fragments.ListViewFragment;
import com.example.hoxyu.team3_weatherapp_final.fragments.RecyclerViewFragment;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.list;
import com.example.hoxyu.team3_weatherapp_final.utils.OpenWeatherMapAPI;
import com.example.hoxyu.team3_weatherapp_final.utils.TypePrediction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Weather_Future_Test_Activity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Adapter_ViewPager2 adapter_viewPager= new Adapter_ViewPager2(getSupportFragmentManager());



    private List<list> longTermWeather ;
    private List<list> longTermTodayWeather ;
    private List<list> longTermTomorrowWeather ;

    double Latitude,Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather__future__test_);
        tabLayout=findViewById(R.id.id_tablayout);
        viewPager=findViewById(R.id.id_viewpager);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Latitude = (double) bundle.get("Latitude");
        Longitude = (double) bundle.get("Longitude");
        parseURL();
        //updateUI();


    }
    private void updateUI() {


//        Bundle bundleToday = new Bundle();
//        bundleToday.putInt("day",0);
//        ListViewFragment listViewFragmentToday = new ListViewFragment();
//        listViewFragmentToday.setArguments(bundleToday);
//        adapter_viewPager.addFragment(listViewFragmentToday, "Today");
//
//        Bundle bundle_Tomorrow = new Bundle();
//        bundle_Tomorrow.putInt("day",1);
//        ListViewFragment listViewFragmentTomorrow = new ListViewFragment();
//        listViewFragmentTomorrow.setArguments(bundle_Tomorrow);
//        adapter_viewPager.addFragment(listViewFragmentTomorrow, "Tomorrow");
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("day",2);
//        ListViewFragment listViewFragmentLater = new ListViewFragment();
//        listViewFragmentLater.setArguments(bundle);
//        adapter_viewPager.addFragment(listViewFragmentLater, "Later");

//        Bundle bundleToday1 = new Bundle();
//        bundleToday1.putInt("day",1);
//        RecyclerViewFragment recyclerViewFragmentToday1 = new RecyclerViewFragment();
//        recyclerViewFragmentToday1.setArguments(bundleToday1);
//        adapter_viewPager.addFragment(recyclerViewFragmentToday1, "More");

//        adapter_viewPager.notifyDataSetChanged();
//        viewPager.setAdapter(adapter_viewPager);
//        tabLayout.setupWithViewPager(viewPager);
//
//        viewPager.setCurrentItem(0, false);
//        adapter_viewPager.notifyDataSetChanged();

        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //onTabSelectedListener(viewPager);



    }
    private void parseURL() {
        Weather_Future_Test_Activity.WeatherFuture_AsyncTask weatherFuture_asyncTask=new Weather_Future_Test_Activity.WeatherFuture_AsyncTask(this,Latitude,Longitude);
        weatherFuture_asyncTask.execute();
    }
    void getOpenWeatherJson2(OpenWeatherJSon openWeatherJSon){

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
        Bundle bundleToday = new Bundle();
        bundleToday.putSerializable("ListWeather", (Serializable) longTermTodayWeather);
        //bundleToday.putc
        ListViewFragment listViewFragmentToday = new ListViewFragment();
        listViewFragmentToday.setArguments(bundleToday);
        adapter_viewPager.addFragment(listViewFragmentToday, "Today");

        Bundle bundle_Tomorrow = new Bundle();
        bundle_Tomorrow.putSerializable("ListWeather", (Serializable) longTermTomorrowWeather);
        ListViewFragment listViewFragmentTomorrow = new ListViewFragment();
        listViewFragmentTomorrow.setArguments(bundle_Tomorrow);
        adapter_viewPager.addFragment(listViewFragmentTomorrow, "Tomorrow");

        Bundle bundle = new Bundle();
        bundle.putSerializable("ListWeather", (Serializable) longTermWeather);
        ListViewFragment listViewFragmentLater = new ListViewFragment();
        listViewFragmentLater.setArguments(bundle);
        adapter_viewPager.addFragment(listViewFragmentLater, "Later");
        adapter_viewPager.notifyDataSetChanged();
        viewPager.setAdapter(adapter_viewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0, false);
        adapter_viewPager.notifyDataSetChanged();
    }

    public Adapter_ListView getAdapter2(int id) {
        Adapter_ListView adapter_listView = null;
//        if (id == 0) {
//             adapter_listView = new Adapter_ListView(this,R.layout.list_row, (ArrayList<list>) longTermTodayWeather);
//        }
//        if (id == 1) {
//             adapter_listView = new Adapter_ListView(this,R.layout.list_row, (ArrayList<list>) longTermTomorrowWeather);
//        }
//        if(id == 2){
//             adapter_listView = new Adapter_ListView(this,R.layout.list_row, (ArrayList<list>) longTermWeather);
//        }
        return adapter_listView = new Adapter_ListView(this,R.layout.list_row, (ArrayList<list>) longTermTodayWeather);
    }
    class WeatherFuture_AsyncTask extends AsyncTask<String,Void,OpenWeatherJSon> {
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

            getOpenWeatherJson2(openWeatherJSon);
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
}
