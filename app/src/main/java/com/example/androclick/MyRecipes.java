package com.example.androclick;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MyRecipes extends Fragment {

    private MyRecipesViewModel mViewModel;

    public static MyRecipes newInstance() {
        return new MyRecipes();
    }

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

        final ListView listview = (ListView) view.findViewById(R.id.list_myrecipes);
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList("Recette 1", "Recette 2"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        // Lorsque l'on clique sur une recette
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                moveToMyRecipe(position);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyRecipesViewModel.class);
        // TODO: Use the ViewModel
    }

    private void moveToMyRecipe(int id) {
        startActivity(new Intent(getActivity(), MyRecipe.class));
    }

}
