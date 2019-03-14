package com.example.androclick;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    SharedPreferences sharedPreferences;

    private ArrayList<O_Tacos> listeOTacos;

    private ArrayList<Sauce> listeSauces;
    private ArrayList<Viande> listeViandes;
    private ArrayList<Supplement> listeSupplements;

    private ArrayList<Recette> listeRecettes = new ArrayList<>();

    void addRecette(Recette recette) {
        listeRecettes.add(recette);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        View view = bottomNavigationView.findViewById(R.id.navigation_recipes);
        switch (item.getItemId()) {
            case R.id.nav_myrecipes:
                view.performClick();
                break;
            case R.id.nav_otacos:
                view = bottomNavigationView.findViewById(R.id.navigation_otacos);
                view.performClick();
                break;
            case R.id.nav_tacos:
                view = bottomNavigationView.findViewById(R.id.navigation_tacos);
                view.performClick();
                break;
            case R.id.nav_account:
                view.performClick();
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout,
                        new MyAccount()).commit();
                break;
            case R.id.nav_settings:
                view.performClick();
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout,
                        new Settings()).commit();
                break;
            case R.id.nav_about:
                view.performClick();
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout,
                        new About()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getData();

        drawer = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_myrecipes);


        ((MyApplication) getApplicationContext()).setListeOTacos(listeOTacos);

        ((MyApplication) getApplicationContext()).setListeSauces(listeSauces);
        ((MyApplication) getApplicationContext()).setListeViandes(listeViandes);
        ((MyApplication) getApplicationContext()).setListeSupplements(listeSupplements);

        ((MyApplication) getApplicationContext()).setListeRecettes(getAllRecipes());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.constraintLayout, new MyRecipes()) // premier onglet ouvert (défaut: MyRecipes())
                    .commitNow();
        }
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
        (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recipes:
                        navigationView.setCheckedItem(R.id.nav_myrecipes);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.constraintLayout, new MyRecipes())
                                .commitNow();
                        break;
                    case R.id.navigation_otacos:
                        navigationView.setCheckedItem(R.id.nav_otacos);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.constraintLayout, new OTacos())
                                .commitNow();
                        break;
                    case R.id.navigation_tacos:
                        navigationView.setCheckedItem(R.id.nav_tacos);
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

    private ArrayList<O_Tacos> getAllOTacos() {
        final ArrayList<O_Tacos> otacos = new ArrayList<>();

        db.collection("OTacos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        O_Tacos o_tacos = (O_Tacos) document.toObject(O_Tacos.class);
                        otacos.add(o_tacos);
                    }
                }
            }
        });

        return otacos;
    }

    private ArrayList<Sauce> getAllSauces() {
        final ArrayList<Sauce> sauces = new ArrayList<>();
        db.collection("Sauce").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
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
        db.collection("Viande").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
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
        db.collection("Supplement").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Supplement supplement = (Supplement) document.toObject(Supplement.class);
                        supplements.add(supplement);
                    }
                }
            }
        });
        return supplements;
    }


    public ArrayList<Recette> getAllRecipes() {
        /*final ArrayList<Sauce> listeSauces = ((MyApplication) getApplicationContext()).getListeSauces();
        final ArrayList<Viande> listeViandes = ((MyApplication) getApplicationContext()).getListeViandes();
        final ArrayList<Supplement> listeSupplements = ((MyApplication) getApplicationContext()).getListeSupplements();*/

        if (listeSauces.size() <= 0 || listeViandes.size() <= 0 || listeSupplements.size() <= 0) {
            Log.e("DEBUG", "Impossible de set la liste des recettes car les listes sont vides :(");
            return new ArrayList<>();
        }


        // [START get_all_users]

        db.collection("Recette").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Recette recette = new Recette();
                        Object nom = document.get("nom");
                        Object tailleTacos = document.get("tailleTacos");
                        Object isFavorite = document.get("isFavorite");

                        ArrayList sauces = (ArrayList) document.get("sauces");
                        ArrayList viandes = (ArrayList) document.get("viandes");
                        ArrayList supplements = (ArrayList) document.get("supplements");

                        if (nom != null) recette.setNom(nom.toString());
                        if (tailleTacos != null)
                            recette.setTailleTacosByStr(tailleTacos.toString());
                        if (isFavorite != null) recette.setFavorite((boolean) isFavorite);

                        if (sauces != null) {
                            for (int i = 0; i < sauces.size(); i++) {
                                Long idxSauce = (Long) sauces.get(i);
                                Sauce sauce = listeSauces.get(idxSauce.intValue() - 1);
                                recette.addSauce(sauce);
                            }
                        }
                        if (viandes != null) {
                            for (int i = 0; i < viandes.size(); i++) {
                                Long idxViande = (Long) viandes.get(i);
                                Viande viande = listeViandes.get(idxViande.intValue() - 1);
                                recette.addViande(viande);
                            }
                        }
                        if (supplements != null) {
                            for (int i = 0; i < supplements.size(); i++) {
                                Long idxSupplement = (Long) supplements.get(i);
                                Supplement supplement = listeSupplements.get(idxSupplement.intValue() - 1);
                                recette.addSupplement(supplement);
                            }
                        }
                        Log.e("DEBUG", "Recette:" + recette.getNom());
                        addRecette(recette);
                    }
                }
            }
        });
        Log.e("DEBUG", "nbRecettes=" + listeRecettes.size());

        return listeRecettes;
        // [END get_all_users]
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("DEBUG", "Fermeture de l'app");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("needToDownload", false);
        editor.apply();

        //TODO : enregistrer les données

        String filename = "sauces";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
            File directory = getApplicationContext().getFilesDir();
            File file = new File(directory, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("DEBUG", "App fermée");

    }

    private void getData() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean needToDownload = sharedPreferences.getBoolean("needToDownload", true);
        Log.e("DEBUG", "NeedToDownload=" + needToDownload);

        listeOTacos = getAllOTacos();
        listeSauces = getAllSauces();
        listeViandes = getAllViandes();
        listeSupplements = getAllSupplements();
        if (needToDownload) {
            //mettre les gets là
        } else {
            //charger les données
            String filename = "sauces";
            File directory;
            if (filename.isEmpty()) {
                directory = getFilesDir();
                Log.e("DEBUG", "Fichier " + filename + " vides");
            } else {
                directory = getDir(filename, MODE_PRIVATE);
                Log.e("DEBUG", "Fichier " + filename + " remplit");
            }
            File[] files = directory.listFiles();
            Log.e("DEBUG", "filesToString=" + files.toString());
            for (File file : files) {
                Log.e("DEBUG", "Ligne X=" + file.toString());
            }


        }


    }

}
