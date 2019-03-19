package com.example.androclick;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        recette = (Recette) bundle.getSerializable("recette");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_5, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));


        final EditText nomRecette = (EditText) view.findViewById(R.id.recipe_name);
        nomRecette.setText(recette.getNom());

        Button button_createRecipe = (Button) view.findViewById(R.id.button_createRecipe);
        button_createRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enregistrement de la recette dans la liste des recettes
                ((MyApplication) getActivity().getApplicationContext()).addToListeRecettes(recette);

                // Réinitialisation des recyclerview et redirection vers mes recettes
                closeFragment();

//                ((MakeTacos) getParentFragment()).getActivity();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.constraintLayout, new MyRecipes())
//                        .commitNow();
            }
        });


        nomRecette.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                recette.setNom(s.toString());
            }
        });


        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        //Ferme le clavier
        View viewClavier = getActivity().getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (viewClavier != null) {
            imm.hideSoftInputFromWindow(viewClavier.getWindowToken(), 0);
        }
    }

    public void closeFragment() {
        ((MyApplication) this.getActivity().getApplicationContext()).uncheckAllIngredients();

        getParentFragment().onDestroy();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigationView);
        View view = bottomNavigationView.findViewById(R.id.navigation_recipes);
        view.performClick();


        /*getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.constraintLayout, new MyRecipes())
                .commitNow();*/
    }

}
