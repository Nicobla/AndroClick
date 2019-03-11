package com.example.androclick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MakeTacos extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    public MakeTacos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_tacos_fragment, container, false);

        Bundle bundle = new Bundle();
        int numRecette = ((MyApplication) getActivity().getApplicationContext()).getListeRecettes().size() +1;
        bundle.putSerializable("recette", new Recette("Recette "+numRecette));

        viewPager = view.findViewById(R.id.pager);
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), bundle); // getSupportFragmentManager()
        viewPager.setAdapter(pagerAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
