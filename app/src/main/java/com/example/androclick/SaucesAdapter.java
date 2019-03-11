package com.example.androclick;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

class Sauce implements Serializable {
    String nom;
    boolean selected = false;

    public Sauce(String nom) {
        super();
        this.nom=nom;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

public class SaucesAdapter extends RecyclerView.Adapter<SaucesAdapter.SaucesHolder> {
    private ArrayList<Sauce> listeSauces = new ArrayList<Sauce>();

    public class SaucesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomSauce;
        public TextView ingSauce;

        public SaucesHolder(View v) {
            super(v);
            nomSauce = (TextView) v.findViewById(R.id.nom_sauce);
            //ingSauce = (TextView) v.findViewById(R.id.ingredients_sauce);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("Test", "Slt");
        }
    }

    public SaucesAdapter(ArrayList<Sauce> listSauces) {
        listeSauces = listSauces;
    }

    @Override
    public SaucesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sauces, parent, false);
        SaucesHolder vh = new SaucesHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SaucesHolder holder, int position) {
        Sauce r = listeSauces.get(position);

        holder.nomSauce.setText(r.getNom());

    }

    @Override
    public int getItemCount() {
        return listeSauces.size();
    }
}
