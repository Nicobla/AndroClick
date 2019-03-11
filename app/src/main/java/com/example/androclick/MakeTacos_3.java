package com.example.androclick;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class MakeTacos_3 extends Fragment {


    public MakeTacos_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment_3, container, false);

//        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setProgress(getArguments().getInt("progression"));

        return view;
    }

}
