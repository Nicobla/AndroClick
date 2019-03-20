package com.example.androclick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class MakeTacos_2 extends Fragment {

    private Bundle bundle;
    private Recette recette;

    final int NB_MAX_SAUCES = 2;
    ArrayList<String> listePositions;

    ArrayList<Sauce> listeSauces;

    private RecyclerView rvSauces;
    private RecyclerView.Adapter saucesAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public MakeTacos_2() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MyApplication)getActivity().getApplicationContext()).listePositionsSauces = listePositions;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.listePositions = ((MyApplication)getActivity().getApplicationContext()).listePositionsSauces;
        displayListeSauces();
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
        listePositions = ((MyApplication)getActivity().getApplicationContext()).listePositionsSauces;

        rvSauces = (RecyclerView) view.findViewById(R.id.list_sauces);
        displayListeSauces();

        rvSauces.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_sauce);
                        if (chk.isEnabled()) {
                            Sauce s = listeSauces.get(position);
                            s.setSelected(!s.isSelected());
                            chk.setChecked(s.isSelected());

                            if (s.isSelected()) {
                                recette.addSauce(s);
                                listePositions.add(String.valueOf(position));
                            } else {
                                recette.removeSauce(s);
                                listePositions.remove(String.valueOf(position));
                                enableAllCheckboxes();
                            }

                            if (listePositions.size() >= NB_MAX_SAUCES) {
                                disableCheckboxes();
                            }
                            bundle.putSerializable("recette", recette);
                            setArguments(bundle);
                        }
                    }
                })
        );

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }


    private void displayListeSauces() {
        listeSauces = ((MyApplication) this.getActivity().getApplicationContext()).getListeSauces();

        layoutManager = new LinearLayoutManager(getContext());
        rvSauces.setLayoutManager(layoutManager);

        saucesAdapter = new SaucesAdapter(listeSauces, listePositions, NB_MAX_SAUCES);
        rvSauces.setAdapter(saucesAdapter);
    }

    private void disableCheckboxes() {
        for (int i = 0; i < rvSauces.getChildCount(); i++) {
            boolean isChecked = false;
            for (String strPos : listePositions) {
                if (i == Integer.parseInt(strPos))
                    isChecked = true;
            }
            if (!isChecked) {
                RecyclerView.ViewHolder vh = rvSauces.findViewHolderForAdapterPosition(i);
                CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_sauce);
                chkbox.setEnabled(false);
                chkbox.setActivated(false);
            }
        }
    }

    private void enableAllCheckboxes() {
        for (int i = 0; i < rvSauces.getChildCount(); i++) {
            RecyclerView.ViewHolder vh = rvSauces.findViewHolderForAdapterPosition(i);
            CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_sauce);
            chkbox.setEnabled(true);
            chkbox.setActivated(true);
        }
    }
}
