package com.example.androclick;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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

        ((MyApplication) getApplicationContext()).setListeSauces(getAllSauces());
        ((MyApplication) getApplicationContext()).setListeViandes(getAllViandes());
        ((MyApplication) getApplicationContext()).setListeSupplements(getAllSupplements());

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



    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private ArrayList<Sauce> getAllSauces() {
        final ArrayList<Sauce> sauces = new ArrayList<>();
        db.collection("Sauce").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Sauce sauce = (Sauce) document.toObject(Sauce.class);
                        sauces.add(sauce);
                    }
                }
            }
        });
        return sauces;
    }
    private ArrayList<Viande> getAllViandes() {
        final ArrayList<Viande> viandes = new ArrayList<>();
        db.collection("Viande").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Viande viande = (Viande) document.toObject(Viande.class);
                        viandes.add(viande);
                    }
                }
            }
        });
        return viandes;
    }
    private ArrayList<Supplement> getAllSupplements() {
        final ArrayList<Supplement> supplements = new ArrayList<>();
        db.collection("Supplement").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Supplement supplement = (Supplement) document.toObject(Supplement.class);
                        supplements.add(supplement);
                    }
                }
            }
        });
        return supplements;
    }


    private ArrayList<Recette> getAllRecipes() {
        final ArrayList<Sauce> listeSauces = ((MyApplication) getApplicationContext()).getListeSauces();
        Log.e("AAAAAAAA", "nb sauces="+listeSauces.size());

        final ArrayList<Recette> recettes = new ArrayList<>();
        // [START get_all_users]


        db.collection("Recette").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful()){
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       Recette recette = new Recette(document.get("nom").toString());
                       recette.setTailleTacosByStr(document.get("tailleTacos").toString());
                       //for (int idxSauce=0; i<document.)
                       ArrayList sauces = new ArrayList<>();
                       /*for (Object i : (ArrayList)document.getData().get("sauces")) {
                           sauces.add((Long)i);
                       }*/

                       //recette.setSaucesByInt(sauces, listeSauces);

                       recettes.add(recette);
                   }
               }
           }
       });

        /*for (int i=0; i<recettes.size(); i++) {
            for (int j=0; j<recettes.)
            recettes.get(i).setSauces(new ArrayList<Sauce>(Arrays.asList()));
        }*/

        return recettes;
        // [END get_all_users]
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
