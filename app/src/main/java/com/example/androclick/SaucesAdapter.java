package com.example.androclick;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public Sauce(String nom) {
        super();
        this.nom=nom;
        this.selected=false;
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
    private ArrayList<Sauce> listeSauces;

    public class SaucesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomSauce;
        public CheckBox chkSauce;

        public SaucesHolder(View v) {
            super(v);
            nomSauce = (TextView) v.findViewById(R.id.nom_sauce);
            chkSauce = (CheckBox) v.findViewById(R.id.checkbox_sauce);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_sauce);
            chk.setChecked(!chk.isChecked());

            int pos = getAdapterPosition();
            Sauce s = listeSauces.get(pos);
            //s.setSelected(!s.isSelected());

            Log.d("Test", "Click sur une sauce ("+pos+") - "+s.isSelected());
        }
    }

    public SaucesAdapter(ArrayList<Sauce> listSauces) {
        this.listeSauces = listSauces;
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
    }

    @Override
    public int getItemCount() {
        return listeSauces.size();
    }
}
