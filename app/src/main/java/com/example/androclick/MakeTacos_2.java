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

public class MakeTacos_2 extends Fragment implements android.widget.CompoundButton.OnCheckedChangeListener {

    final int NB_MAX_SAUCES = 2;
    int nbSauces = 0;

    ArrayList<Sauce> listeSauces;

    private RecyclerView rvSauces;
    private RecyclerView.Adapter saucesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MakeTacos_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_2, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));

        rvSauces = (RecyclerView)view.findViewById(R.id.list_sauces);
        displayListeSauces();

        return view;
    }


    private void displayListeSauces() {
        listeSauces = new ArrayList<Sauce>();
        //TODO : récupérer la liste des sauces depuis BDD
        listeSauces.add(new Sauce("Mayonnaise"));
        listeSauces.add(new Sauce("Moutarde"));
        listeSauces.add(new Sauce("Ketchup"));
        for (int i=1; i<=4; i++) {
            listeSauces.add(new Sauce("Exemple "+i));
        }

        rvSauces.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvSauces.setLayoutManager(layoutManager);

        saucesAdapter = new SaucesAdapter(listeSauces);
        rvSauces.setAdapter(saucesAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = rvSauces.getChildAdapterPosition(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Sauce s = listeSauces.get(pos);
            s.setSelected(isChecked);
            // Compteur de sauces pour limiter
            if (isChecked)
                nbSauces++;
            else
                nbSauces--;
            if (nbSauces >= NB_MAX_SAUCES) {
                Toast.makeText(getContext(), "MAX SAUCES ("+nbSauces+")", Toast.LENGTH_SHORT).show();
                //TODO : griser checkboxes sauf celles déjà cochées
            }
        }
    }
}
