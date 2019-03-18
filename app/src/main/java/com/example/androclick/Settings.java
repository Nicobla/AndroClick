package com.example.androclick;

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

public class Settings extends Fragment {
    ConstraintLayout cl;
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


        return view;
    }
}
