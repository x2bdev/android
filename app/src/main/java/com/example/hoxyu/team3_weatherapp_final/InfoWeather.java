package com.example.hoxyu.team3_weatherapp_final;

/**
 * Created by BaoBao on 4/26/2018.
 */

public class InfoWeather {

    private int id;
    private String city_name;
    private Double nhiet_do;
    private Double do_am;
    private Double gio;
    private String date;

    public InfoWeather() {
    }

    public InfoWeather(int id, String city_name, Double nhiet_do, Double do_am, Double gio, String date) {
        this.id = id;
        this.city_name = city_name;
        this.nhiet_do = nhiet_do;
        this.do_am = do_am;
        this.gio = gio;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Double getNhiet_do() {
        return nhiet_do;
    }

    public void setNhiet_do(Double nhiet_do) {
        this.nhiet_do = nhiet_do;
    }

    public Double getDo_am() {
        return do_am;
    }

    public void setDo_am(Double do_am) {
        this.do_am = do_am;
    }

    public Double getGio() {
        return gio;
    }

    public void setGio(Double gio) {
        this.gio = gio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "InfoWeather{" +
                "id=" + id +
                ", city_name='" + city_name + '\'' +
                ", nhiet_do=" + nhiet_do +
                ", do_am=" + do_am +
                ", gio=" + gio +
                ", date=" + date +
                '}';
    }
}
