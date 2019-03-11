package com.example.androclick;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MakeTacos_5 extends Fragment {

    private Bundle bundle;
    private Recette recette;

    public MakeTacos_5() {
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
        View view = inflater.inflate(R.layout.make_tacos_fragment_5, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));

        final EditText nomRecette = (EditText)view.findViewById(R.id.recipe_name);
        nomRecette.setText(recette.getNom());

        Button button_createRecipe = (Button) view.findViewById(R.id.button_createRecipe);
        button_createRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //Enregistrement de la recette dans la liste des recettes
                ((MyApplication) getActivity().getApplicationContext()).addToListeRecettes(recette);

                //TODO :  réinitialiser les fragments (adapters ? relancer l'activité ?)

                //((MakeTacos) getParentFragment()).getActivity();
                closeFragment();

                /*//Redirection vers mes recettes
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new MyRecipes())
                        .commitNow();*/
            }
        });


        nomRecette.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                recette.setNom(s.toString());
            }
        });


        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

    public void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.constraintLayout, new MyRecipes())
                .commitNow();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
