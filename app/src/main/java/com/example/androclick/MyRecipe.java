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
import android.widget.Button;
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
        position = ((MyApplication) getApplication().getApplicationContext()).getPositionRecette(recette);

        //position = (int)getIntent().getSerializableExtra("position");
        //recette = ((MyApplication) getApplication().getApplicationContext()).getRecetteAtPos(position);

        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        mText.setText(recette.getNom());

        final TextView tvTailleTacos = (TextView) findViewById(R.id.taille_tacos);
        final ListView lvSauces = (ListView) findViewById(R.id.list_sauces);
        final ListView lvViandes = (ListView) findViewById(R.id.list_viandes);
        final ListView lvSupplements = (ListView) findViewById(R.id.list_supplements);

        tvTailleTacos.setText(recette.getStrTailleTacos());

        final ArrayList<String> listSauces = new ArrayList<>();
        final ArrayList<String> listViandes = new ArrayList<>();
        final ArrayList<String> listSupplements = new ArrayList<>();

        for (String sauce : recette.getStrSauces()) { listSauces.add(StringUtils.capitalize(sauce)); }
        if (listSauces.isEmpty()) listSauces.add("Aucune");

        for (String viande : recette.getStrViandes()) { listViandes.add(StringUtils.capitalize(viande)); }
        if (listViandes.isEmpty()) listViandes.add("Aucune");

        for (String supplement : recette.getStrSupplements()) { listSupplements.add(StringUtils.capitalize(supplement)); }
        if (listSupplements.isEmpty()) listSupplements.add("Aucun");

        ArrayAdapter<String> adapterSauces = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSauces);
        lvSauces.setAdapter(adapterSauces);
        ArrayAdapter<String> adapterViandes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listViandes);
        lvViandes.setAdapter(adapterViandes);
        ArrayAdapter<String> adapterSupplements = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSupplements);
        lvSupplements.setAdapter(adapterSupplements);

        ImageButton button_back = (ImageButton) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
                recette.setNom(mText.getText().toString());

                /*Intent intent = new Intent();
                intent.putExtra("recette", recette);
                intent.putExtra("position", position);
                setResult(Activity.RESULT_OK, intent);*/
                ((MyApplication) getApplicationContext()).setRecette(position, recette);

                //((MyRecipes)getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().indexOf(new MyRecipes()))).reset();
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.myRecipes);
                Log.e("DEBUG", "frag null - "+(currentFragment==null));



                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Recette recette = ((MyApplication) getApplication().getApplicationContext()).getRecetteAtPos(position);
        finish();
        startActivity(getIntent().putExtra("recette", recette));
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
