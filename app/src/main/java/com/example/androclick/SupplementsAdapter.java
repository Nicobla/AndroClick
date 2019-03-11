package com.example.androclick;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

class Supplement implements Serializable {
    String nom;
    boolean selected = false;

    public Supplement(String nom) {
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

public class SupplementsAdapter extends RecyclerView.Adapter<SupplementsAdapter.SupplementsHolder> {
    private ArrayList<Supplement> listeSupplements = new ArrayList<Supplement>();

    public class SupplementsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomSupplement;
        public TextView ingSupplement;

        public SupplementsHolder(View v) {
            super(v);
            nomSupplement = (TextView) v.findViewById(R.id.nom_supplement);
            //ingSupplement = (TextView) v.findViewById(R.id.ingredients_supplement);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("Test", "Slt");
        }
    }

    public SupplementsAdapter(ArrayList<Supplement> listSupplements) {
        listeSupplements = listSupplements;
    }

    @Override
    public SupplementsAdapter.SupplementsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplements, parent, false);
        SupplementsHolder vh = new SupplementsHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SupplementsHolder holder, int position) {
        Supplement r = listeSupplements.get(position);

        holder.nomSupplement.setText(r.getNom());

    }

    @Override
    public int getItemCount() {
        return listeSupplements.size();
    }
}
