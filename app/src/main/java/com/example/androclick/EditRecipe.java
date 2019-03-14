package com.example.androclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class EditRecipe extends AppCompatActivity {//implements android.widget.CompoundButton.OnCheckedChangeListener {

    private Recette recette;
    private int position;

//    final int NB_MAX_SAUCES = 2;
//    int nbSauces = 0;

    ArrayList<Sauce> listeSauces;
    ArrayList<Viande> listeViandes;
    ArrayList<Supplement> listeSupplements;

    private RecyclerView rvSauces;
    private RecyclerView.Adapter saucesAdapter;
    private RecyclerView.LayoutManager lmSauces;

    private RecyclerView rvViandes;
    private RecyclerView.Adapter viandesAdapter;
    private RecyclerView.LayoutManager lmViandes;

    private RecyclerView rvSupplements;
    private RecyclerView.Adapter supplementsAdapter;
    private RecyclerView.LayoutManager lmSupplements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        ((MyApplication) getApplicationContext()).uncheckAllIngredients();

        recette = (Recette) getIntent().getSerializableExtra("recette");
        position = (int) getIntent().getSerializableExtra("position");

        ImageButton button_back = (ImageButton) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvSauces = (RecyclerView) findViewById(R.id.list_sauces);
        displayListeSauces();

        rvViandes = (RecyclerView) findViewById(R.id.list_viandes);
        displayListeViandes();

        rvSupplements = (RecyclerView) findViewById(R.id.list_supplements);
        displayListeSupplements();


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tailles_tacos);

        switch (recette.getStrTailleTacos()) {
            case "M":
                radioGroup.check(R.id.radio_M);
                break;
            case "L":
                radioGroup.check(R.id.radio_L);
                break;
            case "XL":
                radioGroup.check(R.id.radio_XL);
                break;
            case "XXL":
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
            }
        });

        rvSauces.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Sauce s = listeSauces.get(position);
                        s.setSelected(!s.isSelected());
                        if (s.isSelected()) {
                            recette.addSauce(s);
                            Log.e("Click", "Ajout de la sauce " + s.getNom());
                        } else {
                            recette.removeSauce(s);
                            Log.e("Click", "Suppression de la sauce " + s.getNom());
                        }
                    }
                })
        );
        rvViandes.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Viande v = listeViandes.get(position);
                        v.setSelected(!v.isSelected());
                        if (v.isSelected()) {
                            recette.addViande(v);
                            Log.e("Click", "Ajout de la viande " + v.getNom());
                        } else {
                            recette.removeViande(v);
                            Log.e("Click", "Suppression de la viande " + v.getNom());
                        }
                    }
                })
        );
        rvSupplements.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Supplement s = listeSupplements.get(position);
                        s.setSelected(!s.isSelected());
                        if (s.isSelected()) {
                            recette.addSupplement(s);
                            Log.e("Click", "Ajout du supplément " + s.getNom());
                        } else {
                            recette.removeSupplement(s);
                            Log.e("Click", "Suppression du supplément " + s.getNom());
                        }
                    }
                })
        );


        Button button_apply = (Button) findViewById(R.id.button_apply);
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enregistrement de la recette
                ((MyApplication) getApplicationContext()).setRecette(position, recette);

                finish();
            }
        });
    }

    private void displayListeSauces() {
        listeSauces = ((MyApplication) getApplicationContext()).getListeSauces();

        for (Sauce sauce1 : listeSauces) {
            for (Sauce sauce : recette.getSauces()) {
                if (sauce.getNom().equals(sauce1.getNom())) {
                    sauce.setSelected(true);
                    listeSauces.set(listeSauces.indexOf(sauce1), sauce);
                }
            }
        }
        lmSauces = new LinearLayoutManager(getApplicationContext());
        rvSauces.setLayoutManager(lmSauces);

        saucesAdapter = new SaucesAdapter(listeSauces);
        saucesAdapter.notifyDataSetChanged();
        rvSauces.setAdapter(saucesAdapter);
    }

    private void displayListeViandes() {
        listeViandes = ((MyApplication) getApplicationContext()).getListeViandes();

        for (Viande viande1 : listeViandes) {
            for (Viande viande : recette.getViandes()) {
                if (viande.getNom().equals(viande1.getNom())) {
                    viande.setSelected(true);
                    listeViandes.set(listeViandes.indexOf(viande1), viande);
                }
            }
        }
        lmViandes = new LinearLayoutManager(getApplicationContext());
        rvViandes.setLayoutManager(lmViandes);

        viandesAdapter = new ViandesAdapter(listeViandes);
        viandesAdapter.notifyDataSetChanged();
        rvViandes.setAdapter(viandesAdapter);
    }

    private void displayListeSupplements() {
        listeSupplements = ((MyApplication) getApplicationContext()).getListeSupplements();

        for (Supplement supplement1 : listeSupplements) {
            for (Supplement supplement : recette.getSupplements()) {
                if (supplement.getNom().equals(supplement1.getNom())) {
                    supplement.setSelected(true);
                    listeSupplements.set(listeSupplements.indexOf(supplement1), supplement);
                }
            }
        }
        lmSupplements = new LinearLayoutManager(getApplicationContext());
        rvSupplements.setLayoutManager(lmSupplements);

        supplementsAdapter = new SupplementsAdapter(listeSupplements);
        supplementsAdapter.notifyDataSetChanged();
        rvSupplements.setAdapter(supplementsAdapter);
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        int pos = lvSauces.getPositionForView(buttonView);
//        if (pos != ListView.INVALID_POSITION) {
//            Sauce s = listeSauces.get(pos);
//            s.setSelected(isChecked);
//            // Compteur de sauces pour limiter
//            if (isChecked)
//                nbSauces++;
//            else
//                nbSauces--;
//            if (nbSauces >= NB_MAX_SAUCES) {
//                Toast.makeText(getApplicationContext(), "MAX SAUCES ("+nbSauces+")", Toast.LENGTH_SHORT).show();
//                //TODO : griser checkboxes sauf celles déjà cochées
//            }
//        }
//
//        int pos2 = lvSupplements.getPositionForView(buttonView);
//        if (pos2 != ListView.INVALID_POSITION) {
//            Supplement s = listeSupplements.get(pos2);
//            s.setSelected(isChecked);
//        }
//    }

}
