package com.example.androclick;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyApplication extends Application {

    private LatLng userPosition;// = new LatLng(45.9208490, 6.1415);

    private ArrayList<O_Tacos> listeOTacos = new ArrayList<>();
    private ArrayList<Recette> listeRecettes = new ArrayList<>();
    private ArrayList<Sauce> listeSauces = new ArrayList<>();
    private ArrayList<Viande> listeViandes = new ArrayList<>();
    private ArrayList<Supplement> listeSupplements = new ArrayList<>();


    public LatLng getUserPosition() {
        return userPosition;
    }
    public void setUserPosition(LatLng userPosition) {
        this.userPosition = userPosition;
    }


    public ArrayList<O_Tacos> getListeOTacos() {
        return listeOTacos;
    }
    public void setListeOTacos(ArrayList<O_Tacos> listeOTacos) {
        this.listeOTacos = listeOTacos;
    }
    public void setOTacosFav(int id, boolean isFavorite) {
        int idx = 0;
        for (O_Tacos o_tacos : listeOTacos) {
            if (o_tacos.getId() == id) {
                o_tacos.setFavorite(isFavorite);
                listeOTacos.set(idx, o_tacos);
                Log.e("MyApp - setOTacosFav", "Set fav ! ("+o_tacos.getNom()+")");
                return;
            }
            idx++;
        }
        Log.e("MyApp - setOTacosFav", "L'id ne correspond à aucun OTacos");
    }


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

    public ArrayList<Sauce> getListeSauces() {
        return listeSauces;
    }
    public void setListeSauces(ArrayList<Sauce> listeSauces) {
        this.listeSauces = listeSauces;
    }

    public ArrayList<Viande> getListeViandes() {
        return listeViandes;
    }
    public void setListeViandes(ArrayList<Viande> listeViandes) {
        this.listeViandes = listeViandes;
    }

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
        Log.e("MyApp - onCreate", "Ouverture de l'app");
        setData();

    }

    private void setData() {
        // Récupère les données dans l'application (OTacos, recettes, sauces, viandes, suppléments)
        final ArrayList<Sauce> lsa = getAllSauces();
        final ArrayList<Viande> lv = getAllViandes();
        final ArrayList<Supplement> lsu = getAllSupplements();
        setListeOTacos(getAllOTacos());
        setListeSauces(lsa);
        setListeViandes(lv);
        setListeSupplements(lsu);
        setListeRecettes(getAllRecipes());
//        new Handler().postDelayed(new Runnable(){
//            public void run(){
//                Log.e("MainActivity - setData", "re-get 1");
//                setListeRecettes(getAllRecipes());
//                new Handler().postDelayed(new Runnable(){
//                    public void run(){
//                        Log.e("MainActivity - setData", "re-get 2");
//                        setListeRecettes(getAllRecipes(lsa, lv, lsu));
//                    }
//                }, 3000);
//            }
//        }, 3000);

    }

    public void writeArrayInFile(ArrayList arrayList, String filename) {
        try {
            String jsonArray = new Gson().toJson(arrayList);
            FileOutputStream fileOutputStream = openFileOutput(filename, MODE_PRIVATE);
            fileOutputStream.write(jsonArray.getBytes());
            fileOutputStream.close();

            Log.d("MyApp-writeArrayInFile", filename+" saved");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonStringFromFile(String filename) {
        try {
            FileInputStream fileInputStream = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
            return stringBuffer.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<O_Tacos> getAllOTacosFromFile() {
        ArrayList<O_Tacos> listeOTacos = new ArrayList<>();
        //Read JSON string
        String jsonString = getJsonStringFromFile("otacos.json");
        if (jsonString == null) {
            Log.e("MyApp - readOTacos", "otacos.json : jsonString is null");
            return null;
        } else {
            //Convert JSON string
            ArrayList listOTacos = new Gson().fromJson(jsonString, ArrayList.class);

            for (Object object : listOTacos) {
                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) object;

                double idTemp = (double)t.get("id");
                int id = (int) Math.round(idTemp);
                String nom = (String)t.get("nom");

                LinkedTreeMap<Object,Object> tLocation = (LinkedTreeMap) t.get("location");
                GeoPoint location = new GeoPoint((double)tLocation.get("latitude"), (double)tLocation.get("longitude"));

                String adresse = (String)t.get("adresse");
                String cp = (String)t.get("cp");
                String ville = (String)t.get("ville");
                boolean isFavorite = (boolean)t.get("isFavorite");

                O_Tacos otacos = new O_Tacos(id, nom, location, adresse, cp, ville, isFavorite);
                listeOTacos.add(otacos);
            }
        }
        return listeOTacos;
    }
    public ArrayList<Sauce> getAllSaucesFromFile() {
        ArrayList<Sauce> listeSauces = new ArrayList<>();
        //Read JSON string
        String jsonString = getJsonStringFromFile("sauces.json");
        if (jsonString == null) {
            Log.e("MyApp - readSauces", "sauces.json : jsonString is null");
            return null;
        } else {
            //Convert JSON string
            ArrayList list = new Gson().fromJson(jsonString, ArrayList.class);

            for (Object object : list) {
                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) object;

                String nom = (String)t.get("nom");

                Sauce sauce = new Sauce(nom);
                listeSauces.add(sauce);
            }
        }
        return listeSauces;
    }
    public ArrayList<Viande> getAllViandesFromFile() {
        ArrayList<Viande> listeViandes = new ArrayList<>();
        //Read JSON string
        String jsonString = getJsonStringFromFile("viandes.json");
        if (jsonString == null) {
            Log.e("MyApp - readViandes", "viandes.json : jsonString is null");
            return null;
        } else {
            //Convert JSON string
            ArrayList list = new Gson().fromJson(jsonString, ArrayList.class);

            for (Object object : list) {
                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) object;

                String nom = (String)t.get("nom");

                Viande viande = new Viande(nom);
                listeViandes.add(viande);
            }
        }
        return listeViandes;
    }
    public ArrayList<Supplement> getAllSupplementsFromFile() {
        ArrayList<Supplement> listeSupplements = new ArrayList<>();
        //Read JSON string
        String jsonString = getJsonStringFromFile("supplements.json");
        if (jsonString == null) {
            Log.e("MyApp - readSupplements", "supplements.json : jsonString is null");
            return null;
        } else {
            //Convert JSON string
            ArrayList list = new Gson().fromJson(jsonString, ArrayList.class);

            for (Object object : list) {
                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) object;

                String nom = (String)t.get("nom");

                Supplement supplement = new Supplement(nom);
                listeSupplements.add(supplement);
            }
        }
        return listeSupplements;
    }
    public ArrayList<Recette> getAllRecipesFromFile() {
        ArrayList<Recette> listeRecettes = new ArrayList<>();
        //Read JSON string
        String jsonString = getJsonStringFromFile("recettes.json");
        if (jsonString == null) {
            Log.e("MyApp - readRecipes", "recettes.json : jsonString is null");
            return null;
        } else {
            //Convert JSON string
            ArrayList list = new Gson().fromJson(jsonString, ArrayList.class);

            for (Object object : list) {
                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) object;

                String nom = (String)t.get("nom");
                String strTailleTacos = (String)t.get("tailleTacos");
                Recette.TailleTacos tailleTacos = null;
                switch (strTailleTacos) {
                    case "M":
                        tailleTacos = Recette.TailleTacos.M;
                        break;
                    case "L":
                        tailleTacos = Recette.TailleTacos.L;
                        break;
                    case "XL":
                        tailleTacos = Recette.TailleTacos.XL;
                        break;
                    case "XXL":
                        tailleTacos = Recette.TailleTacos.XXL;
                        break;
                }
                ArrayList<Sauce> sauces = new ArrayList<>();
                ArrayList arraySauces = (ArrayList) t.get("sauces");
                for (Object objSauce : arraySauces) {
                    LinkedTreeMap<Object,Object> tSauce = (LinkedTreeMap) objSauce;
                    String nomSauce = (String)tSauce.get("nom");
                    sauces.add(new Sauce(nomSauce));
                }
                ArrayList<Viande> viandes = new ArrayList<>();
                ArrayList arrayViandes = (ArrayList) t.get("viandes");
                for (Object objViande : arrayViandes) {
                    LinkedTreeMap<Object,Object> tViande = (LinkedTreeMap) objViande;
                    String nomViande = (String)tViande.get("nom");
                    viandes.add(new Viande(nomViande));
                }
                ArrayList<Supplement> supplements = new ArrayList<>();
                ArrayList arraySupplements = (ArrayList) t.get("supplements");
                for (Object objSupplement : arraySupplements) {
                    LinkedTreeMap<Object,Object> tSupplement = (LinkedTreeMap) objSupplement;
                    String nomSupplement = (String)tSupplement.get("nom");
                    supplements.add(new Supplement(nomSupplement));
                }
                boolean isFavorite = (boolean)t.get("isFavorite");
                Recette recette = new Recette(nom, tailleTacos, sauces, viandes, supplements, isFavorite);
                listeRecettes.add(recette);
            }
        }
        return listeRecettes;
    }


    public ArrayList<O_Tacos> getAllOTacos() {
        ArrayList<O_Tacos> listeOTacos = getAllOTacosFromFile();
        if (listeOTacos == null) {
            Log.e("MyApp - getAllOTacos", "liste null --> get from database");
            return Data.getAllOTacosFromDb();
        }
        else {
            Log.d("MyApp - getAllOTacos", "lecture de la liste OK");
            return listeOTacos;
        }
    }
    public ArrayList<Sauce> getAllSauces() {
        ArrayList<Sauce> listeSauces = getAllSaucesFromFile();
        if (listeSauces == null) {
            Log.e("MyApp - getAllSauces", "liste null --> get from database");
            return Data.getAllSaucesFromDb();
        }
        else {
            Log.d("MyApp - getAllSauces", "lecture de la liste OK");
            return listeSauces;
        }
    }
    public ArrayList<Viande> getAllViandes() {
        ArrayList<Viande> listeViandes = getAllViandesFromFile();
        if (listeViandes == null) {
            Log.e("MyApp - getAllViandes", "liste null --> get from database");
            return Data.getAllViandesFromDb();
        }
        else {
            Log.d("MyApp - getAllViandes", "lecture de la liste OK");
            return listeViandes;
        }
    }
    public  ArrayList<Supplement> getAllSupplements() {
        ArrayList<Supplement> listeSupplements = getAllSupplementsFromFile();
        if (listeSupplements == null) {
            Log.e("MyApp-getAllSupplements", "liste null --> get from database");
            return Data.getAllSupplementsFromDb();
        }
        else {
            Log.d("MyApp-getAllSupplements", "lecture de la liste OK");
            return listeSupplements;
        }
    }
    public ArrayList<Recette> getAllRecipes() {
        ArrayList<Recette> listeRecettes = getAllRecipesFromFile();
        if (listeRecettes == null) {
            Log.e("MyApp - getAllRecipes", "liste null --> get from database");
            return Data.getAllRecipesFromDb(getAllSauces(), getAllViandes(), getAllSupplements());
        }
        else {
            Log.d("MyApp - getAllRecipes", "lecture de la liste OK");
            return listeRecettes;
        }
    }
//    public ArrayList<Recette> getAllRecipes(ArrayList<Sauce> listeSauces, ArrayList<Viande> listeViandes, ArrayList<Supplement> listeSupplements) {
//        ArrayList<Recette> listeRecettes = Data.getAllRecipesFromDb(listeSauces, listeViandes, listeSupplements);
//        if (listeRecettes != null) {
//            Log.e("Data - getAllRecipes", "size="+listeRecettes.size());
//        }
//        return listeRecettes;
//    }

    public int getNbRecettes() {
        if (this.listeRecettes == null)
            return 0;
        else
            return this.listeRecettes.size();
    }
}