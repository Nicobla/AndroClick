package com.example.androclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class ListOTacos extends Fragment {

    private ArrayList<O_Tacos> listeOTacos = new ArrayList<>();
    private RecyclerView rvOTacos;
    private RecyclerView.Adapter otacosAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListOTacos() {
        // Required empty public constructor
    }

    public void restart() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otacos_list_fragment, container, false);

        this.listeOTacos = ((MyApplication) this.getActivity().getApplicationContext()).getListeOTacos();

        rvOTacos = (RecyclerView) view.findViewById(R.id.list_otacos);
        displayListeOTaocs(listeOTacos);

        SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        restart();
                    }
                }
        );

        Button button_find = (Button) view.findViewById(R.id.button_find);
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Localisation + calcul plus proche
                LatLng home = new LatLng(45.9208490, 6.1415); // TODO : get position
                double distance_min = -1;
                O_Tacos nearest_otacos = new O_Tacos();
                nearest_otacos.setId(-1);
                for (O_Tacos o_tacos : listeOTacos) {
                    double distance_otacos = distance(o_tacos.getLocation(), home);
                    if (distance_min == -1 || distance_min > distance_otacos) {
                        distance_min = distance_otacos;
                        nearest_otacos = o_tacos;
                    }
                }
                if (nearest_otacos.getId() != -1) {
                    //Toast.makeText(getContext(), "OTacos le plus proche : "+nearest_otacos.getNom(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), OTacos_Details.class);
                    O_Tacos_Serializable otacos = new O_Tacos_Serializable(nearest_otacos);
                    intent.putExtra("otacos", otacos);
                    ((Activity) getContext()).startActivity(intent);
                }


            }

            private double distance(GeoPoint location, LatLng home) {
                double distanceN = Math.abs(location.getLatitude() - home.latitude);
                double distanceE = Math.abs(location.getLongitude() - home.longitude);

                return (double) Math.abs(distanceN - distanceE);
            }
        });

        return view;
    }

    private void displayListeOTaocs(ArrayList<O_Tacos> listeOTacos) {
        layoutManager = new LinearLayoutManager(getContext());
        rvOTacos.setLayoutManager(layoutManager);

        otacosAdapter = new OTacosAdapter(listeOTacos);
        rvOTacos.setAdapter(otacosAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
