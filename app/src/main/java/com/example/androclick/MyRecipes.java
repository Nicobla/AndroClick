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

public class MyRecipes extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

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

        final ListView listview = (ListView) findViewById(R.id.list_myrecipes);
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList("Recette 1", "Recette 2"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // Lorsque l'on clique sur une recette
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            moveToMyRecipe(position);
            }
        });
    }

    private void moveToMyRecipe(int id) {
        startActivity(new Intent(this, MyRecipe.class));
    }

    public void showOptionsMenu(View view) {
        Toast.makeText(getApplicationContext(), "TODO : showOptionsMenu", Toast.LENGTH_LONG).show();
    }
    public void searchRecipe(View view) {
        Toast.makeText(getApplicationContext(), "TODO : searchRecipe", Toast.LENGTH_LONG).show();
    }

    private void moveToActivity(View view, Class classe) {
        startActivity(new Intent(this, classe));
    }
}
