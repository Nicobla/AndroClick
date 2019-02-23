package com.example.androclick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final ListView listview = (ListView) findViewById(R.id.list_options);
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList("À propos", "Mon compte"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // Lorsque l'on clique sur une option
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString()+"Oui monsieur", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString()+"Oui madame", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    public void moveToMyrecipes(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}