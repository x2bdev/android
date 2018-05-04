package com.example.hoxyu.team3_weatherapp_final;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.utils.OpenWeatherMapAPI;
import com.example.hoxyu.team3_weatherapp_final.utils.TypePrediction;
import com.example.hoxyu.team3_weatherapp_final.utils.WeatherAsyncTask;

public class WeatherByAddressActivity extends AppCompatActivity {
    Button btn_moreInfo;
    int id_city;
    //int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__weather_);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        id_city=bundle.getInt("ID_CITY");
        //id=Integer.parseInt(id_city);
        //Toast.makeText(this, id_city+" :id", Toast.LENGTH_SHORT).show();
        getWeatherByID_city();

        addControls();
    }
    public void getWeatherByID_city() {
            WeatherAsyncTask task = new WeatherAsyncTask(this, id_city);
            task.execute();
    }
    private void addControls() {
        //set toolbar mới
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);

        btn_moreInfo=findViewById(R.id.btn_MoreInfo);
        btn_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("ID_CITY",id_city);
                Intent intent=new Intent(WeatherByAddressActivity.this,Weather_Future2_Activity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }
//    void getOpenWeatherJson(OpenWeatherJSon openWeatherJSon){
//        id_city=openWeatherJSon.getCity().getId();
//    }

//    public class WeatherByIDAsyncTask extends AsyncTask<String,Void,OpenWeatherJSon>{
//        int id;
//        Activity activity;
//        TypePrediction typePrediction;
//
//
//        public WeatherByIDAsyncTask(Activity activity,int id) {
//            this.typePrediction= TypePrediction.ID_CITY;
//            this.id=id;
//            this.activity=activity;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(OpenWeatherJSon jSon) {
//            super.onPostExecute(jSon);
//            getOpenWeatherJson(jSon);
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected OpenWeatherJSon doInBackground(String... strings) {
//            OpenWeatherJSon openWeatherJSon=null;
//            if(typePrediction== TypePrediction.ID_CITY)
//                openWeatherJSon= OpenWeatherMapAPI.prediction(id);
//            return openWeatherJSon;
//        }
//    }
}
