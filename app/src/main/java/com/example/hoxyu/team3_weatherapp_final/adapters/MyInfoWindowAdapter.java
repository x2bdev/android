package com.example.hoxyu.team3_weatherapp_final.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoxyu.team3_weatherapp_final.R;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherMapJson;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    Marker maker=null;
    OpenWeatherMapJson openWeatherJSon=null;
    Bitmap myBitmap=null;
    NumberFormat format = new DecimalFormat("#0.0");
    double latitude;
    double longitude;

    public MyInfoWindowAdapter(Activity context, Marker maker, OpenWeatherMapJson openWeatherJSon, Bitmap myBitmap) {
        this.context = context;
        this.maker = maker;
        this.openWeatherJSon = openWeatherJSon;
        this.myBitmap = myBitmap;
    }

    public MyInfoWindowAdapter(Activity context, Marker maker, OpenWeatherMapJson openWeatherJSon, Bitmap myBitmap, double latitude, double longitude) {
        this.context = context;
        this.maker = maker;
        this.openWeatherJSon = openWeatherJSon;
        this.myBitmap = myBitmap;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = this.context.getLayoutInflater().inflate(R.layout.detail_weather_map, null);
        TextView txtTemperature = (TextView) v.findViewById(R.id.txt_Temperature);
        TextView txtCurrentAddressName = (TextView) v.findViewById(R.id.txt_ViTri);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_weather);
        TextView txtMaxtemp = (TextView) v.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp = (TextView) v.findViewById(R.id.txtMinTemp);
        TextView txtWind = (TextView) v.findViewById(R.id.txtWind);
        TextView txtCloudliness = (TextView) v.findViewById(R.id.txtCloudliness);
        TextView txtPressure = (TextView) v.findViewById(R.id.txtPressure);
        TextView txtHumidty = (TextView) v.findViewById(R.id.txtHumidty);
        TextView txtSunrise = (TextView) v.findViewById(R.id.txtSunrise);
        TextView txtSunset = (TextView) v.findViewById(R.id.txtSunset);

        double temperature = openWeatherJSon.getMain().getTemp() - 273.15;
        String maxtemp = format.format(openWeatherJSon.getMain().getTemp_max() - 273.15) + "°C";
        String mintemp = format.format(openWeatherJSon.getMain().getTemp_min() - 273.15) + "°C";
        String wind = openWeatherJSon.getWind().getSpeed() + " m/s";
        String mesg = openWeatherJSon.getWeather().get(0).getMain();

        String cloudiness = mesg;
        String pressure = openWeatherJSon.getMain().getPressure() + " hpa";
        String humidity = openWeatherJSon.getMain().getHumidity() + " %";

        Date timeSunrise = new Date(openWeatherJSon.getSys().getSunrise() * 1000);
        String Sunrise = timeSunrise.getHours() + ":" + timeSunrise.getMinutes() + " AM";
        Date timeSunSet = new Date(openWeatherJSon.getSys().getSunset() * 1000);
        String sunset = timeSunSet.getHours() + ":" + timeSunSet.getMinutes();

        txtCurrentAddressName.setText(openWeatherJSon.getName());
        txtTemperature.setText(format.format(temperature) + "°C");
        imageView.setImageBitmap(myBitmap);
        txtMaxtemp.setText(maxtemp);
        txtMinTemp.setText(mintemp);
        txtWind.setText(wind);
        txtCloudliness.setText(cloudiness);
        txtPressure.setText(pressure);
        txtHumidty.setText(humidity);
        txtSunrise.setText(Sunrise);
        txtSunset.setText(sunset);

        v.setBackgroundColor(Color.WHITE);


        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
