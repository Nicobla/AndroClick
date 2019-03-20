package com.example.androclick;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Settings extends Fragment {

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        final ConstraintLayout cl = view.findViewById(R.id.constraintLayout);

        final ImageButton button_menu = (ImageButton) view.findViewById(R.id.button_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        final Button button_reinit = (Button) view.findViewById(R.id.button_reinit);
        button_reinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_reinit, null);

                // Crée la fenêtre de pop-up
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(cl, Gravity.CENTER, 0, 0);

                Button buttonYes = (Button) popupView.findViewById(R.id.button_confirm);
                Button buttonNo = (Button) popupView.findViewById(R.id.button_cancel);

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        // Suppression
                        ((MyApplication) getActivity().getApplicationContext()).deleteAllData();
                        Toast.makeText(getContext(), "Données réinitialisées !", Toast.LENGTH_SHORT).show();
                    }
                });
                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final Button button_delete_firebase = (Button) view.findViewById(R.id.button_delete_firebase);
        button_delete_firebase.setEnabled(firebaseUser != null);
        button_delete_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    if (firebaseUser != null) {
                        String userID = firebaseUser.getUid();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("User").document(userID).delete();
                        Toast.makeText(getContext(), "Données supprimées !", Toast.LENGTH_SHORT).show();
                        button_delete_firebase.setEnabled(false);
                        FirebaseAuth.getInstance().signOut();
                    }
                } else {
                    Toast.makeText(getContext(), "Impossible de supprimer. Vérifiez votre connexion internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final Button button_force_location = (Button) view.findViewById(R.id.button_force_location);
        button_force_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApplication) getActivity().getApplicationContext()).setUserPosition(new LatLng(45.9208490, 6.1415));
                Toast.makeText(getContext(), "Location SET !", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
