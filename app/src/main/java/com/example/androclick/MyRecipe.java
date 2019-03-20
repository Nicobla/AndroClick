package com.example.androclick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class MyRecipe extends AppCompatActivity {

    private Recette recette;
    private int position;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);

        recette = (Recette) getIntent().getSerializableExtra("recette");
        if (recette != null)
            position = ((MyApplication) getApplication().getApplicationContext()).getPositionRecette(recette);
        if (position == -1 || recette == null) {
            Log.e("MyRecipe - OnCreate", "Erreur : Tentative d'ouverture d'une recette supprimée");
            finish();
        }

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

        for (String sauce : recette.getStrSauces()) {
            listSauces.add(StringUtils.capitalize(sauce));
        }
        if (listSauces.isEmpty()) listSauces.add("Aucune");

        for (String viande : recette.getStrViandes()) {
            listViandes.add(StringUtils.capitalize(viande));
        }
        if (listViandes.isEmpty()) listViandes.add("Aucune");

        for (String supplement : recette.getStrSupplements()) {
            listSupplements.add(StringUtils.capitalize(supplement));
        }
        if (listSupplements.isEmpty()) listSupplements.add("Aucun");

        ArrayAdapter<String> adapterSauces = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSauces);
        lvSauces.setAdapter(adapterSauces);
        ArrayAdapter<String> adapterViandes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listViandes);
        lvViandes.setAdapter(adapterViandes);
        ArrayAdapter<String> adapterSupplements = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSupplements);
        lvSupplements.setAdapter(adapterSupplements);

        final ImageButton button_back = (ImageButton) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton favButton = findViewById(R.id.button_favorite);
        if (recette.isFavorite()) {
            favButton.setBackgroundColor(getColor(R.color.colorFav));
        } else {
            favButton.setBackgroundColor(getColor(R.color.colorNotFav));
        }


        FloatingActionMenu fabEdit;
        FloatingActionButton subfabShare, subfabDelete, subfabEdit;

        fabEdit = (FloatingActionMenu) findViewById(R.id.button_open_edit);
//        fabEdit.setOnMenuButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "qdzqzdq", Toast.LENGTH_SHORT).show();
//            }
//        });
        subfabShare = (FloatingActionButton) findViewById(R.id.button_share);
        subfabDelete = (FloatingActionButton) findViewById(R.id.button_delete);
        subfabEdit = (FloatingActionButton) findViewById(R.id.button_edit);

        subfabShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share();
            }
        });
        subfabDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteRecipe();
            }
        });
        subfabEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editRecipe();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TextInputEditText mText = findViewById(R.id.myrecipe_name_input);
        recette.setNom(mText.getText().toString());

        ((MyApplication) getApplicationContext()).setRecette(position, recette);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((MyApplication)getApplicationContext()).mustRefreshRecipes = true;
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

    public void share() {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        myIntent.putExtra(Intent.EXTRA_SUBJECT, "Ma recette de tacos : " + recette.getNom());
        myIntent.putExtra(Intent.EXTRA_TEXT, "Ingrédients de la recette :\n" + recette.getIngredients());
        startActivity(Intent.createChooser(myIntent, "Partager avec"));
    }

    public void deleteRecipe() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_delete, null);

        // Crée la fenêtre de pop-up
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(findViewById(R.id.constraintLayout), Gravity.CENTER, 0, 0);

        Button buttonYes = (Button) popupView.findViewById(R.id.button_yes);
        Button buttonNo = (Button) popupView.findViewById(R.id.button_no);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                // Suppression
                ((MyApplication) getApplicationContext()).deleteRecetteAt(position);
                finish();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void editRecipe() {
        Intent intent = new Intent(this, EditRecipe.class);
        intent.putExtra("recette", recette);
        intent.putExtra("position", position);
        startActivityForResult(intent, Activity.RESULT_OK);
    }

    @SuppressLint("NewApi")
    public void makeFavorite(View view) {
        ImageButton favButton = findViewById(R.id.button_favorite);
        if (recette.isFavorite()) {
            favButton.setBackgroundColor(getColor(R.color.colorNotFav));
            recette.setFavorite(false);
        } else {
            favButton.setBackgroundColor(getColor(R.color.colorFav));
            recette.setFavorite(true);
        }
    }

//    @Override
//    public void onActivityResult (int requestCode, int resultCode, Intent data) {
//        Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            Bundle b = data.getExtras();
//            if (b != null) {
//                recette = (Recette) b.getSerializable("recette");
//                Toast.makeText(getApplicationContext(), "Receive: "+position + recette.getNom(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
