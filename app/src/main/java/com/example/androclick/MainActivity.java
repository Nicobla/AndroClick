package com.example.androclick;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ((MyApplication) getApplicationContext()).setListeRecettes(insertListeRecettes());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.constraintLayout, new MyRecipes()) // premier onglet ouvert (défaut: MyRecipes())
                    .commitNow();
        }
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
        (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recipes:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.constraintLayout, new MyRecipes())
                            .commitNow();
                    break;
                case R.id.navigation_otacos:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.constraintLayout, new OTacos())
                            .commitNow();
                    break;
                case R.id.navigation_tacos:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.constraintLayout, new MakeTacos())
                            .commitNow();
                    break;
            }
            return true;
            }
        });
    }

    private ArrayList<Recette> insertListeRecettes() {
        ArrayList<Recette> recettes = new ArrayList<Recette>();
        //TODO : récupérer la liste des recettes depuis la BDD

        recettes.add(new Recette("Recette 1", "Tacos moyen",
                new Sauce[] {new Sauce("moutarde")},
                new Supplement[]{}));
        recettes.add(new Recette("Bon tacos", "Grand tacos",
                new Sauce[]{new Sauce("ketchup"), new Sauce("mayonnaise")},
                new Supplement[]{new Supplement("salade"), new Supplement("tomate"), new Supplement("oignon")}));
        recettes.add(new Recette("Recette 3", "Petit tacos",
                new Sauce[]{new Sauce("ketchup")},
                new Supplement[]{}));
        for (int i=4; i<=18; i++) {
            recettes.add(new Recette("Recette "+i, "Tacos moyen",new Sauce[]{},new Supplement[]{}));
        }

        return recettes;
    }

}
