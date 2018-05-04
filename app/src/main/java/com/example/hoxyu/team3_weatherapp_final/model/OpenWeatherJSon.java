package com.example.hoxyu.team3_weatherapp_final.model;

import java.util.List;

/**
 * Created by MyPC on 06/07/2017.
 */

public class OpenWeatherJSon {
    private String cod;
    private double message;
    private int cnt;
    private List<list> list;
    private City city;

    public OpenWeatherJSon() {
    }

    public OpenWeatherJSon(String cod, double message, int cnt, List<com.example.hoxyu.team3_weatherapp_final.model.list> list, City city) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<com.example.hoxyu.team3_weatherapp_final.model.list> getList() {
        return list;
    }

    public void setList(List<com.example.hoxyu.team3_weatherapp_final.model.list> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
