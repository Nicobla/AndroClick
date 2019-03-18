package com.example.androclick;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Data {

    public static ArrayList<O_Tacos> getAllOTacosFromDb() {
        final ArrayList<O_Tacos> otacos = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public static ArrayList<Sauce> getAllSaucesFromDb() {
        final ArrayList<Sauce> sauces = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public static ArrayList<Viande> getAllViandesFromDb() {
        final ArrayList<Viande> viandes = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Viande").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Viande viande = (Viande) document.toObject(Viande.class);
//                        Log.e("Data - getAllViandesDb", "Viande:" + viande.getNom());
                        viandes.add(viande);
                    }
                }
            }
        });
//        Log.e("Data - getAllViandesDb", "nbViandes=" + viandes.size());
        return viandes;
    }

    public static ArrayList<Supplement> getAllSupplementsFromDb() {
        final ArrayList<Supplement> supplements = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public static ArrayList<Recette> getAllRecipesFromDb(ArrayList<Sauce> listeSauces, ArrayList<Viande> listeViandes, ArrayList<Supplement> listeSupplements) {
        if (listeSauces != null && listeViandes != null && listeSupplements != null) {
            if (listeSauces.size() <= 0 || listeViandes.size() <= 0 || listeSupplements.size() <= 0) {
                Log.e("Data - getAllRecipesDb", "Impossible de set la liste des recettes car les listes sont vides :(");
                return new ArrayList<>();
            }
        } else {
            Log.e("Data - getAllRecipesDb", "Impossible de set la liste des recettes car les listes sont null :(");
            return new ArrayList<>();
        }

        final ArrayList<Sauce> listSauces = listeSauces;
        final ArrayList<Viande> listViandes = listeViandes;
        final ArrayList<Supplement> listSupplements = listeSupplements;

        // [START get_all_users]

        final ArrayList<Recette> recettes = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                                Sauce sauce = listSauces.get(idxSauce.intValue() - 1);
                                recette.addSauce(sauce);
                            }
                        }
                        if (viandes != null) {
                            for (int i = 0; i < viandes.size(); i++) {
                                Long idxViande = (Long) viandes.get(i);
                                Viande viande = listViandes.get(idxViande.intValue() - 1);
                                recette.addViande(viande);
                            }
                        }
                        if (supplements != null) {
                            for (int i = 0; i < supplements.size(); i++) {
                                Long idxSupplement = (Long) supplements.get(i);
                                Supplement supplement = listSupplements.get(idxSupplement.intValue() - 1);
                                recette.addSupplement(supplement);
                            }
                        }
                        Log.e("Data - getAllRecipesDb", "Recette:" + recette.getNom());
                        recettes.add(recette);
                    }
                }
            }
        });
        Log.e("Data - getAllRecipesDb", "nbRecettes=" + recettes.size());

        return recettes;
        // [END get_all_users]
    }

}
