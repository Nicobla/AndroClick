package com.example.androclick;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecipes extends Fragment {

    private boolean searching = false;

    private ArrayList<Recette> listeRecettes = new ArrayList<>();
    private RecyclerView rvRecettes;
    private RecyclerView.Adapter recettesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MyRecipes() {
        // Required empty public constructor
    }

    public void reload() {
        recettesAdapter.notifyDataSetChanged();
//        if (getFragmentManager() != null) {
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.detach(this).attach(this).commit();
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_recipes_fragment, container, false);

        this.listeRecettes = ((MyApplication) this.getActivity().getApplicationContext()).getListeRecettes();
//        if (this.listeRecettes != null)
//            Log.e("MyRecipes - onCreate", "nbRecettes="+this.listeRecettes.size());
//        else
//            Log.e("MyRecipes - onCreate", "listeRecettes null");
        final EditText text_search = (EditText) view.findViewById(R.id.text_search);
        final TextView title_myrecipes = (TextView) view.findViewById(R.id.title_myrecipes);

        final ImageButton button_menu = (ImageButton) view.findViewById(R.id.button_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        final ImageButton button_searchRecipe = (ImageButton) view.findViewById(R.id.button_searchRecipe);
        button_searchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searching = !searching;
                View view = getActivity().getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (searching) { // Affiche la barre de recherche
                    button_menu.setVisibility(View.INVISIBLE);
                    title_myrecipes.setVisibility(View.INVISIBLE);
                    text_search.setVisibility(View.VISIBLE);
                    button_searchRecipe.setImageResource(R.drawable.ic_close);
                    imm.showSoftInput(view, 0);
                    text_search.requestFocus();
                } else { // Réinitialise l'affichage
                    button_menu.setVisibility(View.VISIBLE);
                    title_myrecipes.setVisibility(View.VISIBLE);
                    text_search.setVisibility(View.INVISIBLE);
                    text_search.setText("");
                    button_searchRecipe.setImageResource(R.drawable.ic_search);
                    //Ferme le clavier
                    if (view != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
        text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == "") {
                    displayListeRecettes(listeRecettes);
                } else {
                    ArrayList<Recette> liste = new ArrayList<Recette>();
                    for (Recette recette : listeRecettes) {
                        if (recette.getNom().toLowerCase().contains(s.toString().toLowerCase())
                                || recette.getIngredients().toLowerCase().contains(s.toString().toLowerCase())) {
                            liste.add(recette);
                        }
                    }
                    displayListeRecettes(liste);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        rvRecettes = (RecyclerView) view.findViewById(R.id.list_myrecipes);
        displayListeRecettes(listeRecettes);

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        reload();
                        new Handler().postDelayed(new Runnable(){
                            public void run(){
                                mySwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 500);
                        //reload();
                    }
                }
        );


        // Recharge la page tous les X temps
//        final Handler handler = new Handler();
//        final int delay = 2500; //milliseconds
//
//        handler.postDelayed(new Runnable(){
//            public void run(){
//                handler.postDelayed(this, delay);
//                if (!isStateSaved() && isResumed()) {
//                    reload();
//                }
//            }
//        }, delay);

        return view;
    }

    private void displayListeRecettes(ArrayList<Recette> listeRecettes) {
        layoutManager = new LinearLayoutManager(getContext());
        rvRecettes.setLayoutManager(layoutManager);

        if (listeRecettes != null)
            recettesAdapter = new RecettesAdapter(listeRecettes);
        else
            recettesAdapter = new RecettesAdapter(new ArrayList<Recette>());
        rvRecettes.setAdapter(recettesAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

//    @Override
//    public void onActivityResult (int requestCode, int resultCode, Intent data) {
//        Toast.makeText(getContext(), "test",Toast.LENGTH_SHORT).show();
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            Bundle b = data.getExtras();
//            if (b != null) {
//                Recette recette = (Recette) b.getSerializable("recette");
//                int position = (int) b.getSerializable("position");
//                listeRecettes.set(position, recette);
//                Toast.makeText(getContext(), "Receive: "+position + recette.getNom(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
