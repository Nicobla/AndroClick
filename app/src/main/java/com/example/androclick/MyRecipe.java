package com.example.androclick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MyRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);

        final ListView listview = (ListView) findViewById(R.id.list_ingredients);
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList("Tacos moyen", "Frites", "Mayonnaise", "Oignons"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // Lorsque l'on clique sur un ingrédient
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            //TODO : gestion de la suppression de l'ingrédient (genre popup "Voulez-vous vraiment supprimer cet ingrédient ?")
            }
        });
    }

    public void moveBackToMyRecipes(View view) {
        finish();
    }

    public void moveToAddIngredient(View view) {
        startActivity(new Intent(this, AddIngredient.class));
    }
}
