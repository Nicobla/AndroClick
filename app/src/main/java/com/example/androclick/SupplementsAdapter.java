package com.example.androclick;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

class Supplement implements Serializable {
    String nom;
    boolean selected = false;

    public Supplement() {
        this("supplément sans nom");
    }

    public Supplement(String nom) {
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

public class SupplementsAdapter extends RecyclerView.Adapter<SupplementsAdapter.SupplementsHolder> {
    private ArrayList<Supplement> listeSupplements = new ArrayList<Supplement>();

    public class SupplementsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomSupplement;
        public CheckBox chkSupplement;

        public SupplementsHolder(View v) {
            super(v);

            nomSupplement = (TextView) v.findViewById(R.id.nom_supplement);
            chkSupplement = (CheckBox) v.findViewById(R.id.checkbox_supplement);
            //chkSupplement.setChecked(false);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_supplement);
            chk.setChecked(!chk.isChecked());

            int pos = getAdapterPosition();
            Supplement s = listeSupplements.get(pos);
            //s.setSelected(!s.isSelected());

            //Log.d("Test", "Click sur un supplément (" + pos + ") - " + s.isSelected());
        }
    }

    public SupplementsAdapter(ArrayList<Supplement> listSupplements) {
        listeSupplements = listSupplements;
    }

    @Override
    public SupplementsAdapter.SupplementsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplements, parent, false);
        SupplementsHolder vh = new SupplementsHolder(v);
        //vh.chkSupplement.setChecked(false);
        return vh;
    }

    @Override
    public void onBindViewHolder(SupplementsHolder holder, int position) {
        Supplement s = listeSupplements.get(position);

        holder.nomSupplement.setText(s.getNom());
        holder.chkSupplement.setChecked(s.isSelected());

        //holder.chkSupplement.setTag(kek_chose);
    }

    @Override
    public int getItemCount() {
        return listeSupplements.size();
    }
}
