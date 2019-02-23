package com.example.androclick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
    }

    public void moveBackToMyRecipe(View view) {
        finish();
    }

    public void searchIngredient(View view) {
        Toast.makeText(getApplicationContext(), "TODO : searchIngredient", Toast.LENGTH_LONG).show();
    }
}
