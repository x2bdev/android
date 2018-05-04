package com.example.hoxyu.team3_weatherapp_final.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.R;
import com.example.hoxyu.team3_weatherapp_final.model.list;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class Adapter_RecyclerView extends RecyclerView.Adapter<Adapter_RecyclerView.MyViewHolder> {
    Context mContext;
    List<list> list;

    public Adapter_RecyclerView(Context mContext, List<com.example.hoxyu.team3_weatherapp_final.model.list> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.txt_Date.setText(list.get(position).getDt_txt());
        holder.txt_Description.setText(list.get(position).getWeather().get(0).getDescription());

        String pressure= list.get(position).getMain().getPressure()+" hpa";
        String humidity=list.get(position).getMain().getHumidity()+" %";
        String wind= list.get(position).getWind().getSpeed()+" m/s";
        double temperature=list.get(position).getMain().getTemp()-273.15;
        NumberFormat format = new DecimalFormat("#0.0");

        holder.txt_Wind.setText(wind);
        holder.txt_Pressure.setText(pressure);
        holder.txt_Humidity.setText(humidity);
        holder.txt_Temperature.setText(format.format(temperature)+"°C");
        //Toast.makeText(mContext, list.size()+" :Size", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_Date,txt_Description,txt_Wind,txt_Pressure,txt_Humidity,txt_Temperature;
        ImageView item_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_Date=itemView.findViewById(R.id.itemDate);
            txt_Description=itemView.findViewById(R.id.itemDescription);
            txt_Wind=itemView.findViewById(R.id.itemWind);
            txt_Pressure=itemView.findViewById(R.id.itemPressure);
            txt_Humidity=itemView.findViewById(R.id.itemHumidity);
            txt_Temperature=itemView.findViewById(R.id.itemTemperature);
            item_img=itemView.findViewById(R.id.item_img);
        }
    }


}
