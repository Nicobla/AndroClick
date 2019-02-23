package com.example.androclick;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);
    }


    public void moveBackToMyRecipes(View view) {
        finish();
    }

    public void moveToOptions(View view) {
        startActivity(new Intent(this, Options.class));
    }


    public void moveToAddIngredient(View view) {
        startActivity(new Intent(this, AddIngredient.class));
    }
}
