package com.example.nhatro.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhatro.Controller.DanDuongToiPhongTroController;
import com.example.nhatro.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Binh on 5/17/17.
 */

public class DanDuongToiPhongTroActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap googleMap;
    SupportMapFragment supportMapFragment;
    double latitude =0;
    double longitude = 0;
    SharedPreferences sharedPreferences;
    Location vitrihientai;

    DanDuongToiPhongTroController danDuongToiPhongTroController;
    String duongdan = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danduong);

        danDuongToiPhongTroController = new DanDuongToiPhongTroController();


        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

        sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() + "&destination=" +latitude+"," + longitude + "&language=vi&key=AIzaSyBEg9WUb7xVdfOEyiaGhALLBXrl-IFp65U";
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.clear();

        LatLng latLng = new LatLng(vitrihientai.getLatitude(),vitrihientai.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);

        LatLng vitriphongtro = new LatLng(latitude,longitude);
        MarkerOptions markervitriphongtro = new MarkerOptions();
        markervitriphongtro.position(vitriphongtro);
        googleMap.addMarker(markervitriphongtro);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);

        danDuongToiPhongTroController.HienThiDanDuongToiPhongTro(googleMap,duongdan);
    }
}
