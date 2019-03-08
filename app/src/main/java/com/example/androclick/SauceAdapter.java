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

class Sauce {
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

public class SauceAdapter extends ArrayAdapter<Sauce> {
    private List<Sauce> listeSauces;
    private Context context;

    public SauceAdapter(List<Sauce> listeSauces, Context context) {
        super(context, R.layout.list_sauces, listeSauces);
        this.listeSauces = listeSauces;
        this.context = context;
    }

    private static class SauceHolder {
        public TextView nomSauce;
        public CheckBox chkSauce;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SauceHolder holder = new SauceHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_sauces, null);

            holder.nomSauce = (TextView)v.findViewById(R.id.nom_sauce);
            holder.chkSauce = (CheckBox)v.findViewById(R.id.checkbox_sauce);

            if (context.getClass().equals(Tacos.class)) {
                holder.chkSauce.setOnCheckedChangeListener((Tacos)context);
            } else if (context.getClass().equals(EditRecipe.class)) {
                holder.chkSauce.setOnCheckedChangeListener((EditRecipe)context);
            }
        } else {
            holder = (SauceHolder)v.getTag();
        }

        if (holder != null) {
            if (holder.nomSauce != null && holder.chkSauce != null) {

                Sauce s = listeSauces.get(position);
                holder.nomSauce.setText(s.getNom());
                holder.chkSauce.setChecked(s.isSelected());
                holder.chkSauce.setTag(s);
            }
        } else {
            //Toast.makeText(context, "Holder null parce que des aliens modifient les lois de la physique O_O", Toast.LENGTH_LONG).show();
        }

        return v;
    }
}
