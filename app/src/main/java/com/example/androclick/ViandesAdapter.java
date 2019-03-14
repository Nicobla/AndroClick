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

class Viande implements Serializable {
    String nom;
    boolean selected = false;

    public Viande() {
        this("viande sans nom");
    }

    public Viande(String nom) {
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

public class ViandesAdapter extends RecyclerView.Adapter<ViandesAdapter.ViandesHolder> {
    private ArrayList<Viande> listeViandes = new ArrayList<Viande>();

    public class ViandesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomViande;
        public CheckBox chkViande;

        public ViandesHolder(View v) {
            super(v);
            nomViande = (TextView) v.findViewById(R.id.nom_viande);
            chkViande = (CheckBox) v.findViewById(R.id.checkbox_viande);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_viande);
            chk.setChecked(!chk.isChecked());

            int pos = getAdapterPosition();
            Viande v = listeViandes.get(pos);
            //s.setSelected(!s.isSelected());

            Log.d("Test", "Click sur une viande (" + pos + ") - " + v.isSelected());
        }
    }

    public ViandesAdapter(ArrayList<Viande> listViandes) {
        listeViandes = listViandes;
    }

    @Override
    public ViandesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viandes, parent, false);
        ViandesHolder vh = new ViandesHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViandesHolder holder, int position) {
        Viande v = listeViandes.get(position);

        holder.nomViande.setText(v.getNom());
        holder.chkViande.setChecked(v.isSelected());
    }

    @Override
    public int getItemCount() {
        return listeViandes.size();
    }
}
