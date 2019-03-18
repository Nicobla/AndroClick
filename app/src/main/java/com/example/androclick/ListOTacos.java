package com.example.androclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ListOTacos extends Fragment {

    private LatLng userPosition;
    private ArrayList<O_Tacos> listeOTacos = new ArrayList<>();

    private RecyclerView rvOTacos;
    private RecyclerView.Adapter otacosAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListOTacos() {
        // Required empty public constructor
    }

    public void reload() {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
        otacosAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otacos_list_fragment, container, false);

        this.listeOTacos = ((MyApplication) this.getActivity().getApplicationContext()).getListeOTacos();

        rvOTacos = (RecyclerView) view.findViewById(R.id.list_otacos);
        userPosition = ((MyApplication) this.getActivity().getApplicationContext()).getUserPosition();
        displayListeOTaocs(listeOTacos, userPosition);

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        reload();
                        new Handler().postDelayed(new Runnable(){
                            public void run(){
                                mySwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 500);
                    }
                }
        );

        Button button_find = (Button) view.findViewById(R.id.button_find);
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : juste re-localiser
                // Calcul plus proche
                if(userPosition != null) {
                    findNearestOTacos();
                } else {
                    //TODO : relocalise : userPosition = findLocation() ou un truc du genre
                    userPosition = null;

                    if (userPosition == null)
                        Toast.makeText(getContext(), "Localisation impossible", Toast.LENGTH_SHORT).show();
                    else {
                        findNearestOTacos();
                    }
                }
            }
        });

        return view;
    }

    private void findNearestOTacos() {
        O_Tacos nearest_otacos = new O_Tacos();
        nearest_otacos.setId(-1);
        double distance_min = -1;
        for (O_Tacos o_tacos : listeOTacos) {
            double distance_otacos = o_tacos.distance(userPosition);
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
            if (userPosition != null)
                intent.putExtra("userPosition", new GeoPoint2(userPosition.latitude, userPosition.longitude));
            ((Activity) getContext()).startActivity(intent);
        }
    }

    private void displayListeOTaocs(ArrayList<O_Tacos> listeOTacos, LatLng userPosition) {
        layoutManager = new LinearLayoutManager(getContext());
        rvOTacos.setLayoutManager(layoutManager);

        if (listeOTacos != null)
            otacosAdapter = new OTacosAdapter(listeOTacos, userPosition);
        else
            otacosAdapter = new OTacosAdapter(new ArrayList<O_Tacos>(), userPosition);
        rvOTacos.setAdapter(otacosAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
