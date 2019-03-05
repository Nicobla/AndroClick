package com.example.androclick;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Tacos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacos);
    }


    public void moveBackTacos(View view) {}

    public void next(View view) {
        ImageButton mButtonBack = findViewById(R.id.button_back);
        mButtonBack.setVisibility((int)0);

        ConstraintLayout constraintLayout = findViewById(R.id.toolbar_layout);
        //TextView mTitle = findViewById(R.id.title_tacos);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.title_tacos,ConstraintSet.LEFT,R.id.button_back,ConstraintSet.RIGHT,240); //marche pas
        constraintSet.connect(R.id.title_tacos,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,24);
        constraintSet.applyTo(constraintLayout);
    }

}
