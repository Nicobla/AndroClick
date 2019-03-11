package com.example.androclick;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MakeTacos_4 extends Fragment implements android.widget.CompoundButton.OnCheckedChangeListener {

    ArrayList<Supplement> listeSupplements;

    private RecyclerView rvSupplements;
    private RecyclerView.Adapter supplementsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MakeTacos_4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_4, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));

        rvSupplements = (RecyclerView)view.findViewById(R.id.list_supplements);
        displayListeSupplements();

        return view;
    }

    private void displayListeSupplements() {
        listeSupplements = new ArrayList<Supplement>();
        //TODO : récupérer la liste des suppléments depuis BDD
        listeSupplements.add(new Supplement("Salade"));
        listeSupplements.add(new Supplement("Tomate"));
        listeSupplements.add(new Supplement("Oignon"));
        for (int i=1; i<=15; i++) {
            listeSupplements.add(new Supplement("Exemple "+i));
        }

        rvSupplements.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvSupplements.setLayoutManager(layoutManager);

        supplementsAdapter = new SupplementsAdapter(listeSupplements);
        rvSupplements.setAdapter(supplementsAdapter);

        rvSupplements.setAdapter(supplementsAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos2 = rvSupplements.getChildAdapterPosition(buttonView);//.getPositionForView(buttonView);
        if (pos2 != ListView.INVALID_POSITION) {
            Supplement s = listeSupplements.get(pos2);
            s.setSelected(isChecked);
        }
    }
}
