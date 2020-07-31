package com.example.nhatro.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhatro.Controller.OdauController;
import com.example.nhatro.R;


/**
 * Created by Binh on 4/13/17.
 */

public class OdauFragment extends Fragment {
    OdauController odauController;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau,container,false);
        recyclerOdau = (RecyclerView) view.findViewById(R.id.recyclerOdau);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarODau);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestScrollViewODau);

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        odauController = new OdauController(getContext());


        odauController.getDanhSachPhongTroController(getContext(),nestedScrollView,recyclerOdau,progressBar,vitrihientai);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
