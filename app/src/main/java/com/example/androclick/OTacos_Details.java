package com.example.androclick;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class OTacos_Details extends AppCompatActivity {

    private O_Tacos_Serializable otacos;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otacos_details);

        otacos = (O_Tacos_Serializable) getIntent().getSerializableExtra("otacos");
        GeoPoint2 userPosition = (GeoPoint2) getIntent().getSerializableExtra("userPosition");

        final TextView tvDistance = (TextView) findViewById(R.id.distance);
        if (userPosition != null) {
            double distance = otacos.distance(userPosition);
            distance = (double) Math.round(distance/1000 * 10) / 10;
            String distanceStr = distance+" km";

            tvDistance.setText(distanceStr);
        } else {
            tvDistance.setText(getString(R.string.unknown_location));
        }

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
                int id = otacos.getId();
                if (id != 0) {
                    ((MyApplication) getApplicationContext()).setOTacosFav(id, otacos.isFavorite());
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
