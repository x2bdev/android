package com.example.hoxyu.team3_weatherapp_final.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.hoxyu.team3_weatherapp_final.adapters.MyInfoWindowAdapter;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherMapJson;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WeatherAsyncMapTask extends AsyncTask<Void,Void,OpenWeatherMapJson> {
    Dialog dialog;

    Activity activity;
    TypePrediction typePrediction;

    String q;
    double latitude;
    double longitude;
    int id;

    NumberFormat format = new DecimalFormat("#0.0");
    Bitmap myBitmap=null;

    Marker marker=null;
    GoogleMap map=null;

    int code=0;
    public WeatherAsyncMapTask(Marker marker,GoogleMap map,Activity activity,double latitude,double longitude)
    {
        this.activity=activity;
        this.typePrediction=TypePrediction.Map;
        this.latitude=latitude;
        this.longitude=longitude;
        this.marker=marker;
        this.map=map;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(OpenWeatherMapJson openWeatherMapJson) {
        super.onPostExecute(openWeatherMapJson);
        if(map!=null) {
            map.setInfoWindowAdapter(new MyInfoWindowAdapter(this.activity,marker,openWeatherMapJson,myBitmap, latitude,longitude));
            marker.showInfoWindow();
            //dialog.dismiss();
            return;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected OpenWeatherMapJson doInBackground(Void... voids) {
        OpenWeatherMapJson openWeatherJSon=null;
        if(typePrediction==TypePrediction.Map){
            openWeatherJSon= OpenWeatherMapAPI.prediction(latitude,longitude,1);
        }
        try {
            String idIcon = openWeatherJSon.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
//Tiến hành tạo đối tượng URL
            URL urlConnection = new URL(urlIcon);
//Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
//Đọc dữ liệu
            InputStream input = connection.getInputStream();
//Tiến hành convert qua hình ảnh
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openWeatherJSon;
    }
}
