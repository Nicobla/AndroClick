package com.example.androclick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MyRecipe extends AppCompatActivity {

    private String name;//à supprimer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);

        //TODO : modifier affichage (considérer tacos comme objet)
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

        name = "(nom de la recette)"; //TODO : get name (get infos de la recette)
        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        mText.setText(name);
    }

    public void moveBackToMyRecipes(View view) {
        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        Toast.makeText(getApplicationContext(), "Nom de la recette : "+mText.getText(), Toast.LENGTH_SHORT).show();
        //TODO : sauvegarder le nom de la recette
        finish();
    }

    public void moveToAddIngredient(View view) {
        startActivity(new Intent(this, AddIngredient.class));
    }

    @SuppressLint("NewApi")
    public void makeFavorite(View view) {
        Toast.makeText(getApplicationContext(), "TODO : gestion des favoris", Toast.LENGTH_SHORT).show();
        //TODO : gestion du clic sur le bouton favoris
        ImageButton mButton = findViewById(R.id.button_favorite);
        mButton.setBackgroundColor(getColor(R.color.colorFav));
    }

}
