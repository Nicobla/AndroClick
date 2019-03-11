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
}