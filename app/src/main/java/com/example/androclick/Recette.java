package com.example.androclick;


public class Recette {
    String nom;
    String tailleTacos;
    String[] sauces;
    String[] supplements;

    public Recette(String nom, String tailleTacos, String[]sauces, String[]supplements) {
        super();
        this.nom = nom;
        this.tailleTacos = tailleTacos;
        this.sauces = sauces;
        this.supplements = supplements;
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

    public String[] getSauces() {
        return sauces;
    }
    public void setSauces(String[] sauces) {
        this.sauces = sauces;
    }
    public String[] getSupplements() {
        return supplements;
    }
    public void setSupplements(String[] supplements) {
        this.supplements = supplements;
    }

    public String[] getIngredients() {
        String[] ingredients = new String[]{tailleTacos};
        ingredients = combine(ingredients, sauces);
        ingredients = combine(ingredients, supplements);
        return ingredients;
    }

    private static String[] combine(String[] a, String[] b){
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}