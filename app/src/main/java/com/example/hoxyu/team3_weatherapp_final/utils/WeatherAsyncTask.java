package com.example.hoxyu.team3_weatherapp_final.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.R;
import com.example.hoxyu.team3_weatherapp_final.SQLiteDataBase.DBHelper;
import com.example.hoxyu.team3_weatherapp_final.adapters.MyInfoWindowAdapter;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.Weather;
import com.example.hoxyu.team3_weatherapp_final.model.list;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MyPC on 06/07/2017.
 */

public class WeatherAsyncTask extends AsyncTask<Void,Void,OpenWeatherJSon> {
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


    DBHelper db;
    String DATABASE_NAME ="Weather.sqlite";

    public WeatherAsyncTask(Activity activity, double latitude, double longitude)
    {
        this.activity=activity;
        this.typePrediction=TypePrediction.LATITUDE_LONGITUDE;
        this.latitude=latitude;
        this.longitude=longitude;
        this.code=0;
        this.dialog=new ProgressDialog(activity);
        this.dialog.setTitle("Đang tải thông tin ...");
        //this.dialog.setMessage("Vui lòng chờ...");
        this.dialog.setCancelable(true);
        this.dialog.show();
    }
    public WeatherAsyncTask(Activity activity,int id)
    {
        this.activity=activity;
        this.typePrediction=TypePrediction.ID_CITY;
        this.id=id;
        this.code=1;
        this.dialog=new ProgressDialog(activity);
        this.dialog.setTitle("Đang tải thông tin ...");
        //this.dialog.setMessage("Vui lòng chờ...");
        this.dialog.setCancelable(true);
        this.dialog.show();
    }
//    public WeatherAsyncTask(Marker marker,GoogleMap map,Activity activity,double latitude,double longitude)
//    {
//        this(activity,latitude,longitude);
//        this.marker=marker;
//        this.map=map;
//    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(OpenWeatherJSon openWeatherJSon) {
        super.onPostExecute(openWeatherJSon);
            if(code==0){
                db=new DBHelper(activity,DATABASE_NAME,null,1);
                db.QueryData("DROP TABLE IF EXISTS Weather");
                db.QueryData("CREATE TABLE IF NOT EXISTS Weather(ID INTEGER,DATE VARCHAR(200),TEMP REAL,PRESSURE REAL,RAIN REAL)");
                ArrayList<list> arrayList=new ArrayList<>();
                ArrayList<Weather> weathers=new ArrayList<>();
                int id=openWeatherJSon.getCity().getId();
                arrayList= (ArrayList<list>) openWeatherJSon.getList();
                for(list item:arrayList){
                    String date=String.valueOf(item.getDt());
                    double temp=item.getMain().getTemp();
                    double pressure=item.getMain().getPressure();
                    double rain;
                    //if(item.getRain().getValue()=="")
                    try{
                        rain=item.getRain().getH3();
                    }catch (Exception ex){
                        rain=0.0;
                    }

                    Weather weather=new Weather(id,date,temp,pressure,rain);
                    weathers.add(weather);
                }
                db.insertArrWeather(weathers);
            }

            TextView txt_city = (TextView) activity.findViewById(R.id.txt_city);
            TextView txt_country = (TextView) activity.findViewById(R.id.txt_country);
            TextView txt_time = (TextView) activity.findViewById(R.id.txt_time);
            ImageView image_weather = (ImageView) activity.findViewById(R.id.img_weather);
            TextView txt_deg = (TextView) activity.findViewById(R.id.txt_deg);
            TextView txt_forecast = (TextView) activity.findViewById(R.id.forecast);
            TextView txt_humidity = (TextView) activity.findViewById(R.id.txt_humidity);
            TextView txt_wind = (TextView) activity.findViewById(R.id.txt_wind);
            TextView txt_pressure = (TextView) activity.findViewById(R.id.txt_pressure);

            String name_city = openWeatherJSon.getCity().getName();
            String name_country = openWeatherJSon.getCity().getCountry();
            String time_weather = openWeatherJSon.getList().get(0).getDt_txt();
            double temperature = openWeatherJSon.getList().get(0).getMain().getTemp() - 273.15;
            String forecast = openWeatherJSon.getList().get(0).getWeather().get(0).getDescription();
//        Translator translate = Translator.getInstance();
//        String cloudiness=mesg+" ("+translate.translate(mesg, Language.ENGLISH, Language.VIETNAMESE)+")";
            String pressure = openWeatherJSon.getList().get(0).getMain().getPressure() + " hpa";
            String humidity = openWeatherJSon.getList().get(0).getMain().getHumidity() + " %";
            String wind = openWeatherJSon.getList().get(0).getWind().getSpeed() + " m/s";

//set thông tin thời tiết
            txt_city.setText(name_city);
            txt_country.setText(name_country);
            txt_time.setText(time_weather);
            image_weather.setImageBitmap(myBitmap);
            txt_deg.setText(format.format(temperature) + "°C");
            txt_forecast.setText(forecast);
            txt_humidity.setText(humidity);
            txt_pressure.setText(pressure);
            txt_wind.setText(wind);

        Toast.makeText(activity, openWeatherJSon.getList().get(0).getRain().getH3()+": mưa", Toast.LENGTH_SHORT).show();
            this.dialog.dismiss();

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected OpenWeatherJSon doInBackground(Void... params) {
        OpenWeatherJSon openWeatherJSon=null;
        if(typePrediction== TypePrediction.LATITUDE_LONGITUDE){
            openWeatherJSon= OpenWeatherMapAPI.prediction(latitude,longitude);
        }
        if(typePrediction==TypePrediction.ID_CITY){
            openWeatherJSon= OpenWeatherMapAPI.prediction(id);
        }
//        if(typePrediction==TypePrediction.Map){
//            openWeatherJSon= OpenWeatherMapAPI.prediction(latitude,longitude,1);
//        }

        try {
            String idIcon = openWeatherJSon.getList().get(0).getWeather().get(0).getIcon().toString();
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
