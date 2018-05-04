package com.example.hoxyu.team3_weatherapp_final.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoxyu.team3_weatherapp_final.R;
import com.example.hoxyu.team3_weatherapp_final.Weather_FutureActivity;

public class RecyclerViewFragment extends Fragment {
    private boolean isFragmentLoaded=false;
    RecyclerView recyclerView;
    int day;

    public RecyclerViewFragment () {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Weather_FutureActivity mainActivity = (Weather_FutureActivity) getActivity();
        day=bundle.getInt("day");
        recyclerView.setAdapter(mainActivity.getAdapter(bundle.getInt("day")));
        return view;
    }
}
