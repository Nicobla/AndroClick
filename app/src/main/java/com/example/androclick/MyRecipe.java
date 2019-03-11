package com.example.androclick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
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

public class MyRecipe extends AppCompatActivity {

    private Recette recette;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);

        recette = (Recette)getIntent().getSerializableExtra("recette");
        position = (int)getIntent().getSerializableExtra("position");

        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        mText.setText(recette.getNom());

        final TextView tvTailleTacos = (TextView) findViewById(R.id.taille_tacos);
        final ListView lvSauces = (ListView) findViewById(R.id.list_sauces);
        final ListView lvViandes = (ListView) findViewById(R.id.list_viandes);
        final ListView lvSupplements = (ListView) findViewById(R.id.list_supplements);

        tvTailleTacos.setText(recette.getStrTailleTacos());
        final ArrayList<String> listSauces = new ArrayList<String>(){};
        for (String sauce : recette.getStrSauces()) { listSauces.add(StringUtils.capitalize(sauce)); }
        if (listSauces.isEmpty()) listSauces.add("Aucune");

        final ArrayList<String> listViandes = new ArrayList<String>(){};
        for (String viande : recette.getStrViandes()) { listViandes.add(StringUtils.capitalize(viande)); }
        if (listViandes.isEmpty()) listViandes.add("Aucune");

        final ArrayList<String> listSupplements = new ArrayList<String>(){};
        for (String supplement : recette.getStrSupplements()) { listSupplements.add(StringUtils.capitalize(supplement)); }
        if (listSupplements.isEmpty()) listSupplements.add("Aucun");

        ArrayAdapter<String> adapterSauces = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSauces);
        lvSauces.setAdapter(adapterSauces);
        ArrayAdapter<String> adapterViandes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViandes);
        lvViandes.setAdapter(adapterViandes);
        ArrayAdapter<String> adapterSupplements = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSupplements);
        lvSupplements.setAdapter(adapterSupplements);

    }

    public void moveBackToMyRecipes(View view) {
        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        recette.setNom(mText.getText().toString());

        ((MyApplication) this.getApplicationContext()).setRecette(position, recette);
        Intent intent = new Intent();
        intent.putExtra("recette", recette);
        intent.putExtra("position", position);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void moveToEditRecipe(View view) {
        //TODO : plusieurs float buttons (edit, suppress->popup)
        Intent intent = new Intent(this, EditRecipe.class);
        intent.putExtra("recette", recette);
        intent.putExtra("position", position);
        startActivityForResult(intent, Activity.RESULT_OK);
    }

    @SuppressLint("NewApi")
    public void makeFavorite(View view) {
        Toast.makeText(getApplicationContext(), "TODO : gestion des favoris", Toast.LENGTH_SHORT).show();
        //TODO : gestion du clic sur le bouton favoris
        ImageButton mButton = findViewById(R.id.button_favorite);
        mButton.setBackgroundColor(getColor(R.color.colorFav));
    }

    /*@Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle b = data.getExtras();
            if (b != null) {
                recette = (Recette) b.getSerializable("recette");
                Toast.makeText(getApplicationContext(), "Receive: "+position + recette.getNom(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

}
