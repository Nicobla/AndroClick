package com.example.androclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class OTacosAdapter extends RecyclerView.Adapter<OTacosAdapter.OTacosHolder> {
    private ArrayList<O_Tacos> listeOTacos = new ArrayList<>();
    private LatLng userPosition;

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
            if (userPosition != null)
                intent.putExtra("userPosition", new GeoPoint2(userPosition.latitude, userPosition.longitude));
            ((Activity) view.getContext()).startActivity(intent);
        }
    }

    public OTacosAdapter(ArrayList<O_Tacos> listOTacos, LatLng userPosition) {
        this.userPosition = userPosition;
        if (userPosition != null)
            this.listeOTacos = triParDistance(listOTacos, userPosition);
        else
            this.listeOTacos = listOTacos;
//        Collections.sort(listOTacos); //tri les OTacos par ordre de distance au point "userPosition"
//        this.listeOTacos = listOTacos;
    }

    public ArrayList<O_Tacos> triParDistance(ArrayList<O_Tacos> listeOTacos, LatLng userPosition) {
        boolean continueTri = true;
        int nbOTacos = listeOTacos.size();

        while (continueTri) {
            continueTri = false;
            for (int idx=0; idx<nbOTacos-1; idx++) {
                O_Tacos o1 = listeOTacos.get(idx);
                O_Tacos o2 = listeOTacos.get(idx+1);

                double dif = o1.distance(userPosition) - o2.distance(userPosition);

                if (dif > 0) {
                    continueTri = true;
                    O_Tacos temp = listeOTacos.get(idx);
                    listeOTacos.set(idx, listeOTacos.get(idx+1));
                    listeOTacos.set(idx+1, temp);
                }
            }
        }

        return listeOTacos;
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

        String strAdresse;
        if (userPosition != null)
            strAdresse = o.getStrFullAdresse() + "\n" + (double) Math.round(o.distance(userPosition)/1000 * 10) / 10 + " km";
        else
            strAdresse = o.getStrFullAdresse();
        holder.adrOTacos.setText(strAdresse);
    }

    @Override
    public int getItemCount() {
        return this.listeOTacos.size();
    }
}
