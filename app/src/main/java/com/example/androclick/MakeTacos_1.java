package com.example.androclick;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MakeTacos_1 extends Fragment {

    private Bundle bundle;
    private Recette recette;

    public MakeTacos_1() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        recette = (Recette)bundle.getSerializable("recette");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_1, container, false);

        if (bundle != null) {
            recette = (Recette) bundle.getSerializable("recette");
            int numRecette = ((MyApplication) getActivity().getApplicationContext()).getListeRecettes().size() +1;
            if (recette == null) recette = new Recette("Recette "+numRecette);
        } else {
            bundle = new Bundle();
            int numRecette = ((MyApplication) getActivity().getApplicationContext()).getListeRecettes().size() +1;
            if (recette == null) recette = new Recette("Recette "+numRecette);
        }


        RadioGroup ItemtypeGroup = (RadioGroup) view.findViewById(R.id.tailles_tacos);

        ItemtypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged (RadioGroup group,int checkedId){
                switch (checkedId) {
                    case R.id.radio_M:
                        recette.setTailleTacos(Recette.TailleTacos.M);
                        break;
                    case R.id.radio_L:
                        recette.setTailleTacos(Recette.TailleTacos.L);
                        break;
                    case R.id.radio_XL:
                        recette.setTailleTacos(Recette.TailleTacos.XL);
                        break;
                    case R.id.radio_XXL:
                        recette.setTailleTacos(Recette.TailleTacos.XXL);
                        break;
                }
            }
        });

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

}
