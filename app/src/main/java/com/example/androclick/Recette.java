package com.example.androclick;

import java.io.Serializable;
import java.util.ArrayList;


public class Recette implements Serializable {
    public enum TailleTacos implements Serializable {
        M {
            @Override
            public String toString() {
                return "M";
            }
        },
        L {
            @Override
            public String toString() {
                return "L";
            }
        },
        XL {
            @Override
            public String toString() {
                return "XL";
            }
        },
        XXL {
            @Override
            public String toString() {
                return "XXL";
            }
        }
    }

    String nom;
    TailleTacos tailleTacos;
    ArrayList<Sauce> sauces;
    ArrayList<Viande> viandes;
    ArrayList<Supplement> supplements;

    public Recette(String nom, TailleTacos tailleTacos, ArrayList<Sauce> sauces, ArrayList<Viande> viandes, ArrayList<Supplement> supplements) {
        super();
        this.nom = nom;
        this.tailleTacos = tailleTacos;
        this.sauces = sauces;
        this.viandes = viandes;
        this.supplements = supplements;
    }
    public Recette(String nom) {
        super();
        this.nom = nom;
        this.tailleTacos = TailleTacos.L;
        this.sauces = new ArrayList<Sauce>(){};
        this.viandes = new ArrayList<Viande>(){};
        this.supplements = new ArrayList<Supplement>(){};
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStrTailleTacos() {return getTailleTacos().toString();}
    public TailleTacos getTailleTacos() {
        return tailleTacos;
    }
    public void setTailleTacos(TailleTacos tailleTacos) {
        this.tailleTacos = tailleTacos;
    }

    public void addSauce(Sauce sauce) {
        ArrayList<Sauce> sauces = this.getSauces();
        sauces.add(sauce);
        this.setSauces(sauces);
    }
    public void removeSauce(Sauce sauce) {
        ArrayList<Sauce> sauces = this.getSauces();
        sauces.remove(sauce);
        this.setSauces(sauces);
    }
    public String[] getStrSauces() {
        String[] strSauces = new String[]{};
        for (Sauce sauce : getSauces()) {
            strSauces = combine(strSauces, new String[]{sauce.getNom()});
        }
        return strSauces;
    }
    public ArrayList<Sauce> getSauces() {
        return sauces;
    }
    public void setSauces(ArrayList<Sauce> sauces) {
        this.sauces = sauces;
    }

    public void addViande(Viande viande) {
        ArrayList<Viande> viandes = this.getViandes();
        viandes.add(viande);
        this.setViandes(viandes);
    }
    public void removeViande(Viande viande) {
        ArrayList<Viande> viandes = this.getViandes();
        viandes.remove(viande);
        this.setViandes(viandes);
    }
    public String[] getStrViandes() {
        String[] strViandes = new String[]{};
        for (Viande viande : getViandes()) {
            strViandes = combine(strViandes, new String[]{viande.getNom()});
        }
        return strViandes;
    }
    public ArrayList<Viande> getViandes() {
        return viandes;
    }
    public void setViandes(ArrayList<Viande> viandes) {
        this.viandes = viandes;
    }

    public void addSupplement(Supplement supplement) {
        ArrayList<Supplement> supplements = this.getSupplements();
        supplements.add(supplement);
        this.setSupplements(supplements);
    }
    public void removeSupplement(Supplement supplement) {
        ArrayList<Supplement> supplements = this.getSupplements();
        supplements.remove(supplement);
        this.setSupplements(supplements);
    }
    public String[] getStrSupplements() {
        String[] strSupplements = new String[]{};
        for (Supplement supplement : getSupplements()) {
            strSupplements = combine(strSupplements, new String[]{supplement.getNom()});
        }
        return strSupplements;
    }
    public ArrayList<Supplement> getSupplements() {
        return supplements;
    }
    public void setSupplements(ArrayList<Supplement> supplements) {
        this.supplements = supplements;
    }


    public String getIngredients() {
        String[] ingredients = new String[]{};
        ingredients = combine(ingredients, getStrSauces());
        ingredients = combine(ingredients, getStrViandes());
        ingredients = combine(ingredients, getStrSupplements());

        String strIngredients = tailleTacos.toString();
        for (String ingredient : ingredients) {
            if (strIngredients != "")
                strIngredients += ", ";
            strIngredients += ingredient.toLowerCase();
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