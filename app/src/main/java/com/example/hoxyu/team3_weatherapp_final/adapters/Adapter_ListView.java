package com.example.hoxyu.team3_weatherapp_final.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoxyu.team3_weatherapp_final.R;
import com.example.hoxyu.team3_weatherapp_final.model.list;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_ListView extends ArrayAdapter<list> {
    Context context;
    int resource;
    ArrayList<list> objects;

    public Adapter_ListView(@NonNull Context context, int resource, @NonNull ArrayList<list> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(resource,null);

        TextView txt_Date,txt_Description,txt_Wind,txt_Pressure,txt_Humidity,txt_Temperature;
        ImageView item_img;

        txt_Date=v.findViewById(R.id.itemDate);
        txt_Description=v.findViewById(R.id.itemDescription);
        txt_Wind=v.findViewById(R.id.itemWind);
        txt_Pressure=v.findViewById(R.id.itemPressure);
        txt_Humidity=v.findViewById(R.id.itemHumidity);
        txt_Temperature=v.findViewById(R.id.itemTemperature);
        item_img=v.findViewById(R.id.item_img);



        txt_Date.setText(objects.get(position).getDt_txt());
        txt_Description.setText(objects.get(position).getWeather().get(0).getDescription());

        String pressure= objects.get(position).getMain().getPressure()+" hpa";
        String humidity=objects.get(position).getMain().getHumidity()+" %";
        String wind= objects.get(position).getWind().getSpeed()+" m/s";
        double temperature=objects.get(position).getMain().getTemp()-273.15;
        NumberFormat format = new DecimalFormat("#0.0");

        txt_Wind.setText(wind);
        txt_Pressure.setText(pressure);
        txt_Humidity.setText(humidity);
        txt_Temperature.setText(format.format(temperature)+"°C");

        return v;
    }
}
