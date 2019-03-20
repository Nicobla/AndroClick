package com.example.androclick;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class MakeTacos_1 extends Fragment {

    final int NB_MAX_SAUCES = 2;
    final int NB_MAX_SUPPLEMENTS = 8;

    private Bundle bundle;
    private Recette recette;

    public MakeTacos_1() {
        // Required empty public constructor
    }

//    public void restart() {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        recette = (Recette) bundle.getSerializable("recette");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_1, container, false);

        if (bundle == null) {
            bundle = new Bundle();
        } else {
            //recette = (Recette) bundle.getSerializable("recette");
        }
        int numRecette = ((MyApplication) getActivity().getApplicationContext()).getNbRecettes() + 1;
        if (recette == null) recette = new Recette("Recette " + numRecette);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.tailles_tacos);

        switch (recette.getTailleTacos()) {
            case M:
                radioGroup.check(R.id.radio_M);
                break;
            case L:
                radioGroup.check(R.id.radio_L);
                break;
            case XL:
                radioGroup.check(R.id.radio_XL);
                break;
            case XXL:
                radioGroup.check(R.id.radio_XXL);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
                ((MyApplication)getActivity().getApplicationContext()).listePositionsViandes = new ArrayList<>();
                recette.setViandes(new ArrayList<Viande>());
                bundle.putSerializable("recette", recette);
                setArguments(bundle);
            }
        });

        Button button_random = (Button) view.findViewById(R.id.button_random);
        button_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Génération aléatoire de la recette
                recette = randRecette();
//                bundle.putSerializable("recette", recette);
//                setArguments(bundle);
//                restart();
                ((MyApplication) getActivity().getApplicationContext()).addToListeRecettes(recette);

                // Réinitialisation des recyclerview et redirection vers mes recettes
                closeFragment();
            }
        });

        bundle.putSerializable("recette", recette);
        setArguments(bundle);

        return view;
    }

    int rand(int min, int max) {
        return (int) (Math.random() * (max + 1 - min)) + min;
    }

    Recette randRecette() {
        ArrayList<Sauce> listeSauces = ((MyApplication) getActivity().getApplicationContext()).getListeSauces();
        ArrayList<Viande> listeViandes = ((MyApplication) getActivity().getApplicationContext()).getListeViandes();
        ArrayList<Supplement> listeSupplements = ((MyApplication) getActivity().getApplicationContext()).getListeSupplements();

        if (listeSauces.size() == 0 || listeViandes.size() == 0 || listeSupplements.size() == 0) {
            return new Recette();
        }

        Recette recette = new Recette(this.recette.getNom() + " (aléatoire)");

        int idxTaille = rand(1, 4);
        int nbViandes = 1;

        switch (idxTaille) {
            case 1:
                nbViandes = rand(0, 1);
                recette.setTailleTacos(Recette.TailleTacos.M);
                break;
            case 2:
                nbViandes = rand(0, 2);
                recette.setTailleTacos(Recette.TailleTacos.L);
                break;
            case 3:
                nbViandes = rand(0, 3);
                recette.setTailleTacos(Recette.TailleTacos.XL);
                break;
            case 4:
                nbViandes = rand(0, 4);
                recette.setTailleTacos(Recette.TailleTacos.XXL);
                break;
        }

        int nbSauces = rand(0, NB_MAX_SAUCES);
        for (int i = 0; i < nbSauces; i++) {
            int numSauce = rand(0, listeSauces.size() - 1);
            recette.addSauce(listeSauces.get(numSauce));
        }
        for (int i = 0; i < nbViandes; i++) {
            int numViande = rand(0, listeViandes.size() - 1);
            recette.addViande(listeViandes.get(numViande));
        }
        int nbSupplements = rand(0, NB_MAX_SUPPLEMENTS);
        for (int i = 0; i < nbSupplements; i++) {
            int numSupplements = rand(0, listeSupplements.size() - 1);
            recette.addSupplement(listeSupplements.get(numSupplements));
        }

        return recette;
    }

    public void closeFragment() {
        ((MyApplication) this.getActivity().getApplicationContext()).uncheckAllIngredients();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigationView);
        View view = bottomNavigationView.findViewById(R.id.navigation_recipes);
        view.performClick();
    }

}
