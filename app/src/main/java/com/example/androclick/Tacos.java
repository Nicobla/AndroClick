package com.example.androclick;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Tacos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacos);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_tacos);
        bottomNavigationView.setOnNavigationItemSelectedListener
        (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recipes:
                        Toast.makeText(getApplicationContext(), "Recettes", Toast.LENGTH_LONG).show();
                        moveToActivity(bottomNavigationView, MyRecipes.class);
                        break;
                    case R.id.navigation_otacos:
                        Toast.makeText(getApplicationContext(), "OTacos", Toast.LENGTH_LONG).show();
                        //moveToActivity(bottomNavigationView, OTacos.class);
                        break;
                    case R.id.navigation_tacos:
                        Toast.makeText(getApplicationContext(), "Tacos", Toast.LENGTH_LONG).show();
                        moveToActivity(bottomNavigationView, Tacos.class);
                        break;
                }
                return true;
            }
        });
    }

    private void moveToActivity(View view, Class classe) {
        startActivity(new Intent(this, classe));
    }
}
