package com.example.androclick;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class MyApplication extends Application {

    private ArrayList<O_Tacos> listeOTacos;

    public ArrayList<O_Tacos> getListeOTacos() {
        return listeOTacos;
    }

    public void setListeOTacos(ArrayList<O_Tacos> listeOTacos) {
        this.listeOTacos = listeOTacos;
    }


    private ArrayList<Recette> listeRecettes;

    public ArrayList<Recette> getListeRecettes() {
        return listeRecettes;
    }

    public void setListeRecettes(ArrayList<Recette> listeRecettes) {
        this.listeRecettes = listeRecettes;
    }

    public void addToListeRecettes(Recette recette) {
        //ArrayList<Recette> listRecettes = getListeRecettes();
        //listRecettes.add(recette);
        //this.setListeRecettes(listRecettes);
        this.listeRecettes.add(recette);
    }

    public void setRecette(int position, Recette recette) {
        //ArrayList<Recette> listRecettes = getListeRecettes();
        //listRecettes.set(position, recette);
        //this.setListeRecettes(listRecettes);
        this.listeRecettes.set(position, recette);
    }

    public Recette getRecetteAtPos(int position) {
        return this.getListeRecettes().get(position);
    }

    public int getPositionRecette(Recette recette) {
        int idx = 0;
        for (Recette recette1 : getListeRecettes()) {
            if (recette1.getNom().equals(recette.getNom())) {
                return idx;
            }
            idx++;
        }
        return -1;
    }

    public void deleteRecetteAt(int position) {
        this.listeRecettes.remove(position);
    }


    private ArrayList<Sauce> listeSauces;

    public ArrayList<Sauce> getListeSauces() {
        return listeSauces;
    }

    public void setListeSauces(ArrayList<Sauce> listeSauces) {
        this.listeSauces = listeSauces;
    }


    private ArrayList<Viande> listeViandes;

    public ArrayList<Viande> getListeViandes() {
        return listeViandes;
    }

    public void setListeViandes(ArrayList<Viande> listeViandes) {
        this.listeViandes = listeViandes;
    }


    private ArrayList<Supplement> listeSupplements;

    public ArrayList<Supplement> getListeSupplements() {
        return listeSupplements;
    }

    public void setListeSupplements(ArrayList<Supplement> listeSupplements) {
        this.listeSupplements = listeSupplements;
    }


    public void uncheckAllIngredients() {
        ArrayList<Sauce> listeSauces = getListeSauces();
        ArrayList<Viande> listeViandes = getListeViandes();
        ArrayList<Supplement> listeSupplements = getListeSupplements();

        for (int i = 0; i < listeSauces.size(); i++) listeSauces.get(i).setSelected(false);
        for (int i = 0; i < listeViandes.size(); i++) listeViandes.get(i).setSelected(false);
        for (int i = 0; i < listeSupplements.size(); i++)
            listeSupplements.get(i).setSelected(false);

        this.setListeSauces(listeSauces);
        this.setListeViandes(listeViandes);
        this.setListeSupplements(listeSupplements);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DEBUG", "Ouverture de l'app");
    }
}