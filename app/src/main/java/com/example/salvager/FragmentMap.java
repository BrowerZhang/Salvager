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

                LatLng center1 = new LatLng(32.00498573886551, -81.09589305338758);
                LatLng center2 = new LatLng(32.01878014682245, -80.97238626398435);
                LatLng center3 = new LatLng(31.976412620414038, -81.24906714852901);
                LatLng center4 = new LatLng(32.08649534215099, -81.16876499673256);

                //Added latitute and longitude to centeralize map when opening map tab
                LatLng num5 = new LatLng(32.03265415443687, -81.12086433931199);

                markerOptions1.position(center1);
                markerOptions2.position(center2);
                markerOptions3.position(center3);
                markerOptions4.position(center4);


                googleMap.addMarker(markerOptions1);
                googleMap.addMarker(markerOptions2);
                googleMap.addMarker(markerOptions3);
                googleMap.addMarker(markerOptions4);

                markerOptions1.title("Chatham County Resource Conservation and Recycling Education Center");
                markerOptions2.title("Wilmington Island Drop-Off Center\n");
                markerOptions3.title("Chevis Road Drop-Off Center\n");
                markerOptions4.title("Sharon Park Drop-Off Center");

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(num5, 10));
            }
        });


        //return view
        return view;
    }
}
