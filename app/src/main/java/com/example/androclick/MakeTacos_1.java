package com.example.androclick;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MakeTacos_1 extends Fragment {

    private Bundle bundle;

    private Recette recette = new Recette("");

    public MakeTacos_1() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(), context.getClass().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_1, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));

        bundle = getArguments();
        if (bundle != null) {
            recette = (Recette) bundle.getSerializable("recette");
            int numRecette = ((MyApplication) getActivity().getApplicationContext()).getListeRecettes().size() +1;
            if (recette == null) recette = new Recette("Recette "+numRecette);
        } else {
            bundle = new Bundle();
        }

        Toast.makeText(getContext(), recette.getNom(), Toast.LENGTH_SHORT).show();

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

}
