package com.example.androclick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class OTacos_Details extends AppCompatActivity {

    private O_Tacos_Serializable otacos;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otacos_details);

        otacos = (O_Tacos_Serializable) getIntent().getSerializableExtra("otacos");

        final TextView tvNomTacos = (TextView) findViewById(R.id.otacos_name);
        tvNomTacos.setText(otacos.getNom());

        final TextView tvAdresse1Tacos = (TextView) findViewById(R.id.adresse1_otacos);
        tvAdresse1Tacos.setText(otacos.getAdresse());

        final TextView tvAdresse2Tacos = (TextView) findViewById(R.id.adresse2_otacos);
        tvAdresse2Tacos.setText(otacos.getFullStrVille());

        ImageButton button_back = (ImageButton) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otacos.getId() != 0) {
                    //TODO : enregistrer l'id otacos en favoris
                    Toast.makeText(getApplicationContext(), "TODO : enregistrer " + otacos.getId() + " en favoris - " + otacos.isFavorite(), Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });


        ImageButton favButton = findViewById(R.id.button_favorite);
        if (otacos.isFavorite()) {
            favButton.setBackgroundColor(getColor(R.color.colorFav));
        } else {
            favButton.setBackgroundColor(getColor(R.color.colorNotFav));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @SuppressLint("NewApi")
    public void makeFavorite(View view) {
        ImageButton favButton = findViewById(R.id.button_favorite);
        if (otacos.isFavorite()) {
            favButton.setBackgroundColor(getColor(R.color.colorNotFav));
            otacos.setFavorite(false);
        } else {
            favButton.setBackgroundColor(getColor(R.color.colorFav));
            otacos.setFavorite(true);
        }
    }

}
