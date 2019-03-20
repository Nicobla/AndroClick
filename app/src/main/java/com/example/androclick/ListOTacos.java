package com.example.androclick;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ListOTacos extends Fragment {

    private LatLng userPosition;
    private ArrayList<O_Tacos> listeOTacos = new ArrayList<>();

    private RecyclerView rvFavOTacos;
    private RecyclerView rvOtherOTacos;
    private RecyclerView.Adapter favOTacosAdapter;
    private RecyclerView.Adapter otherOTacosAdapter;

    public ListOTacos() {
        // Required empty public constructor
    }

    public void reload() {
        //this.listeOTacos = ((MyApplication) this.getActivity().getApplicationContext()).getListeOTacos();
        locate();
        displayListeOTaocs(listeOTacos, userPosition);
        favOTacosAdapter.notifyDataSetChanged();
        otherOTacosAdapter.notifyDataSetChanged();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
    }

    public void locate() {
        userPosition = ((MyApplication) this.getActivity().getApplicationContext()).getUserPosition();
        if (userPosition == null) {
            Log.e("ListOTacos - locate", "localisation impossible");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otacos_list_fragment, container, false);

        this.listeOTacos = ((MyApplication) this.getActivity().getApplicationContext()).getListeOTacos();

        rvFavOTacos = (RecyclerView) view.findViewById(R.id.list_fav_otacos);
        rvOtherOTacos = (RecyclerView) view.findViewById(R.id.list_other_otacos);
        userPosition = ((MyApplication) this.getActivity().getApplicationContext()).getUserPosition();
        displayListeOTaocs(listeOTacos, userPosition);

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        reload();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mySwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 500);
                    }
                }
        );

        // Recharge la page tous les X temps
        final Handler handler = new Handler();
        final int delay = 500; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                handler.postDelayed(this, delay);
                if (getActivity() != null) {
                    if (((MyApplication)getActivity().getApplicationContext()).mustRefreshOTacos) {
                        if (isResumed() && !isStateSaved())
                            reload();
                        ((MyApplication)getActivity().getApplicationContext()).mustRefreshOTacos = false;
                    }
                }
            }
        }, delay);

        return view;
    }

//    private void findNearestOTacos() {
//        O_Tacos nearest_otacos = new O_Tacos();
//        nearest_otacos.setId(-1);
//        double distance_min = -1;
//        for (O_Tacos o_tacos : listeOTacos) {
//            double distance_otacos = o_tacos.distance(userPosition);
//            if (distance_min == -1 || distance_min > distance_otacos) {
//                distance_min = distance_otacos;
//                nearest_otacos = o_tacos;
//            }
//        }
//        if (nearest_otacos.getId() != -1) {
//            //Toast.makeText(getContext(), "OTacos le plus proche : "+nearest_otacos.getNom(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getContext(), OTacos_Details.class);
//            O_Tacos_Serializable otacos = new O_Tacos_Serializable(nearest_otacos);
//            intent.putExtra("otacos", otacos);
//            if (userPosition != null)
//                intent.putExtra("userPosition", new GeoPoint2(userPosition.latitude, userPosition.longitude));
//            ((Activity) getContext()).startActivity(intent);
//        }
//    }

    private void displayListeOTaocs(ArrayList<O_Tacos> listeOTacos, LatLng userPosition) {
        rvFavOTacos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOtherOTacos.setLayoutManager(new LinearLayoutManager(getContext()));

        if (listeOTacos != null) {
            ArrayList<O_Tacos> favOTacos = new ArrayList<>();
            ArrayList<O_Tacos> otherOTacos = new ArrayList<>();
            for (O_Tacos otacos : listeOTacos) {
                if (otacos.isFavorite()) {
                    favOTacos.add(otacos);
                } else {
                    otherOTacos.add(otacos);
                }
            }
            favOTacosAdapter = new OTacosAdapter(favOTacos, userPosition);
            otherOTacosAdapter = new OTacosAdapter(otherOTacos, userPosition);
        } else {
            favOTacosAdapter = new OTacosAdapter(new ArrayList<O_Tacos>(), userPosition);
            otherOTacosAdapter = new OTacosAdapter(new ArrayList<O_Tacos>(), userPosition);
        }
        rvFavOTacos.setAdapter(favOTacosAdapter);
        rvOtherOTacos.setAdapter(otherOTacosAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}