package com.example.hoxyu.team3_weatherapp_final.model;

public class Weather {
    int id;
    String date;
    double temp;
    double pressure;
    double rain;

    public Weather(int id, String date, double temp, double pressure, double rain) {
        this.id = id;
        this.date = date;
        this.temp = temp;
        this.pressure = pressure;
        this.rain = rain;
    }

    public Weather() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", rain=" + rain +
                '}';
    }
}
