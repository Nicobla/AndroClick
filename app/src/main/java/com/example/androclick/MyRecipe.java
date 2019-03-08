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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MyRecipe extends AppCompatActivity {

    private Recette recette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);

        //TODO : get recette from database
        recette = new Recette("Exemple recette", "Tacos moyen",new String[]{"Ketchup", "Mayonnaise"}, new String[]{"Oignons", "Salade"});

        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        mText.setText(recette.getNom());

        final TextView tvTailleTacos = (TextView) findViewById(R.id.taille_tacos);
        final ListView lvSauces = (ListView) findViewById(R.id.list_sauces);
        final ListView lvSupplements = (ListView) findViewById(R.id.list_supplements);

        tvTailleTacos.setText(recette.getTailleTacos());
        final ArrayList<String> listSauces = new ArrayList<String>(){};
        listSauces.addAll(Arrays.asList(recette.getSauces()));
        final ArrayList<String> listSupplements = new ArrayList<String>(){};
        listSupplements.addAll(Arrays.asList(recette.getSupplements()));

        ArrayAdapter<String> adapterSauces = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSauces);
        lvSauces.setAdapter(adapterSauces);
        ArrayAdapter<String> adapterSupplements = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSupplements);
        lvSupplements.setAdapter(adapterSupplements);

    }

    public void moveBackToMyRecipes(View view) {
        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        Toast.makeText(getApplicationContext(), mText.getText(), Toast.LENGTH_SHORT).show();
        //TODO : sauvegarder la recette
        finish();
    }

    public void moveToEditRecipe(View view) {
        startActivity(new Intent(this, EditRecipe.class));
    }

    @SuppressLint("NewApi")
    public void makeFavorite(View view) {
        Toast.makeText(getApplicationContext(), "TODO : gestion des favoris", Toast.LENGTH_SHORT).show();
        //TODO : gestion du clic sur le bouton favoris
        ImageButton mButton = findViewById(R.id.button_favorite);
        mButton.setBackgroundColor(getColor(R.color.colorFav));
    }

}
