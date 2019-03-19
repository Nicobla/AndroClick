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

    private String nom;
    private TailleTacos tailleTacos;
    private ArrayList<Sauce> sauces;
    private ArrayList<Viande> viandes;
    private ArrayList<Supplement> supplements;

    private boolean isFavorite;

    /*public Recette(String nom, String tailleTacos, ArrayList<Sauce> sauces, ArrayList<Viande> viandes, ArrayList<Supplement> supplements) {
        TailleTacos tailleTacos2 = TailleTacos.L;
        switch (tailleTacos) {
            case "M": tailleTacos2 = TailleTacos.M; break;
            case "L": tailleTacos2 = TailleTacos.L; break;
            case "XL": tailleTacos2 = TailleTacos.XL; break;
            case "XXL": tailleTacos2 = TailleTacos.XXL; break;
        }
        this.nom = nom;
        this.tailleTacos = tailleTacos2;
        this.sauces = sauces;
        this.viandes = viandes;
        this.supplements = supplements;
    }*/

    public Recette(String nom, TailleTacos tailleTacos, ArrayList<Sauce> sauces, ArrayList<Viande> viandes, ArrayList<Supplement> supplements, boolean isFavorite) {
        super();
        this.nom = nom;
        this.tailleTacos = tailleTacos;
        this.sauces = sauces;
        this.viandes = viandes;
        this.supplements = supplements;
        this.isFavorite = isFavorite;
    }

    public Recette(String nom, TailleTacos tailleTacos, ArrayList<Sauce> sauces, ArrayList<Viande> viandes, ArrayList<Supplement> supplements) {
        this(nom, tailleTacos, sauces, viandes, supplements, false);
    }

    public Recette(String nom) {
        this(nom, TailleTacos.L, new ArrayList<Sauce>(), new ArrayList<Viande>(), new ArrayList<Supplement>());
    }

    public Recette() {
        this("Recette sans nom");
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStrTailleTacos() {
        return getTailleTacos().toString();
    }

    public TailleTacos getTailleTacos() {
        return tailleTacos;
    }

    public void setTailleTacos(TailleTacos tailleTacos) {
        this.tailleTacos = tailleTacos;
    }

    public void setTailleTacosByStr(String tailleTacos) {
        switch (tailleTacos) {
            case "M":
                this.tailleTacos = TailleTacos.M;
                break;
            case "L":
                this.tailleTacos = TailleTacos.L;
                break;
            case "XL":
                this.tailleTacos = TailleTacos.XL;
                break;
            case "XXL":
                this.tailleTacos = TailleTacos.XXL;
                break;
        }
    }


    public void addSauce(Sauce sauce) {
        ArrayList<Sauce> sauces = this.getSauces();
        if (sauces.indexOf(sauce) == -1) //Si la sauce n'est pas déjà dans la liste
            sauces.add(sauce);
        this.setSauces(sauces);
    }

    public void removeSauce(Sauce sauce) {
        ArrayList<Sauce> sauces = this.getSauces();
        sauces.remove(sauce);
        this.setSauces(sauces);
    }

    ArrayList<String> getStrSauces() {
        ArrayList<String> strSauces = new ArrayList<>();
        for (Sauce sauce : getSauces()) {
            strSauces.add(sauce.getNom());
            //strSauces = combine(strSauces, new String[]{sauce.getNom()});
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
        if (viandes.indexOf(viande) == -1) //Si la viande n'est pas déjà dans la liste
            viandes.add(viande);
        this.setViandes(viandes);
    }

    public void removeViande(Viande viande) {
        ArrayList<Viande> viandes = this.getViandes();
        viandes.remove(viande);
        this.setViandes(viandes);
    }

    ArrayList<String> getStrViandes() {
        ArrayList<String> strViandes = new ArrayList<>();
        for (Viande viande : getViandes()) {
            strViandes.add(viande.getNom());
            //strViandes = combine(strViandes, new String[]{viande.getNom()});
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
        if (supplements.indexOf(supplement) == -1) //Si le supplément n'est pas déjà dans la liste
            supplements.add(supplement);
        this.setSupplements(supplements);
    }

    public void removeSupplement(Supplement supplement) {
        ArrayList<Supplement> supplements = this.getSupplements();
        supplements.remove(supplement);
        this.setSupplements(supplements);
    }

    ArrayList<String> getStrSupplements() {
        ArrayList<String> strSupplements = new ArrayList<>();
        for (Supplement supplement : getSupplements()) {
            strSupplements.add(supplement.getNom());
            //strSupplements = combine(strSupplements, new String[]{supplement.getNom()});
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
        ArrayList<String> ingredients = new ArrayList<>();
        //ingredients = combine(ingredients, getStrSauces());
        //ingredients = combine(ingredients, getStrViandes());
        //ingredients = combine(ingredients, getStrSupplements());
        ingredients.addAll(getStrSauces());
        ingredients.addAll(getStrViandes());
        ingredients.addAll(getStrSupplements());

        String strIngredients = tailleTacos.toString();
        for (String ingredient : ingredients) {
            if (strIngredients != "")
                strIngredients += ", ";
            strIngredients += ingredient.toLowerCase();
        }

        return strIngredients;
    }

//    private String[] combine(String[] a, String[] b) {
//        int length = a.length + b.length;
//        String[] result = new String[length];
//        System.arraycopy(a, 0, result, 0, a.length);
//        System.arraycopy(b, 0, result, a.length, b.length);
//        return result;
//    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    /*public void empty() {
        this.setSauces(new ArrayList<Sauce>());
        this.setViandes(new ArrayList<Viande>());
        this.setSupplements(new ArrayList<Supplement>());
    }*/
}