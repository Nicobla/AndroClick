package com.example.androclick;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*public class RecettesAdapter extends RecyclerView.Adapter<Recette> {

    private static class RecettesHolder {
        public TextView nomRecette;
        public TextView ingRecette;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        RecettesHolder holder = new RecettesHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_recettes, null);

            holder.nomRecette = (TextView)v.findViewById(R.id.nom_recette);
            holder.ingRecette = (TextView)v.findViewById(R.id.ingredients_recette);

        } else {
            holder = (RecettesHolder)v.getTag();
        }

        if (holder != null) {
            if (holder.nomRecette != null && holder.ingRecette != null) {

                Recette s = listeRecettes.get(position);
                holder.nomRecette.setText(s.getNom());

                String ingredients = "";
                for (String ingredient : s.getIngredients()) {
                    if (ingredients != "")
                        ingredients += ", ";
                    ingredients += ingredient;
                }
                holder.ingRecette.setText(ingredients);
            }
        } else {
            //Toast.makeText(context, "Holder toujours null parce que des aliens ont encore modifié les lois de la physique ._.", Toast.LENGTH_LONG).show();
        }

        return v;
    }
}*/



public class RecettesAdapter extends RecyclerView.Adapter<RecettesAdapter.RecettesHolder> {
    private ArrayList<Recette> listeRecettes = new ArrayList<Recette>();

    public static class RecettesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomRecette;
        public TextView ingRecette;

        public RecettesHolder(View v) {
            super(v);
            nomRecette = (TextView) v.findViewById(R.id.nom_recette);
            ingRecette = (TextView) v.findViewById(R.id.ingredients_recette);
            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "item "+getPosition(), Toast.LENGTH_SHORT).show();
            view.getContext().startActivity(new Intent(view.getContext(), MyRecipe.class));
        }
    }

    public RecettesAdapter(ArrayList<Recette> test) {
        listeRecettes = test;
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

        String ingredients = "";
        for (String ingredient : r.getIngredients()) {
            if (ingredients != "")
                ingredients += ", ";
            ingredients += ingredient;
        }
        holder.ingRecette.setText(ingredients);
    }

    @Override
    public int getItemCount() {
        return listeRecettes.size();
    }
}
