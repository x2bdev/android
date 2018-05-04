package com.example.hoxyu.team3_weatherapp_final;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_ListView;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.list;
import com.example.hoxyu.team3_weatherapp_final.utils.OpenWeatherMapAPI;
import com.example.hoxyu.team3_weatherapp_final.utils.TypePrediction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Weather_Future2_Activity extends AppCompatActivity {
    private ArrayList<list> longTermWeather ;
    private ArrayList<list> longTermTodayWeather ;
    private ArrayList<list> longTermTomorrowWeather ;

    ListView lv_today,lv_tomorrow,lv_later;

    Adapter_ListView adapter_listView_today,adapter_listView_tomorrow,adapter_listView_later;


    int id_city;
    ProgressDialog myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather__future2_);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        id_city=bundle.getInt("ID_CITY");
//        myProgress = new ProgressDialog(this);
//        myProgress.setTitle("Đang Tải Dữ Liệu");
//        myProgress.setMessage("Vui lòng chờ...");
//        myProgress.setCancelable(true);
//        myProgress.show();
        WeatherFuture_AsyncTask task=new WeatherFuture_AsyncTask(this,id_city);
        task.execute();
        //myProgress.dismiss();
        addControls();

    }

    private void addControls() {
//        longTermWeather=new ArrayList<>();
//        longTermTodayWeather=new ArrayList<>();
//        longTermTomorrowWeather=new ArrayList<>();

        final TabHost tabhost=findViewById(R.id.tab_host);
        tabhost.setup();
        TabHost.TabSpec tab1;

        tab1=tabhost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("TONAY");
        tabhost.addTab(tab1);

        TabHost.TabSpec tab2;
        tab2=tabhost.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("TOMORROW");
        tabhost.addTab(tab2);

        TabHost.TabSpec tab3;
        tab3=tabhost.newTabSpec("tab3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("LATER");
        tabhost.addTab(tab3);
        tabhost.setCurrentTab(0);

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String s="Tab tag ="+tabId +"; index ="+
                        tabhost.getCurrentTab();
                Toast.makeText(getApplicationContext(),
                        s, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getOpenWeatherJson(OpenWeatherJSon openWeatherJSon){

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
        adapter_listView_today=new Adapter_ListView(Weather_Future2_Activity.this,R.layout.list_row,longTermTodayWeather);
        adapter_listView_tomorrow=new Adapter_ListView(Weather_Future2_Activity.this,R.layout.list_row,longTermTomorrowWeather);
        adapter_listView_later=new Adapter_ListView(Weather_Future2_Activity.this,R.layout.list_row,longTermWeather);
        Toast.makeText(this,"Tonay: "+ longTermTodayWeather.size()+"\n Tomorrow: "+longTermTomorrowWeather.size()+"\n Tomorrow: "+longTermWeather.size(), Toast.LENGTH_SHORT).show();

        lv_today=findViewById(R.id.lv_Today);
        lv_tomorrow=findViewById(R.id.lv_Tomorrow);
        lv_later=findViewById(R.id.lv_Later);
        adapter_listView_today=new Adapter_ListView(Weather_Future2_Activity.this,R.layout.list_row,longTermTodayWeather);
        adapter_listView_tomorrow=new Adapter_ListView(Weather_Future2_Activity.this,R.layout.list_row,longTermTomorrowWeather);
        adapter_listView_later=new Adapter_ListView(Weather_Future2_Activity.this,R.layout.list_row,longTermWeather);
        lv_today.setAdapter(adapter_listView_today);
        lv_tomorrow.setAdapter(adapter_listView_tomorrow);
        lv_later.setAdapter(adapter_listView_later);
    }

    class WeatherFuture_AsyncTask extends AsyncTask<String,Void,OpenWeatherJSon> {
        int id_city;
        Activity activity;
        TypePrediction typePrediction;


        public WeatherFuture_AsyncTask(Activity activity,int id_city) {
            this.typePrediction= TypePrediction.ID_CITY;
            this.id_city=id_city;
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
            if(typePrediction== TypePrediction.ID_CITY)
                openWeatherJSon= OpenWeatherMapAPI.prediction(id_city);
            return openWeatherJSon;
        }
    }
}
