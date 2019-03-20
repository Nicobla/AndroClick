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

public class MakeTacos_3 extends Fragment {

    private Bundle bundle;
    private Recette recette;

    final int[] NB_MAX_VIANDES = {1,2,3,4};

    ArrayList<Viande> listeViandes;
    ArrayList<String> listePositions;
    int nb_max_viandes = 1;
    boolean mustUncheck = false;

    private RecyclerView rvViandes;
    private RecyclerView.Adapter viandesAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public MakeTacos_3() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        this.listePositions = ((MyApplication)getActivity().getApplicationContext()).listePositionsViandes;
        if (listePositions.size() == 0)
            mustUncheck = true;
        displayListeViandes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MyApplication)getActivity().getApplicationContext()).listePositionsViandes = listePositions;
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
        listePositions = ((MyApplication)getActivity().getApplicationContext()).listePositionsViandes;

        rvViandes = (RecyclerView) view.findViewById(R.id.list_viandes);
        displayListeViandes();

        rvViandes.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_viande);
                        if (chk.isEnabled()) {
                            Viande v = listeViandes.get(position);
                            v.setSelected(!v.isSelected());
                            chk.setChecked(v.isSelected());

                            if (v.isSelected()) {
                                recette.addViande(v);
                                listePositions.add(String.valueOf(position));
                            } else {
                                recette.removeViande(v);
                                listePositions.remove(String.valueOf(position));
                                enableAllCheckboxes();
                            }
                            switch (recette.getTailleTacos()) {
                                case M: nb_max_viandes = NB_MAX_VIANDES[0]; break;
                                case L: nb_max_viandes = NB_MAX_VIANDES[1]; break;
                                case XL: nb_max_viandes = NB_MAX_VIANDES[2]; break;
                                case XXL: nb_max_viandes = NB_MAX_VIANDES[3]; break;
                            }
                            if (listePositions.size() >= nb_max_viandes) {
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

    private void displayListeViandes() {
        if (mustUncheck) {
            ((MyApplication) this.getActivity().getApplicationContext()).uncheckViandes();
            mustUncheck = false;
        }
        listeViandes = ((MyApplication) this.getActivity().getApplicationContext()).getListeViandes();

        layoutManager = new LinearLayoutManager(getContext());
        rvViandes.setLayoutManager(layoutManager);

        viandesAdapter = new ViandesAdapter(listeViandes, listePositions, nb_max_viandes);
        rvViandes.setAdapter(viandesAdapter);
    }

    private void disableCheckboxes() {
        for (int i = 0; i < rvViandes.getChildCount(); i++) {
            boolean isChecked = false;
            for (String strPos : listePositions) {
                if (i == Integer.parseInt(strPos))
                    isChecked = true;
            }
            if (!isChecked) {
                RecyclerView.ViewHolder vh = rvViandes.findViewHolderForAdapterPosition(i);
                CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_viande);
                chkbox.setEnabled(false);
                chkbox.setActivated(false);
            }
        }
    }

    private void enableAllCheckboxes() {
        for (int i = 0; i < rvViandes.getChildCount(); i++) {
            RecyclerView.ViewHolder vh = rvViandes.findViewHolderForAdapterPosition(i);
            CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_viande);
            chkbox.setEnabled(true);
            chkbox.setActivated(true);
        }
    }
}
