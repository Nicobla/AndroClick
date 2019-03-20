package com.example.androclick;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class EditRecipe extends AppCompatActivity {

    private Recette recette;
    private int position;

    final int NB_MAX_SAUCES = 2;
    final int[] NB_MAX_VIANDES = {1, 2, 3, 4};
    int nb_max_viandes;
    boolean mustUncheck;

    ArrayList<String> listePositionsSauces;
    ArrayList<String> listePositionsViandes;

    ArrayList<Sauce> listeSauces;
    ArrayList<Viande> listeViandes;
    ArrayList<Supplement> listeSupplements;

    private RecyclerView rvSauces;
    private RecyclerView rvViandes;
    private RecyclerView rvSupplements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        ((MyApplication) getApplicationContext()).uncheckAllIngredients();

        recette = (Recette) getIntent().getSerializableExtra("recette");
        position = (int) getIntent().getSerializableExtra("position");

        listePositionsSauces = new ArrayList<>();
        for (Sauce sauce : recette.getSauces()) {
            int idx = 0;
            for (Sauce sauce1 : ((MyApplication)getApplicationContext()).getListeSauces()) {
                if (sauce1.getNom().equals(sauce.getNom()))
                    break;
                idx++;
            }
            listePositionsSauces.add(String.valueOf(idx));
        }

        listePositionsViandes = new ArrayList<>();
        for (Viande viande : recette.getViandes()) {
            int idx = 0;
            for (Viande viande1 : ((MyApplication)getApplicationContext()).getListeViandes()) {
                if (viande1.getNom().equals(viande.getNom()))
                    break;
                idx++;
            }
            listePositionsViandes.add(String.valueOf(idx));
        }

        ImageButton button_back = (ImageButton) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tailles_tacos);

        switch (recette.getStrTailleTacos()) {
            case "M":
                radioGroup.check(R.id.radio_M);
                nb_max_viandes = NB_MAX_VIANDES[0];
                break;
            case "L":
                radioGroup.check(R.id.radio_L);
                nb_max_viandes = NB_MAX_VIANDES[1];
                break;
            case "XL":
                radioGroup.check(R.id.radio_XL);
                nb_max_viandes = NB_MAX_VIANDES[2];
                break;
            case "XXL":
                radioGroup.check(R.id.radio_XXL);
                nb_max_viandes = NB_MAX_VIANDES[3];
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_M:
                        recette.setTailleTacos(Recette.TailleTacos.M);
                        nb_max_viandes = NB_MAX_VIANDES[0];
                        break;
                    case R.id.radio_L:
                        recette.setTailleTacos(Recette.TailleTacos.L);
                        nb_max_viandes = NB_MAX_VIANDES[1];
                        break;
                    case R.id.radio_XL:
                        recette.setTailleTacos(Recette.TailleTacos.XL);
                        nb_max_viandes = NB_MAX_VIANDES[2];
                        break;
                    case R.id.radio_XXL:
                        recette.setTailleTacos(Recette.TailleTacos.XXL);
                        nb_max_viandes = NB_MAX_VIANDES[3];
                        break;
                }
                if (listePositionsViandes.size() > nb_max_viandes)
                    mustUncheck = true;
                displayListeViandes();
            }
        });

        rvSauces = (RecyclerView) findViewById(R.id.list_sauces);
        displayListeSauces();

        rvViandes = (RecyclerView) findViewById(R.id.list_viandes);
        displayListeViandes();

        rvSupplements = (RecyclerView) findViewById(R.id.list_supplements);
        displayListeSupplements();

        rvSauces.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_sauce);
                        if (chk.isEnabled()) {
                            Sauce s = listeSauces.get(position);
                            s.setSelected(!s.isSelected());
                            chk.setChecked(s.isSelected());

                            if (s.isSelected()) {
                                recette.addSauce(s);
                                listePositionsSauces.add(String.valueOf(position));
                            } else {
                                recette.removeSauce(s);
                                listePositionsSauces.remove(String.valueOf(position));
                                enableAllSaucesCheckboxes();
                            }

                            if (listePositionsSauces.size() >= NB_MAX_SAUCES) {
                                disableSaucesCheckboxes();
                            }
                        }
                    }
                })
        );
        rvViandes.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CheckBox chk = (CheckBox) view.findViewById(R.id.checkbox_viande);
                        if (chk.isEnabled()) {
                            Viande v = listeViandes.get(position);
                            v.setSelected(!v.isSelected());
                            chk.setChecked(v.isSelected());

                            if (v.isSelected()) {
                                recette.addViande(v);
                                listePositionsViandes.add(String.valueOf(position));
                            } else {
                                recette.removeViande(v);
                                listePositionsViandes.remove(String.valueOf(position));
                                enableAllViandesCheckboxes();
                            }
//                            switch (recette.getTailleTacos()) {
//                                case M:
//                                    nb_max_viandes = NB_MAX_VIANDES[0];
//                                    break;
//                                case L:
//                                    nb_max_viandes = NB_MAX_VIANDES[1];
//                                    break;
//                                case XL:
//                                    nb_max_viandes = NB_MAX_VIANDES[2];
//                                    break;
//                                case XXL:
//                                    nb_max_viandes = NB_MAX_VIANDES[3];
//                                    break;
//                            }
                            if (listePositionsViandes.size() >= nb_max_viandes) {
                                disableViandesCheckboxes();
                            }
                        }
                    }
                })
        );
        rvSupplements.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Supplement s = listeSupplements.get(position);
                        s.setSelected(!s.isSelected());
                        if (s.isSelected()) {
                            recette.addSupplement(s);
                        } else {
                            recette.removeSupplement(s);
                        }
                    }
                })
        );


        FloatingActionButton button_apply = (FloatingActionButton) findViewById(R.id.button_apply);
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enregistrement de la recette
                ((MyApplication) getApplicationContext()).setRecette(position, recette);
                finish();
            }
        });
    }

    private void displayListeSauces() {
        listeSauces = ((MyApplication) getApplicationContext()).getListeSauces();

        for (Sauce sauce1 : listeSauces) {
            for (Sauce sauce : recette.getSauces()) {
                if (sauce.getNom().equals(sauce1.getNom())) {
                    sauce.setSelected(true);
                    listeSauces.set(listeSauces.indexOf(sauce1), sauce);
                }
            }
        }
        rvSauces.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvSauces.setAdapter(new SaucesAdapter(listeSauces, listePositionsSauces, NB_MAX_SAUCES));
    }
    private void displayListeViandes() {
        if (mustUncheck) {
            ((MyApplication) getApplicationContext()).uncheckViandes();
            recette.setViandes(new ArrayList<Viande>());
            listePositionsViandes = new ArrayList<>();
            mustUncheck = false;
        }
        listeViandes = ((MyApplication) getApplicationContext()).getListeViandes();

        for (Viande viande1 : listeViandes) {
            for (Viande viande : recette.getViandes()) {
                if (viande.getNom().equals(viande1.getNom())) {
                    viande.setSelected(true);
                    listeViandes.set(listeViandes.indexOf(viande1), viande);
                }
            }
        }
        rvViandes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvViandes.setAdapter(new ViandesAdapter(listeViandes, listePositionsViandes, nb_max_viandes));
    }
    private void displayListeSupplements() {
        listeSupplements = ((MyApplication) getApplicationContext()).getListeSupplements();

        for (Supplement supplement1 : listeSupplements) {
            for (Supplement supplement : recette.getSupplements()) {
                if (supplement.getNom().equals(supplement1.getNom())) {
                    supplement.setSelected(true);
                    listeSupplements.set(listeSupplements.indexOf(supplement1), supplement);
                }
            }
        }
        rvSupplements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvSupplements.setAdapter(new SupplementsAdapter(listeSupplements));
    }

    private void disableSaucesCheckboxes() {
        for (int i = 0; i < rvSauces.getChildCount(); i++) {
            boolean isChecked = false;
            for (String strPos : listePositionsSauces) {
                if (i == Integer.parseInt(strPos))
                    isChecked = true;
            }
            if (!isChecked) {
                RecyclerView.ViewHolder vh = rvSauces.findViewHolderForAdapterPosition(i);
                CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_sauce);
                chkbox.setEnabled(false);
                chkbox.setActivated(false);
            }
        }
    }
    private void disableViandesCheckboxes() {
        for (int i = 0; i < rvViandes.getChildCount(); i++) {
            boolean isChecked = false;
            for (String strPos : listePositionsViandes) {
                if (i == Integer.parseInt(strPos))
                    isChecked = true;
            }
            if (!isChecked) {
                RecyclerView.ViewHolder vh = rvViandes.findViewHolderForAdapterPosition(i);
                CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_viande);
                chkbox.setEnabled(false);
                chkbox.setActivated(false);
            }
        }
    }

    private void enableAllSaucesCheckboxes() {
        for (int i = 0; i < rvSauces.getChildCount(); i++) {
            RecyclerView.ViewHolder vh = rvSauces.findViewHolderForAdapterPosition(i);
            CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_sauce);
            chkbox.setEnabled(true);
            chkbox.setActivated(true);
        }
    }
    private void enableAllViandesCheckboxes() {
        for (int i = 0; i < rvViandes.getChildCount(); i++) {
            RecyclerView.ViewHolder vh = rvViandes.findViewHolderForAdapterPosition(i);
            CheckBox chkbox = (CheckBox) vh.itemView.findViewById(R.id.checkbox_viande);
            chkbox.setEnabled(true);
            chkbox.setActivated(true);
        }
    }

}
