package com.example.androclick;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

class Sauce implements Serializable {
    String nom;
    boolean selected;

    public Sauce() {
        this("Sauce sans nom");
    }

    public Sauce(String nom) {
        super();
        this.nom = nom;
        this.selected = false;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    boolean isSelected() {
        return selected;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

}

public class SaucesAdapter extends RecyclerView.Adapter<SaucesAdapter.SaucesHolder> {
    private ArrayList<Sauce> listeSauces;
    private ArrayList<String> listePositions;
    private int nb_max_sauces;

    public class SaucesHolder extends RecyclerView.ViewHolder {
        public TextView nomSauce;
        public CheckBox chkSauce;

        public SaucesHolder(View v) {
            super(v);
            nomSauce = (TextView) v.findViewById(R.id.nom_sauce);
            chkSauce = (CheckBox) v.findViewById(R.id.checkbox_sauce);
        }
    }

    public SaucesAdapter(ArrayList<Sauce> listSauces, ArrayList<String> listPositions, int nb_max_sauces) {
        if (listSauces == null)
            this.listeSauces = new ArrayList<>();
        else
            this.listeSauces = listSauces;
        this.listePositions = listPositions;
        this.nb_max_sauces = nb_max_sauces;
    }

    @Override
    public SaucesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sauces, parent, false);
        SaucesHolder vh = new SaucesHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SaucesHolder holder, int position) {
        Sauce s = listeSauces.get(position);

        holder.nomSauce.setText(s.getNom());
        holder.chkSauce.setChecked(s.isSelected());

        if (listePositions.size() >= nb_max_sauces) {
            boolean isChecked = false;
            for (String strPos : listePositions) {
                if (position == Integer.parseInt(strPos)) {
                    isChecked = true;
                }
            }
            if (isChecked) {
                holder.chkSauce.setEnabled(true);
                holder.chkSauce.setActivated(true);
            } else {
                holder.chkSauce.setEnabled(false);
                holder.chkSauce.setActivated(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listeSauces.size();
    }
}
