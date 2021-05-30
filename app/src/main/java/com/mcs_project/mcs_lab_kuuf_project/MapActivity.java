package com.mcs_project.mcs_lab_kuuf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    //2 new Double variables, Latitude and Longitude
//    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get latitude & longitude from intent, probably
//        latitude = getIntent().getDoubleExtra("latitude", 0);
//        longitude = getIntent().getDoubleExtra("longitude", 0);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //use latitude & longitude variables to make new LatLng var
//        LatLng productPos = new LatLng(latitude, longitude);

        //Exploding Kitten for example
        LatLng productPos = new LatLng(-6.912035, 106.265139);

        //Place a new marker on said coordinates
        googleMap.addMarker(new MarkerOptions().position(productPos).title("Product Location"));
        //move the maps camera to marker
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(productPos));
    }
}