package com.example.salvager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMap extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //initialize view
        View view = inflater.inflate(R.layout.fragmentmap_layout,container,false);

        //initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                MarkerOptions markerOptions1 = new MarkerOptions();
                MarkerOptions markerOptions2 = new MarkerOptions();
                MarkerOptions markerOptions3 = new MarkerOptions();
                MarkerOptions markerOptions4 = new MarkerOptions();

                markerOptions1.position( , );
                markerOptions2.position( , );
                markerOptions3.position( , );
                markerOptions4.position( , );

                googleMap.addMarker(markerOptions1);
                googleMap.addMarker(markerOptions2);
                googleMap.addMarker(markerOptions3);
                googleMap.addMarker(markerOptions4);


            }
        });


        //return view
        return view;
    }
}
