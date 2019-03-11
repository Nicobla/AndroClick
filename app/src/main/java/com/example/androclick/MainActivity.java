package com.example.androclick;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ((MyApplication) getApplicationContext()).setListeSauces(insertListeSauces());
        ((MyApplication) getApplicationContext()).setListeViandes(insertListeViandes());
        ((MyApplication) getApplicationContext()).setListeSupplements(insertListeSupplements());

        ((MyApplication) getApplicationContext()).setListeRecettes(getAllRecipes());

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
        final ArrayList<Sauce> sauces = ((MyApplication) getApplicationContext()).getListeSauces();
        final ArrayList<Viande> viandes = ((MyApplication) getApplicationContext()).getListeViandes();
        final ArrayList<Supplement> supplements = ((MyApplication) getApplicationContext()).getListeSupplements();

        ArrayList<Recette> recettes = new ArrayList<Recette>();
        //TODO : récupérer la liste des recettes depuis la BDD

        recettes.add(new Recette("Mon premier tacos",
                Recette.TailleTacos.L,
                new ArrayList<Sauce>(Arrays.asList(sauces.get(1))),
                new ArrayList<Viande>(),
                new ArrayList<Supplement>()));
        recettes.add(new Recette("Bon tacos",
                Recette.TailleTacos.XL,
                new ArrayList<Sauce>(Arrays.asList(sauces.get(2), sauces.get(0))),
                new ArrayList<Viande>(Arrays.asList(viandes.get(1))),
                new ArrayList<Supplement>(Arrays.asList(supplements.get(0), supplements.get(1), supplements.get(2)))));
        recettes.add(new Recette("Petite faim",
                Recette.TailleTacos.M,
                new ArrayList<Sauce>(Arrays.asList(sauces.get(2))),
                new ArrayList<Viande>(Arrays.asList(viandes.get(2))),
                new ArrayList<Supplement>()));
        for (int i=4; i<=7; i++) {
            recettes.add(new Recette("Recette "+i,
                    Recette.TailleTacos.L,
                    new ArrayList<Sauce>(),
                    new ArrayList<Viande>(),
                    new ArrayList<Supplement>()));
        }

        return recettes;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Recette> getAllRecipes() {
        final ArrayList<Recette> recettes = new ArrayList<Recette>();
        // [START get_all_users]
        db.collection("Recettes").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful()){
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       Log.e("DEBUG", document.getId() + " => " + document.getData());
                       Log.e("DEBUG", document.getData().toString());
                       recettes.add(new Recette( document.getId()));
                   }
               }
           }
       });

        return recettes;
        // [END get_all_users]
    }

    private ArrayList<Sauce> insertListeSauces() {
        ArrayList<Sauce> listeSauces = new ArrayList<Sauce>();
        //TODO : récupérer la liste des sauces depuis BDD

        listeSauces.add(new Sauce("Mayonnaise"));
        listeSauces.add(new Sauce("Moutarde"));
        listeSauces.add(new Sauce("Ketchup"));
        for (int i = 4; i <= 8; i++) {
            listeSauces.add(new Sauce("Sauce n. " + i));
        }
        return listeSauces;
    }

    private ArrayList<Viande> insertListeViandes() {
        ArrayList<Viande> listeViandes = new ArrayList<Viande>();
        //TODO : récupérer la liste des viandes depuis BDD

        listeViandes.add(new Viande("Steak"));
        listeViandes.add(new Viande("Poulet"));
        listeViandes.add(new Viande("Jambon"));
        for (int i=4; i<=7; i++) {
            listeViandes.add(new Viande("Viande n. "+i));
        }
        return listeViandes;
    }

    private ArrayList<Supplement> insertListeSupplements() {
        ArrayList<Supplement> listeSupplements = new ArrayList<Supplement>();
        //TODO : récupérer la liste des suppléments depuis BDD

        listeSupplements.add(new Supplement("Salade"));
        listeSupplements.add(new Supplement("Tomate"));
        listeSupplements.add(new Supplement("Oignon"));
        for (int i=1; i<=15; i++) {
            listeSupplements.add(new Supplement("Supplément n. "+i));
        }
        return listeSupplements;
    }
}
