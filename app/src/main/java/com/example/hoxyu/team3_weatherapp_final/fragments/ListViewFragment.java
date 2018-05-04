package com.example.hoxyu.team3_weatherapp_final.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.R;
import com.example.hoxyu.team3_weatherapp_final.Weather_FutureActivity;
import com.example.hoxyu.team3_weatherapp_final.Weather_Future_Test_Activity;
import com.example.hoxyu.team3_weatherapp_final.adapters.Adapter_ListView;
import com.example.hoxyu.team3_weatherapp_final.model.list;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {
    ListView listView;
    ArrayList<list> list;

    public ListViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        Weather_Future_Test_Activity mainActivity = (Weather_Future_Test_Activity) getActivity();
        list= (ArrayList<com.example.hoxyu.team3_weatherapp_final.model.list>) bundle.getSerializable("ListWeather");
        Adapter_ListView adapterListView=new Adapter_ListView(mainActivity,R.layout.list_row,list);
        listView.setAdapter(adapterListView);
        return view;
    }
}
