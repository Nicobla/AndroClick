package com.example.androclick;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MakeTacos_3 extends Fragment implements android.widget.CompoundButton.OnCheckedChangeListener {

    private Bundle bundle;
    private Recette recette;

    final int NB_MAX_VIANDES = 2;
    int nbViandes = 0;

    ArrayList<Viande> listeViandes;

    private RecyclerView rvViandes;
    private RecyclerView.Adapter viandesAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public MakeTacos_3() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        recette = (Recette) bundle.getSerializable("recette");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_3, container, false);

        rvViandes = (RecyclerView) view.findViewById(R.id.list_viandes);
        displayListeViandes();


        rvViandes.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Viande v = listeViandes.get(position);
                        v.setSelected(!v.isSelected());
                        if (v.isSelected())
                            recette.addViande(v);
                        else
                            recette.removeViande(v);
                        bundle.putSerializable("recette", recette);
                        setArguments(bundle);
                    }
                })
        );

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

    private void displayListeViandes() {
        listeViandes = ((MyApplication) this.getActivity().getApplicationContext()).getListeViandes();

        rvViandes.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvViandes.setLayoutManager(layoutManager);

        viandesAdapter = new ViandesAdapter(listeViandes);
        rvViandes.setAdapter(viandesAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = rvViandes.getChildAdapterPosition(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Viande v = listeViandes.get(pos);
            v.setSelected(isChecked);
            // Compteur de viandes pour limiter
            if (isChecked)
                nbViandes++;
            else
                nbViandes--;
            if (nbViandes >= NB_MAX_VIANDES) {
                Toast.makeText(getContext(), "MAX VIANDES (" + nbViandes + ")", Toast.LENGTH_SHORT).show();
                //TODO : griser checkboxes sauf celles déjà cochées
            }
        }
    }

}
