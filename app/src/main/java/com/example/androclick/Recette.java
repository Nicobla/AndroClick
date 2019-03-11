package com.example.androclick;

import com.google.firebase.firestore.CollectionReference;

import java.io.Serializable;

public class Recette implements Serializable {
    String nom;
    String tailleTacos;
    Sauce[] sauces;
    Supplement[] supplements;

    public Recette(String nom, String tailleTacos, Sauce[]sauces, Supplement[]supplements) {
        super();
        this.nom = nom;
        this.tailleTacos = tailleTacos;
        this.sauces = sauces;
        this.supplements = supplements;
    }
    public Recette(String nom) {
        super();
        this.nom = nom;
        this.tailleTacos = "";
        this.sauces = new Sauce[]{};
        this.supplements = new Supplement[]{};
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getTailleTacos() {
        return tailleTacos;
    }
    public void setTailleTacos(String tailleTacos) {
        this.tailleTacos = tailleTacos;
    }

    public String[] getStrSauces() {
        String[] strSauces = new String[]{};
        for (Sauce sauce : getSauces()) {
            strSauces = combine(strSauces, new String[]{sauce.getNom()});
        }
        return strSauces;
    }
    public Sauce[] getSauces() {
        return sauces;
    }
    public void setSauces(Sauce[] sauces) {
        this.sauces = sauces;
    }
    public String[] getStrSupplements() {
        String[] strSupplements = new String[]{};
        for (Supplement supplement : getSupplements()) {
            strSupplements = combine(strSupplements, new String[]{supplement.getNom()});
        }
        return strSupplements;
    }
    public Supplement[] getSupplements() {
        return supplements;
    }
    public void setSupplements(Supplement[] supplements) {
        this.supplements = supplements;
    }

    public String getIngredients() {
        String[] ingredients = new String[]{tailleTacos};
        ingredients = combine(ingredients, getStrSauces());
        ingredients = combine(ingredients, getStrSupplements());

        String strIngredients = "";
        for (String ingredient : ingredients) {
            if (strIngredients != "")
                strIngredients += ", ";
            strIngredients += ingredient;
        }

        return strIngredients;
    }

    private String[] combine(String[] a, String[] b){
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}