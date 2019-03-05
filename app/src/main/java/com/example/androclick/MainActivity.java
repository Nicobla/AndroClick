package com.example.androclick;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.constraintLayout, MyRecipes.newInstance())
                    .commitNow();
        }
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
        (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recipes:
                    Toast.makeText(getApplicationContext(), "Recettes", Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.constraintLayout, MyRecipes.newInstance())
                            .commitNow();
                    break;
                case R.id.navigation_otacos:
                    Toast.makeText(getApplicationContext(), "OTacos", Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.constraintLayout, OTacos.newInstance())
                            .commitNow();
                    break;
                case R.id.navigation_tacos:
                    Toast.makeText(getApplicationContext(), "Tacos", Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.constraintLayout, MakeTacos.newInstance())
                            .commitNow();
                    break;
            }
            return true;
            }
        });
    }
}
