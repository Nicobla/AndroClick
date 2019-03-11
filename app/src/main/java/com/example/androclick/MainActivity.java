package com.example.androclick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.QueryListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DocSnippets";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ((MyApplication) getApplicationContext()).setListeRecettes(getAllRecype());

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

 /*   private ArrayList<Recette> insertListeRecettes() {
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
    */

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int i =0;
    private ArrayList<Recette> getAllRecype() {
        final ArrayList<Recette> recettes = new ArrayList<Recette>();
        // [START get_all_users]
        db.collection("Recettes").get().addOnCompleteListener( new OnCompleteListener <QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task <QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    recettes.add(new Recette( document.getId()));
                    }
                }
            }
        }
    );

        return recettes;
        // [END get_all_users]
    }


}
