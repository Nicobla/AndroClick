package com.example.androclick;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        View view = bottomNavigationView.findViewById(R.id.navigation_recipes);
        switch (item.getItemId()) {
            case R.id.nav_myrecipes:
                view.performClick();
                break;
            case R.id.nav_otacos:
                view = bottomNavigationView.findViewById(R.id.navigation_otacos);
                view.performClick();
                break;
            case R.id.nav_tacos:
                view = bottomNavigationView.findViewById(R.id.navigation_tacos);
                view.performClick();
                break;
            case R.id.nav_account:
                view.performClick();
                //navigationView.setCheckedItem(R.id.nav_account);
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout,
                        new MyAccount()).commit();
                break;
            case R.id.nav_settings:
                view.performClick();
                //navigationView.setCheckedItem(R.id.nav_settings);
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout,
                        new Settings()).commit();
                break;
            case R.id.nav_about:
                view.performClick();
                //navigationView.setCheckedItem(R.id.nav_about);
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayout,
                        new About()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("MainActivity - onCreate", "Démarrage de l'app");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_myrecipes);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.constraintLayout, new MyRecipes()) // premier onglet ouvert (défaut: MyRecipes())
                    .commitNow();
        }
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
        (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recipes:
                        navigationView.setCheckedItem(R.id.nav_myrecipes);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.constraintLayout, new MyRecipes())
                                .commitNow();
                        break;
                    case R.id.navigation_otacos:
                        navigationView.setCheckedItem(R.id.nav_otacos);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.constraintLayout, new OTacos())
                                .commitNow();
                        break;
                    case R.id.navigation_tacos:
                        navigationView.setCheckedItem(R.id.nav_tacos);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.constraintLayout, new MakeTacos())
                                .commitNow();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity - onStop", "Fermeture de mainActivity, enregistrement des données");
        ((MyApplication) getApplicationContext()).writeData();
    }

}
