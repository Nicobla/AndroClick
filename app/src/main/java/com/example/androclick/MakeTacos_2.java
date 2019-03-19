package com.example.androclick;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MakeTacos_2 extends Fragment {//implements android.widget.CompoundButton.OnCheckedChangeListener {

    private Bundle bundle;
    private Recette recette;

//    final int NB_MAX_SAUCES = 2;
//    int nbSauces = 0;

    ArrayList<Sauce> listeSauces;

    private RecyclerView rvSauces;
    private RecyclerView.Adapter saucesAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public MakeTacos_2() {
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
        View view = inflater.inflate(R.layout.make_tacos_fragment_2, container, false);

        rvSauces = (RecyclerView) view.findViewById(R.id.list_sauces);
        displayListeSauces();

        rvSauces.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Sauce s = listeSauces.get(position);
                        s.setSelected(!s.isSelected());
                        if (s.isSelected())
                            recette.addSauce(s);
                        else
                            recette.removeSauce(s);
                        bundle.putSerializable("recette", recette);
                        setArguments(bundle);
                    }
                })
        );

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }


    private void displayListeSauces() {
        listeSauces = ((MyApplication) this.getActivity().getApplicationContext()).getListeSauces();

//        for (Sauce sauce : listeSauces) {
//            Log.d("Sauce", "isSelected=" + sauce.isSelected());
//        }

        //rvSauces.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvSauces.setLayoutManager(layoutManager);

        saucesAdapter = new SaucesAdapter(listeSauces);
        saucesAdapter.notifyDataSetChanged();
        rvSauces.setAdapter(saucesAdapter);
    }

    /*@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = rvSauces.getChildAdapterPosition(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Sauce s = listeSauces.get(pos);
            s.setSelected(isChecked);
            if (s.isSelected())
                recette.addSauce(s);
            else
                recette.removeSauce(s);
            // Compteur de sauces pour limiter
            if (isChecked)
                nbSauces++;
            else
                nbSauces--;
            if (nbSauces >= NB_MAX_SAUCES) {
                Toast.makeText(getContext(), "MAX SAUCES ("+nbSauces+")", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
