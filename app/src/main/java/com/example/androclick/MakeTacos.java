package com.example.androclick;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MakeTacos extends Fragment {

    private MakeTacosViewModel mViewModel;

    public static MakeTacos newInstance() {
        return new MakeTacos();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment, container, false);

        final Button button_start = (Button)view.findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "Création d'une recette", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Tacos.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MakeTacosViewModel.class);
        // TODO: Use the ViewModel
    }

}
