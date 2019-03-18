package com.example.androclick;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Map extends Fragment implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION_PERMISSION = 0;
    private GoogleMap mMap;

    private Location myLocation;
    private LatLng userLocation;

    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otacos_map_fragment, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ajout des marqueurs des O'Tacos
        ArrayList<O_Tacos> listeOTacos = ((MyApplication) getActivity().getApplicationContext()).getListeOTacos();
        for (O_Tacos o_tacos : listeOTacos) {
            LatLng latLng = new LatLng(o_tacos.getLocation().getLatitude(), o_tacos.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(o_tacos.getNom()));
        }



        enableMyLocation(mMap);
        // Positionne la caméra sur la position de l'utilisateur (si elle existe)
        if (userLocation != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
    }

    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            if (mMap.isMyLocationEnabled()) {
                //TODO : enregistrer localisation
//                myLocation = mMap.getMyLocation();
//                if (myLocation != null) {
//                    userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                    Log.e("DEBUG", userLocation.latitude + ", " + userLocation.longitude);
//                    ((MyApplication) this.getActivity().getApplicationContext()).setUserPosition(userLocation);
//                } else {
//                    Log.e("DEBUG", "enabled mais null");
//                }
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
