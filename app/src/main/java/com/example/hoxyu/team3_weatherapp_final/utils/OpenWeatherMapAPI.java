package com.example.hoxyu.team3_weatherapp_final.utils;

import android.renderscript.Sampler;

import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherMapJson;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by MyPC on 06/07/2017.
 */

public class OpenWeatherMapAPI {

    // tìm thông tin theo kinh độ vĩ độ nhập vào
    public static OpenWeatherJSon prediction(double lat,double lon)
    {
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&appid=9962339932e4b20897e5661ee64852db");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");

            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            //String idIcon = results.getList().get(0).getWeather().get(0).getIcon().toString();
            //String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            //URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) { }
        catch (IOException e) { }
        return null;
    }
    public static OpenWeatherMapJson prediction(double lat,double lon, int code)
    {
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=9962339932e4b20897e5661ee64852db");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");

            OpenWeatherMapJson results = new Gson().fromJson(reader, OpenWeatherMapJson.class);

            //String idIcon = results.getList().get(0).getWeather().get(0).getIcon().toString();
            //String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            //URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) { }
        catch (IOException e) { }
        return null;
    }
    public static OpenWeatherJSon prediction(String q)
    {
        try {
            String location= URLEncoder.encode(q, "UTF-8");

            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+location+"&appid=9962339932e4b20897e5661ee64852db");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            //String idIcon = results.getWeather().get(0).getIcon().toString();
            // String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            //URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) {
// TODO Auto-generated catch block
//e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
//e.printStackTrace();
        }
        return null;
    }
    public static OpenWeatherJSon prediction( int id)
    {
        try {
            String id_city= String.valueOf(id);

            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?id="+id_city+"&appid=9962339932e4b20897e5661ee64852db");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            //String idIcon = results.getWeather().get(0).getIcon().toString();
            // String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            //URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) {
// TODO Auto-generated catch block
//e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
//e.printStackTrace();
        }
        return null;
    }
}