package com.example.androclick;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRecipes extends Fragment {

    ArrayList<Recette> listeRecettes;
    private RecyclerView rvRecettes;
    private RecyclerView.Adapter recettesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MyRecipes() {
        // Required empty public constructor
    }
    //public static MyRecipes newInstance() { return new MyRecipes(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_recipes_fragment, container, false);

        ImageButton button_menu = (ImageButton) view.findViewById(R.id.button_menu);
        button_menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "TODO : showOptionsMenu", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton button_searchRecipe = (ImageButton) view.findViewById(R.id.button_searchRecipe);
        button_searchRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "TODO : searchRecipe", Toast.LENGTH_LONG).show();
            }


        });

        rvRecettes = (RecyclerView)view.findViewById(R.id.list_myrecipes);
        displayListeRecettes();

        return view;
    }

    private void displayListeRecettes() {
        listeRecettes = new ArrayList<Recette>();
        //TODO : récupérer la liste des recettes depuis BDD
        listeRecettes.add(new Recette("Recette 1", "Tacos moyen", new String[]{"moutarde"}, new String[]{}));
        listeRecettes.add(new Recette("Bon tacos", "Grand tacos", new String[]{"mayonnaise"}, new String[]{"salade", "tomate"}));
        listeRecettes.add(new Recette("Recette 3", "Petit tacos", new String[]{"ketchup"}, new String[]{}));
        for (int i=4; i<=18; i++) {
            listeRecettes.add(new Recette("Recette "+i, "Tacos moyen",new String[]{},new String[]{}));
        }

        rvRecettes.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvRecettes.setLayoutManager(layoutManager);

        recettesAdapter = new RecettesAdapter(listeRecettes);
        rvRecettes.setAdapter(recettesAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
