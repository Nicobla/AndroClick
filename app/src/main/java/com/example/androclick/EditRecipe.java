package com.example.androclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditRecipe extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {

    final int NB_MAX_SAUCES = 2;
    int nbSauces = 0;

    ListView lvSauces;
    ArrayList<Sauce> listeSauces;
    SauceAdapter sauceAdapter;

    ListView lvSupplements;
    ArrayList<Supplement> listeSupplements;
    SupplementAdapter supplementsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        ImageButton button_back = (ImageButton) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { finish(); }
        });

        lvSauces = (ListView)findViewById(R.id.list_sauces);
        displayListeSauces();

        lvSupplements = (ListView)findViewById(R.id.list_supplements);
        displayListeSupplements();

        Button button_apply = (Button) findViewById(R.id.button_apply);
        button_apply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //TODO : modifier recette
                finish();
            }
        });
    }

    private void displayListeSauces() {
        listeSauces = new ArrayList<Sauce>();
        //TODO : récupérer la liste des sauces depuis BDD
        listeSauces.add(new Sauce("Mayonnaise"));
        listeSauces.add(new Sauce("Moutarde"));
        listeSauces.add(new Sauce("Ketchup"));
        for (int i=1; i<=4; i++) {
            listeSauces.add(new Sauce("Exemple "+i));
        }

        sauceAdapter = new SauceAdapter(listeSauces, this);
        lvSauces.setAdapter(sauceAdapter);
    }
    private void displayListeSupplements() {
        listeSupplements = new ArrayList<Supplement>();
        //TODO : récupérer la liste des suppléments depuis BDD
        listeSupplements.add(new Supplement("Salade"));
        listeSupplements.add(new Supplement("Tomate"));
        listeSupplements.add(new Supplement("Oignon"));
        for (int i=1; i<=15; i++) {
            listeSupplements.add(new Supplement("Exemple "+i));
        }

        supplementsAdapter = new SupplementAdapter(listeSupplements, this);
        lvSupplements.setAdapter(supplementsAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lvSauces.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Sauce s = listeSauces.get(pos);
            s.setSelected(isChecked);
            // Compteur de sauces pour limiter
            if (isChecked)
                nbSauces++;
            else
                nbSauces--;
            if (nbSauces >= NB_MAX_SAUCES) {
                Toast.makeText(getApplicationContext(), "MAX SAUCES ("+nbSauces+")", Toast.LENGTH_SHORT).show();
                //TODO : griser checkboxes sauf celles déjà cochées
            }
        }

        int pos2 = lvSupplements.getPositionForView(buttonView);
        if (pos2 != ListView.INVALID_POSITION) {
            Supplement s = listeSupplements.get(pos2);
            s.setSelected(isChecked);
        }
    }

}
