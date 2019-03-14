package com.example.androclick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

class GeoPoint2 implements Serializable {
    private double latitude;
    private double longitude;

    public GeoPoint2(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPoint2(GeoPoint geoPoint) {
        this(geoPoint.getLatitude(), geoPoint.getLongitude());
    }
}

class O_Tacos_Serializable implements Serializable {
    private int id;

    private String nom;
    private GeoPoint2 location;
    private String adresse;
    private String cp;
    private String ville;

    private boolean isFavorite;


    public O_Tacos_Serializable(int id, String nom, GeoPoint location, String adresse, String cp, String ville, boolean isFavorite) {
        this(nom, location, adresse, cp, ville, isFavorite);
        this.id = id;
    }

    public O_Tacos_Serializable(String nom, GeoPoint location, String adresse, String cp, String ville, boolean isFavorite) {
        this.nom = nom;
        this.location = new GeoPoint2(location);
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.isFavorite = isFavorite;
    }

    public O_Tacos_Serializable(String nom, GeoPoint location, String adresse, String cp, String ville) {
        this(nom, location, adresse, cp, ville, false);
    }

    public O_Tacos_Serializable(O_Tacos o_tacos) {
        this(o_tacos.getId(), o_tacos.getNom(), o_tacos.getLocation(), o_tacos.getAdresse(), o_tacos.getCp(), o_tacos.getVille(), o_tacos.isFavorite());
    }

    public O_Tacos_Serializable(String nom, GeoPoint location) {
        this(nom, location, "", "", "");
    }
    /*public O_Tacos(String nom, double latitude, double longitude) {
        this(nom, new GeoPoint(latitude, longitude));
    }*/

    public O_Tacos_Serializable() {
        this("O'Tacos sans nom", new GeoPoint(0, 0), "", "", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public GeoPoint2 getLocation() {
        return location;
    }

    public void setLocation(GeoPoint2 location) {
        this.location = location;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getStrFullAdresse() {
        return this.getAdresse() + ", " + this.getCp() + ", " + this.getVille();
    }

    public String getFullStrVille() {
        return this.getCp() + ", " + this.getVille();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

class O_Tacos implements Serializable { //Utiliser cette classe pour instancier des restaurants
    private int id;

    private String nom;
    private GeoPoint location;
    private String adresse;
    private String cp;
    private String ville;

    private boolean isFavorite;

    public O_Tacos(int id, String nom, GeoPoint location, String adresse, String cp, String ville, boolean isFavorite) {
        this(nom, location, adresse, cp, ville, isFavorite);
        this.id = id;
    }

    public O_Tacos(String nom, GeoPoint location, String adresse, String cp, String ville, boolean isFavorite) {
        this.nom = nom;
        this.location = location;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.isFavorite = isFavorite;
    }

    public O_Tacos(String nom, GeoPoint location, String adresse, String cp, String ville) {
        this(nom, location, adresse, cp, ville, false);
    }

    public O_Tacos(String nom, GeoPoint location) {
        this(nom, location, "", "", "");
    }
    /*public O_Tacos(String nom, double latitude, double longitude) {
        this(nom, new GeoPoint(latitude, longitude));
    }*/

    public O_Tacos() {
        this("O'Tacos sans nom", new GeoPoint(0, 0), "", "", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getStrFullAdresse() {
        return this.getAdresse() + ", " + this.getCp() + " " + this.getVille();
    }

    public String getFullStrVille() {
        return this.getCp() + ", " + this.getVille();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

public class OTacos extends Fragment {

    public OTacos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otacos_fragment, container, false); //TODO : layout otacos avec tabs

        final ImageButton button_menu = (ImageButton) view.findViewById(R.id.button_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.constraintLayout2, new ListOTacos()) // premier onglet ouvert (défaut: ListOTacos())
                    .commitNow();
        }

        final BottomNavigationView topNavigationView = (BottomNavigationView) view.findViewById(R.id.navigationViewOTacos);

        topNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_list_otacos:
                                getChildFragmentManager().beginTransaction()
                                        .replace(R.id.constraintLayout2, new ListOTacos())
                                        .commitNow();
                                break;
                            case R.id.navigation_map_otacos:
                                getChildFragmentManager().beginTransaction()
                                        .replace(R.id.constraintLayout2, new Map())
                                        .commitNow();
                                break;
                        }
                        return true;
                    }
                });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
