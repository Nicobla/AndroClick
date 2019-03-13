package com.example.androclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class RecettesAdapter extends RecyclerView.Adapter<RecettesAdapter.RecettesHolder> {
    private ArrayList<Recette> listeRecettes = new ArrayList<>();

    public class RecettesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomRecette;
        public TextView ingRecette;

        public RecettesHolder(View v) {
            super(v);
            nomRecette = (TextView) v.findViewById(R.id.nom_recette);
            ingRecette = (TextView) v.findViewById(R.id.ingredients_recette);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MyRecipe.class);
            Recette recette = listeRecettes.get(getAdapterPosition());
            intent.putExtra("recette", recette);
            //intent.putExtra("position", getAdapterPosition());
            ((Activity) view.getContext()).startActivity(intent);
            //((Activity) view.getContext()).recreate();//displayListeRecettes();
        }
    }

    public RecettesAdapter(ArrayList<Recette> listRecettes) {
        this.listeRecettes = listRecettes;
    }

    @Override
    public RecettesAdapter.RecettesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recettes, parent, false);
        RecettesHolder vh = new RecettesHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecettesHolder holder, int position) {
        Recette r = listeRecettes.get(position);

        holder.nomRecette.setText(r.getNom());

        holder.ingRecette.setText(r.getIngredients());
    }

    @Override
    public int getItemCount() {
        return this.listeRecettes.size();
    }
}
