package com.example.androclick;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    private ArrayList<Recette> listeRecettes;

    public ArrayList<Recette> getListeRecettes() {
        return listeRecettes;
    }
    public void setListeRecettes(ArrayList<Recette> listeRecettes) {
        this.listeRecettes = listeRecettes;
    }

    public void addToListeRecettes(Recette recette) {
        ArrayList<Recette> listRecettes = getListeRecettes();
        listRecettes.add(recette);
        this.setListeRecettes(listRecettes);
    }
    public void setRecette(int position, Recette recette) {
        ArrayList<Recette> listRecettes = getListeRecettes();
        listRecettes.set(position, recette);
        this.setListeRecettes(listRecettes);
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

}