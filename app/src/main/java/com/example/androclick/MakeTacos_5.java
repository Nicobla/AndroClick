package com.example.androclick;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MakeTacos_5 extends Fragment {

    private Recette recette = new Recette("");

    public MakeTacos_5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_5, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));

        final EditText nomRecette = (EditText)view.findViewById(R.id.recipe_name);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recette = (Recette) bundle.getSerializable("recette");
            nomRecette.setText(recette.getNom());
        } else {
            bundle = new Bundle();
        }

        Button button_createRecipe = (Button) view.findViewById(R.id.button_createRecipe);
        button_createRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //Enregistrement de la recette dans la liste des recettes
                ArrayList<Recette> listeRecettes = ((MyApplication) getActivity().getApplicationContext()).getListeRecettes();
                listeRecettes.add(recette);

                //TODO :  réinitialiser les fragments

                //Redirection vers mes recettes
                ((MyApplication) getActivity().getApplicationContext()).setListeRecettes(listeRecettes);
                getParentFragment().getFragmentManager().beginTransaction()
                        .replace(R.id.constraintLayout, new MyRecipes())
                        .commitNow();
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

        //Toast.makeText(getContext(), recette.getNom() + "/" + nomRecette.getText().toString(), Toast.LENGTH_SHORT).show();

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
