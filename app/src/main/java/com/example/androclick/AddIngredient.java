package com.example.androclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        final ListView listview = (ListView) findViewById(R.id.list_add_ingredient);
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList(
                "Grand tacos", "Tacos moyen", "Tacos simple",
                "Salade", "Tomates", "Oignons", "Frites"
        ));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // Lorsque l'on clique sur un ingrédient
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            //TODO : add ingrédient dans la liste de la recette
            finish();
            }
        });
    }

    public void moveBackToMyRecipe(View view) {
        finish();
    }

    public void searchIngredient(View view) {
        Toast.makeText(getApplicationContext(), "TODO : searchIngredient", Toast.LENGTH_LONG).show();
    }
}
