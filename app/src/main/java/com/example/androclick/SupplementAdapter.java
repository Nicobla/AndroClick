package com.example.androclick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class Supplement {
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

public class SupplementAdapter extends ArrayAdapter<Supplement> {
    private List<Supplement> listeSupplements;
    private Context context;

    public SupplementAdapter(List<Supplement> listeSupplements, Context context) {
        super(context, R.layout.list_supplements, listeSupplements);
        this.listeSupplements = listeSupplements;
        this.context = context;
    }

    private static class SupplementHolder {
        public TextView nomSupplement;
        public CheckBox chkSupplement;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SupplementAdapter.SupplementHolder holder = new SupplementAdapter.SupplementHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_supplements, null);

            holder.nomSupplement = (TextView)v.findViewById(R.id.nom_supplement);
            holder.chkSupplement = (CheckBox)v.findViewById(R.id.checkbox_supplement);

            if (context.getClass().equals(Tacos.class)) {
                holder.chkSupplement.setOnCheckedChangeListener((Tacos)context);
            } else if (context.getClass().equals(EditRecipe.class)) {
                holder.chkSupplement.setOnCheckedChangeListener((EditRecipe)context);
            }
        } else {
            holder = (SupplementAdapter.SupplementHolder)v.getTag();
        }

        if (holder != null) {
            if (holder.nomSupplement != null && holder.chkSupplement != null) {

                Supplement s = listeSupplements.get(position);
                holder.nomSupplement.setText(s.getNom());
                holder.chkSupplement.setChecked(s.isSelected());
                holder.chkSupplement.setTag(s);
            }
        } else {
            //Toast.makeText(context, "Holder toujours null parce que des aliens ont encore modifié les lois de la physique ._.", Toast.LENGTH_LONG).show();
        }

        return v;
    }
}
