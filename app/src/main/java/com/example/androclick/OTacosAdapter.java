package com.example.androclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class OTacosAdapter extends RecyclerView.Adapter<OTacosAdapter.OTacosHolder> {
    private ArrayList<O_Tacos> listeOTacos = new ArrayList<>();

    public class OTacosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nomOTacos;
        public TextView adrOTacos;

        public OTacosHolder(View v) {
            super(v);
            nomOTacos = (TextView) v.findViewById(R.id.nom_otacos);
            adrOTacos = (TextView) v.findViewById(R.id.adresse_otacos);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), OTacos_Details.class);
            O_Tacos_Serializable otacos = new O_Tacos_Serializable(listeOTacos.get(getAdapterPosition()));
            intent.putExtra("otacos", otacos);
            ((Activity) view.getContext()).startActivity(intent);
        }
    }

    public OTacosAdapter(ArrayList<O_Tacos> listOTacos) {
        this.listeOTacos = listOTacos;
    }

    @Override
    public OTacosAdapter.OTacosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_otacos, parent, false);
        OTacosHolder vh = new OTacosHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OTacosHolder holder, int position) {
        O_Tacos o = listeOTacos.get(position);

        holder.nomOTacos.setText(o.getNom());

        holder.adrOTacos.setText(o.getStrFullAdresse());
    }

    @Override
    public int getItemCount() {
        return this.listeOTacos.size();
    }
}
