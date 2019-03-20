package com.example.androclick;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

class Viande implements Serializable {
    String nom;
    boolean selected;

    public Viande() {
        this("Viande sans nom");
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

    boolean isSelected() {
        return selected;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

}

public class ViandesAdapter extends RecyclerView.Adapter<ViandesAdapter.ViandesHolder> {
    private ArrayList<Viande> listeViandes;
    private ArrayList<String> listePositions;
    private int nb_max_viandes;

    public class ViandesHolder extends RecyclerView.ViewHolder {
        public TextView nomViande;
        public CheckBox chkViande;

        public ViandesHolder(View v) {
            super(v);
            nomViande = (TextView) v.findViewById(R.id.nom_viande);
            chkViande = (CheckBox) v.findViewById(R.id.checkbox_viande);
        }
    }

    public ViandesAdapter(ArrayList<Viande> listViandes, ArrayList<String> listPositions, int nb_max_viandes) {
        if (listViandes == null)
            this.listeViandes = new ArrayList<>();
        else
            this.listeViandes = listViandes;
        this.listePositions = listPositions;
        this.nb_max_viandes = nb_max_viandes;
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

        if (listePositions.size() >= nb_max_viandes) {
            boolean isChecked = false;
            for (String strPos : listePositions) {
                if (position == Integer.parseInt(strPos)) {
                    isChecked = true;
                }
            }
            if (isChecked) {
                holder.chkViande.setEnabled(true);
                holder.chkViande.setActivated(true);
            } else {
                holder.chkViande.setEnabled(false);
                holder.chkViande.setActivated(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listeViandes.size();
    }
}
