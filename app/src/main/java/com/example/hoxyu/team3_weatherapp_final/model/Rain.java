package com.example.hoxyu.team3_weatherapp_final.model;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("3h")
    private double h3;
    //private String name;
    public Rain(int h3) {
        this.h3 = h3;
        //this.name = name;
    }

    public double getH3() {
        return h3;
    }

    public void setH3(double h3) {
        this.h3 = h3;
    }
}
